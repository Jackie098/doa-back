package project.common.mappers;

import java.util.List;
import java.util.stream.Collectors;

import project.common.database.Pageable;
import project.v1.dtos.campaignDonation.CampaignDonationCreateDTO;
import project.v1.dtos.campaignDonation.CampaignDonationMinDTO;
import project.v1.dtos.campaignVolunteer.CampaignVolunteerMinDTO;
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

  public static CampaignDonationMinDTO fromEntityToMinimal(CampaignDonation entity) {
    CampaignVolunteerMinDTO volunteerDto = CampaignVolunteerMapper.fromEntityToMinimal(entity.getVolunteer());

    return CampaignDonationMinDTO.builder()
        .id(entity.getId())
        .donorName(entity.getDonorName())
        .ticketQuantity(entity.getTicketQuantity())
        .status(entity.getStatus())
        .isDonation(entity.getIsDonation())
        .createdAt(entity.getCreatedAt())
        .volunteer(volunteerDto)
        .build();
  }

  public static Pageable<CampaignDonationMinDTO> fromEntityToMinPageableDTO(Pageable<CampaignDonation> data) {
    List<CampaignDonationMinDTO> dto = data.getData().stream()
        .map(CampaignDonationMapper::fromEntityToMinimal)
        .collect(Collectors.toList());

    Pageable.PageableBuilder<CampaignDonationMinDTO> builder = Pageable.builder();
    builder.data(dto);
    builder.pageSize(data.getPageSize());
    builder.totalPages(data.getTotalPages());
    builder.totalElements(data.getTotalElements());
    builder.currentPage(data.getCurrentPage());

    return builder.build();
  }
}
