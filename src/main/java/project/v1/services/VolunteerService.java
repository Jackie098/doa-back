package project.v1.services;

import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.transaction.Transactional;
import project.common.database.Pageable;
import project.common.exceptions.MessageErrorEnum;
import project.common.exceptions.customs.BusinessException;
import project.common.exceptions.customs.ConflictException;
import project.common.exceptions.customs.NotFoundException;
import project.common.mappers.CampaignDonationMapper;
import project.common.mappers.CampaignVolunteerMapper;
import project.common.mappers.UserMapper;
import project.v1.dtos.campaignDonation.CampaignDonationCreateDTO;
import project.v1.dtos.campaignVolunteer.CampaignVolunteerExtDTO;
import project.v1.dtos.common.PageDTO;
import project.v1.dtos.volunteer.VolunteerCreateDTO;
import project.v1.entities.Campaign;
import project.v1.entities.CampaignDonation;
import project.v1.entities.CampaignVolunteer;
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
  @Inject
  private CampaignDonationService campaignDonationService;

  @Transactional
  public void create(VolunteerCreateDTO dto) {
    Campaign campaign = campaignService.findById(dto.getVolunteer().getCampaignId())
        .orElseThrow(() -> new NotFoundException(MessageErrorEnum.CAMPAIGN_NOT_FOUND.getMessage()));

    if (!(campaign.getStatus() == CampaignStatusEnum.SCHEDULED || campaign.getStatus() == CampaignStatusEnum.ACTIVE)) {
      throw new BusinessException(MessageErrorEnum.CAMPAIGN_NOT_RECEIVE_NEW_VOLUNTEER.getMessage(), 400);
    }

    userService
        .findByEmailOrPhoneNumber(dto.getEmail(), dto.getPhoneNumber())
        .ifPresent(user -> {
          throw new ConflictException(MessageErrorEnum.USER_ALREADY_EXISTS.getMessage());
        });

    var userCreatedDto = UserMapper.fromVolunteerCreateToUserCreateDTO(dto);
    var user = userService.create(userCreatedDto);

    campaignVolunteerService.bind(campaign, user);
  }

  @Transactional
  public void bindVolunteerCampaign(Long userId, Long campaignId) {
    campaignVolunteerService.findBindByUser(userId, campaignId).ifPresent((data) -> {
      throw new ConflictException(MessageErrorEnum.CAMPAIGN_VOLUNTEER_BIND_ALREADY_EXISTS.getMessage());
    });

    Campaign campaign = campaignService.findById(campaignId)
        .orElseThrow(() -> new NotFoundException(MessageErrorEnum.CAMPAIGN_NOT_FOUND.getMessage()));

    if (!(campaign.getStatus() == CampaignStatusEnum.SCHEDULED || campaign.getStatus() == CampaignStatusEnum.ACTIVE)) {
      throw new BusinessException(MessageErrorEnum.CAMPAIGN_NOT_RECEIVE_NEW_VOLUNTEER.getMessage(), 400);
    }

    User user = userService.findById(userId)
        .orElseThrow(() -> new NotFoundException(MessageErrorEnum.USER_NOT_FOUND.getMessage()));

    campaignVolunteerService.bind(campaign, user);
  }

  @Transactional
  public Pageable<CampaignVolunteerExtDTO> listCampaigns(Long userId, Boolean isAccepted, PageDTO pagination) {
    Pageable<CampaignVolunteer> campaignVolunteers = campaignVolunteerService.listCampaignsByVolunteer(userId,
        isAccepted, pagination);

    return CampaignVolunteerMapper.fromEntityToExtPageableDTO(campaignVolunteers);
  }

  @Transactional
  public void createDonation(Long userId, Long campaignId, CampaignDonationCreateDTO dto) {
    Campaign campaign = campaignService.findById(campaignId)
        .orElseThrow(() -> new NotFoundException(MessageErrorEnum.CAMPAIGN_NOT_FOUND.getMessage()));

    if (campaign.getStatus() != CampaignStatusEnum.ACTIVE) {
      throw new BusinessException(MessageErrorEnum.CAMPAIGN_NOT_ACTIVE_DOESNT_RECEIVE_DONATIONS.getMessage(), 400);
    }

    // TODO: Add validation if has availiable tickets yet

    CampaignVolunteer volunteer = campaignVolunteerService.findBindByUser(userId, campaignId)
        .orElseThrow(() -> new NotFoundException(MessageErrorEnum.CAMPAIGN_VOLUNTEER_BIND_NOT_EXISTS.getMessage()));

    if (volunteer.getIsAccepted().equals(false)) {
      throw new BusinessException(
          MessageErrorEnum.VOLUNTEER_NO_ACCEPTED_CANT_ADD_NEW_DONATION.getMessage(),
          403);
    }

    CampaignDonation newDonation = CampaignDonationMapper.fromDTO(dto, campaign, volunteer);
    campaignDonationService.create(newDonation);
  }
}
