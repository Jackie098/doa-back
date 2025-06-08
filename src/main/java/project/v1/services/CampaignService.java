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

}
