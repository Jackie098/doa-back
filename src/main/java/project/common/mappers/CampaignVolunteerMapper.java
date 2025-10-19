package project.common.mappers;

import java.util.List;

import project.common.database.Pageable;
import project.v1.dtos.campaign.CampaignDTO;
import project.v1.dtos.campaignVolunteer.CampaignVolunteerDTO;
import project.v1.dtos.campaignVolunteer.CampaignVolunteerExtDTO;
import project.v1.dtos.user.UserDTO;
import project.v1.entities.Campaign;
import project.v1.entities.CampaignVolunteer;
import project.v1.entities.User;

public class CampaignVolunteerMapper {
  public static CampaignVolunteerDTO fromEntityToDTO(CampaignVolunteer data) {
    UserDTO userDto = UserMapper.fromEntityToDto(data.getUser());

    return CampaignVolunteerDTO.builder()
        .id(data.getId())
        .isAccepted(data.getIsAccepted())
        .user(userDto)
        .createdAt(data.getCreatedAt())
        .build();
  }

  public static CampaignVolunteerExtDTO fromEntityToExtDTO(CampaignVolunteer data) {
    UserDTO userDto = UserMapper.fromEntityToDto(data.getUser());
    CampaignDTO campaignDto = CampaignMapper.fromEntityToCampaignDTO(data.getCampaign());

    return CampaignVolunteerExtDTO.builder()
        .id(data.getId())
        .isAccepted(data.getIsAccepted())
        .user(userDto)
        .campaign(campaignDto)
        .createdAt(data.getCreatedAt())
        .build();
  }

  public static CampaignVolunteer fromRelatedToEntity(Campaign campaign, User user) {
    return CampaignVolunteer.builder()
        .campaign(campaign)
        .user(user)
        .build();
  }

  public static Pageable<CampaignVolunteerDTO> fromEntityToPageableDTO(Pageable<CampaignVolunteer> data) {
    List<CampaignVolunteerDTO> dto = data.getData().stream().map((item) -> {
      return fromEntityToDTO(item);
    }).toList();

    Pageable.PageableBuilder<CampaignVolunteerDTO> builder = Pageable.builder();
    builder.data(dto);
    builder.pageSize(data.getPageSize());
    builder.totalPages(data.getTotalPages());
    builder.totalElements(data.getTotalElements());
    builder.currentPage(data.getCurrentPage());

    return builder.build();
  }

  public static Pageable<CampaignVolunteerExtDTO> fromEntityToExtPageableDTO(Pageable<CampaignVolunteer> data) {
    List<CampaignVolunteerExtDTO> dto = data.getData().stream().map((item) -> {
      return fromEntityToExtDTO(item);
    }).toList();

    Pageable.PageableBuilder<CampaignVolunteerExtDTO> builder = Pageable.builder();
    builder.data(dto);
    builder.pageSize(data.getPageSize());
    builder.totalPages(data.getTotalPages());
    builder.totalElements(data.getTotalElements());
    builder.currentPage(data.getCurrentPage());

    return builder.build();
  }
}
