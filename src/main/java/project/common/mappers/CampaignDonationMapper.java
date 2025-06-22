package project.common.mappers;

import project.v1.dtos.campaignDonation.CampaignDonationCreateDTO;
import project.v1.entities.Campaign;
import project.v1.entities.CampaignDonation;
import project.v1.entities.CampaignVolunteer;

public class CampaignDonationMapper {
  public static CampaignDonation fromDTO(CampaignDonationCreateDTO dto, Campaign campaign,
      CampaignVolunteer volunteer) {
    return CampaignDonation.builder()
        .campaign(campaign)
        .volunteer(volunteer)
        .donorName(dto.getDonorName())
        .donorPhoneNumber(dto.getDonorPhoneNumber())
        .ticketQuantity(dto.getTicketQuantity())
        .status(dto.getStatus())
        .isDonation(dto.getIsDonation())
        .build();
  }
}
