package project.v1.services;

import java.time.Instant;
import java.util.Optional;

import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import project.common.exceptions.MessageErrorEnum;
import project.common.exceptions.customs.BusinessException;
import project.common.mappers.CampaignMapper;
import project.v1.dtos.campaign.CampaignCreateDTO;
import project.v1.dtos.campaign.CampaignUpdateDTO;
import project.v1.entities.Campaign;
import project.v1.entities.CharityAgent;
import project.v1.entities.enums.CampaignStatusEnum;
import project.v1.repositories.CampaignRepository;

@ApplicationScoped
public class CampaignService {
  @Inject
  private CampaignRepository campaignRepository;

  @Inject
  private AgentService agentService;

  public Boolean verifySlug(CampaignCreateDTO dto) {
    return campaignRepository.find("slug = ?1 AND agent.id = ?2", dto.getSlug(), dto.getAgentId())
        .firstResult() != null;
  }

  public Campaign create(CampaignCreateDTO dto) {
    CharityAgent agent = agentService.findById(dto.getAgentId())
        .orElseThrow(() -> new BusinessException(MessageErrorEnum.AGENT_NOT_FOUND.getMessage(), 404));

    if (Boolean.FALSE.equals(dto.getStartNow()) && dto.getStartDate() == null) {
      throw new BusinessException(
          MessageErrorEnum.SCHEDULED_CAMPAIGN_WITHOUT_START_DATE.getMessage(), 400);
    }

    if (Boolean.FALSE.equals(dto.getStartNow()) && dto.getStartDate().isBefore(Instant.now())) {
      throw new BusinessException(MessageErrorEnum.SCHEDULED_CAMPAIGN_INVALID_START_DATE.getMessage(), 400);
    }

    if (dto.getDueDate().isBefore(dto.getStartDate())) {
      throw new BusinessException(MessageErrorEnum.SCHEDULED_CAMPAIGN_INVALID_DUE_DATE.getMessage(), 400);
    }

    if (verifySlug(dto)) {
      throw new BusinessException(MessageErrorEnum.SLUG_ALREADY_EXISTS.getMessage(), 400);
    }

    if (Boolean.TRUE.equals(dto.getStartNow())) {
      dto.setStartDate(Instant.now());
      dto.setStatus(CampaignStatusEnum.ACTIVE);
    } else {
      dto.setStatus(CampaignStatusEnum.AWAITING);
    }

    Campaign campaign = CampaignMapper.fromDTO(dto, agent);

    campaignRepository.persistAndFlush(campaign);
    System.out.println("created at-> " + campaign.getCreatedAt());

    return campaign;
  }

  public Campaign update(CampaignUpdateDTO dto) {
    if (dto == null || isEmptyUpdateDTO(dto)) {
      throw new BusinessException("Não é possível atualizar uma campanha com nenhum valor novo.", 400);
    }

    Campaign campaign = campaignRepository.find("id = ?1 AND agent.user.id = ?2", dto.getId(), dto.getAgentId())
        .firstResult();

    if (campaign == null) {
      throw new BusinessException(MessageErrorEnum.CAMPAIGN_NOT_FOUND.getMessage(), 404);
    }

    // Alterar ticketPrice somente SE status = AWAITING
    if (dto.getTicketPrice() != null && campaign.getStatus() != CampaignStatusEnum.AWAITING) {
      throw new BusinessException("Somente é possível alterar o valor do ticket antes da campanha começar.", 400);
    }

    if (campaign.getStatus() == CampaignStatusEnum.CANCELED
        || campaign.getStatus() == CampaignStatusEnum.FINISHED) {

      Integer newTotal = dto.getTotalTickets() != null ? dto.getTotalTickets() : 0;
      Integer currentTotal = campaign.getTotalTickets() != null ? campaign.getTotalTickets() : 0;
      // TODO: Alterar totalTickets pra menos somente se ainda há tickets disponíveis
      if (dto.getTotalTickets() != null && newTotal < currentTotal) {
        throw new BusinessException("Diminuir a quantidade total de tickets duma campanha ainda não é possível", 400);
      }

      // Alterar totalTickets para mais SE campanha NÂO cancelada OU finalizada
      if (newTotal > currentTotal) {
        throw new BusinessException(
            "Não é possível alterar o total de tickets pois a campanha está cancelada ou finalizada.", 400);
      }
    }

    // Alterar startDate SE (status == AWAITING) E startDate < dueDate
    if (dto.getStartDate() != null) {
      if (campaign.getStatus() != CampaignStatusEnum.AWAITING) {
        throw new BusinessException("Só é possível alterar a data de início se a campanha estiver agendada (AWAITING).",
            400);
      }

      if (campaign.getDueDate() != null && dto.getStartDate().isAfter(campaign.getDueDate())) {
        throw new BusinessException("A data de início não pode ser posterior à data de término.", 400);
      }
    }

    // Alterar dueDate SE status != CANCELED
    if (dto.getDueDate() != null) {
      if (campaign.getStatus() == CampaignStatusEnum.CANCELED) {
        throw new BusinessException("Não é possível alterar a data de término de uma campanha cancelada.", 400);
      }

      if (campaign.getStartDate().isAfter(dto.getDueDate())) {
        throw new BusinessException("A data de finalização não deve ser anterior a data de início.", 400);
      }
    }

    // Atualizar campos aqui
    updateFromDTO(campaign, dto);

    return campaign;
  }

