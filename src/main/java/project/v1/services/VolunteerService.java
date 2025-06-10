package project.v1.services;

import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.transaction.Transactional;
import project.common.exceptions.MessageErrorEnum;
import project.common.exceptions.customs.BusinessException;
import project.common.mappers.UserMapper;
import project.v1.dtos.volunteer.VolunteerCreateDTO;
import project.v1.entities.Campaign;
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
    // [x]: Verify if campaign is SCHEDULED or ACTIVE
    Campaign campaign = campaignService.findById(dto.getVolunteer().getCampaignId());

    if (!(campaign.getStatus() == CampaignStatusEnum.AWAITING || campaign.getStatus() == CampaignStatusEnum.ACTIVE)) {
      throw new BusinessException("Campanha não pode receber novos voluntários porque está inativa.", 400);
    }

    // [x]: Verify if user data already exists
    userService
        .findByEmailOrPhoneNumber(dto.getEmail(), dto.getPhoneNumber())
        .ifPresent(user -> {
          throw new BusinessException(MessageErrorEnum.USER_ALREADY_EXISTS.getMessage(), 409);
        });

    // [x]: Create user
    var userCreatedDto = UserMapper.fromVolunteerCreateToUserCreateDTO(dto);
    var user = userService.create(userCreatedDto);

    // [x]: With userId, create campaign volunteer
    campaignVolunteerService.bind(campaign, user);
  }
}
