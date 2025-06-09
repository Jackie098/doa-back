package project.v1.services;

import java.time.Instant;

import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import project.common.exceptions.MessageErrorEnum;
import project.common.exceptions.customs.BusinessException;
import project.common.mappers.CampaignMapper;
import project.v1.dtos.campaign.CampaignCreateDTO;
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

}
