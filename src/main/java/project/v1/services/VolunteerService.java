package project.v1.services;

import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.transaction.Transactional;
import project.common.exceptions.MessageErrorEnum;
import project.common.exceptions.customs.BusinessException;
import project.common.mappers.UserMapper;
import project.v1.dtos.volunteer.VolunteerCreateDTO;
import project.v1.entities.Campaign;
import project.v1.entities.User;
import project.v1.entities.enums.CampaignStatusEnum;

@ApplicationScoped
public class VolunteerService {
  @Inject
  private UserService userService;
  @Inject
  private CampaignService campaignService;
  @Inject
  private CampaignVolunteerService campaignVolunteerService;

  @Transactional
  public void create(VolunteerCreateDTO dto) {
    Campaign campaign = campaignService.findById(dto.getVolunteer().getCampaignId())
        .orElseThrow(() -> new BusinessException(MessageErrorEnum.CAMPAIGN_NOT_FOUND.getMessage(), 404));

    if (!(campaign.getStatus() == CampaignStatusEnum.SCHEDULED || campaign.getStatus() == CampaignStatusEnum.ACTIVE)) {
      throw new BusinessException(MessageErrorEnum.CAMPAIGN_NOT_RECEIVE_NEW_VOLUNTEER.getMessage(), 400);
    }

    userService
        .findByEmailOrPhoneNumber(dto.getEmail(), dto.getPhoneNumber())
        .ifPresent(user -> {
          throw new BusinessException(MessageErrorEnum.USER_ALREADY_EXISTS.getMessage(), 409);
        });

    var userCreatedDto = UserMapper.fromVolunteerCreateToUserCreateDTO(dto);
    var user = userService.create(userCreatedDto);

    campaignVolunteerService.bind(campaign, user);
  }

  @Transactional
  public void bindVolunteerCampaign(Long userId, Long campaignId) {
    campaignVolunteerService.findBindByUser(userId, campaignId).ifPresent((data) -> {
      throw new BusinessException(MessageErrorEnum.CAMPAIGN_VOLUNTEER_BIND_ALREADY_EXISTS.getMessage(), 409);
    });

    Campaign campaign = campaignService.findById(campaignId)
        .orElseThrow(() -> new BusinessException(MessageErrorEnum.CAMPAIGN_NOT_FOUND.getMessage(), 404));

    if (!(campaign.getStatus() == CampaignStatusEnum.SCHEDULED || campaign.getStatus() == CampaignStatusEnum.ACTIVE)) {
      throw new BusinessException(MessageErrorEnum.CAMPAIGN_NOT_RECEIVE_NEW_VOLUNTEER.getMessage(), 400);
    }

    User user = userService.findById(userId)
        .orElseThrow(() -> new BusinessException(MessageErrorEnum.USER_NOT_FOUND.getMessage(), 404));

    campaignVolunteerService.bind(campaign, user);
  }
}