  public void reactivate(Long id, Long agentId) {
    Campaign campaign = campaignRepository.find("id = ?1 AND agent.user.id = ?2", id, agentId).firstResult();

    if (campaign == null) {
      throw new BusinessException(MessageErrorEnum.CAMPAIGN_NOT_FOUND.getMessage(), 404);
    }

    if (campaign.getStatus() == CampaignStatusEnum.ACTIVE) {
      throw new BusinessException(MessageErrorEnum.CAMPAIGN_ALREADY_ACTIVE.getMessage(), 400);
    }

    if (campaign.getStatus() == CampaignStatusEnum.CANCELED) {
      throw new BusinessException(MessageErrorEnum.ACTIVATE_CANCELED_CAMPAIGN.getMessage(), 400);
    }

    if (campaign.getFinishedDate() != null || campaign.getStatus() == CampaignStatusEnum.FINISHED) {
      if (campaign.getDueDate().isBefore(Instant.now())) {
        throw new BusinessException(MessageErrorEnum.UPDATE_DUE_DATE.getMessage(),
            404);
      }
    }

    campaign.setStatus(CampaignStatusEnum.ACTIVE);
  }

  public void pause(Long id, Long agentId) {
    Campaign campaign = campaignRepository.find("id = ?1 AND agent.user.id = ?2", id, agentId).firstResult();

    if (campaign == null) {
      throw new BusinessException(MessageErrorEnum.CAMPAIGN_NOT_FOUND.getMessage(), 404);
    }

    if (!(campaign.getStatus() == CampaignStatusEnum.ACTIVE || campaign.getStatus() == CampaignStatusEnum.AWAITING)) {
      throw new BusinessException(MessageErrorEnum.PAUSE_ONLY_ACITVE_OR_AWAIT_CAMPAIN.getMessage(), 400);
    }

    if (campaign.getFinishedDate() != null) {
      throw new BusinessException(MessageErrorEnum.CAMPAIGN_ALREADY_FINISHED.getMessage(), 400);
    }

    campaign.setStatus(CampaignStatusEnum.PAUSED);
  }

  public void cancel(Long id, Long agentId) {
    Campaign campaign = campaignRepository.find("id = ?1 AND agent.user.id = ?2", id, agentId).firstResult();

    if (campaign == null) {
      throw new BusinessException(MessageErrorEnum.CAMPAIGN_NOT_FOUND.getMessage(), 404);
    }

    if (campaign.getStatus() == CampaignStatusEnum.CANCELED) {
      throw new BusinessException(MessageErrorEnum.CAMPAIGN_ALREADY_CANCELED.getMessage(), 400);
    }

    if (campaign.getStatus() == CampaignStatusEnum.FINISHED) {
      throw new BusinessException(MessageErrorEnum.CAMPAIGN_ALREADY_FINISHED.getMessage(), 400);
    }

    campaign.setStatus(CampaignStatusEnum.CANCELED);
  }

  public void finish(Long id, Long agentId) {
    Campaign campaign = campaignRepository.find("id = ?1 AND agent.user.id = ?2", id, agentId).firstResult();

    if (campaign == null) {
      throw new BusinessException(MessageErrorEnum.CAMPAIGN_NOT_FOUND.getMessage(), 404);
    }

    if (campaign.getStatus() != CampaignStatusEnum.ACTIVE) {
      throw new BusinessException(MessageErrorEnum.FINISH_ONLY_ACITVE_CAMPAIN.getMessage(), 400);
    }

    if (campaign.getDueDate().isBefore(Instant.now())) {
      throw new BusinessException(MessageErrorEnum.DUE_DATE_ACTIVE_CAMPAIGN.getMessage(), 400);
    }

    if (campaign.getFinishedDate() != null) {
      throw new BusinessException(MessageErrorEnum.CAMPAIGN_ALREADY_FINISHED.getMessage(), 400);
    }

    campaign.setFinishedDate(Instant.now());
    campaign.setStatus(CampaignStatusEnum.FINISHED);
  }

  public static void updateFromDTO(Campaign campaign, CampaignUpdateDTO dto) {
    Optional.ofNullable(dto.getName()).ifPresent(campaign::setName);
    Optional.ofNullable(dto.getPhoneNumber()).ifPresent(campaign::setPhoneNumber);
    Optional.ofNullable(dto.getDescription()).ifPresent(campaign::setDescription);
    Optional.ofNullable(dto.getAddresLineOne()).ifPresent(campaign::setAddresLineOne);
    Optional.ofNullable(dto.getAddresLineTwo()).ifPresent(campaign::setAddresLineTwo);
    Optional.ofNullable(dto.getAddresLineThree()).ifPresent(campaign::setAddresLineThree);
    Optional.ofNullable(dto.getTotalTickets()).ifPresent(campaign::setTotalTickets);
    Optional.ofNullable(dto.getTicketPrice()).ifPresent(campaign::setTicketPrice);
    Optional.ofNullable(dto.getStartDate()).ifPresent(campaign::setStartDate);
    Optional.ofNullable(dto.getDueDate()).ifPresent(campaign::setDueDate);
  }

  private boolean isEmptyUpdateDTO(CampaignUpdateDTO dto) {
    return dto.getName() == null &&
        dto.getPhoneNumber() == null &&
        dto.getDescription() == null &&
        dto.getAddresLineOne() == null &&
        dto.getAddresLineTwo() == null &&
        dto.getAddresLineThree() == null &&
        dto.getTotalTickets() == null &&
        dto.getTicketPrice() == null &&
        dto.getStartDate() == null &&
        dto.getDueDate() == null;
  }
}
