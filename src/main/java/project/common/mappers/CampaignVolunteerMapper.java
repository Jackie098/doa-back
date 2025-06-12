package project.common.mappers;

import java.util.List;

import project.common.database.Pageable;
import project.v1.dtos.campaignVolunteer.CampaignVolunteerDTO;
import project.v1.dtos.user.UserExtMinDTO;
import project.v1.entities.Campaign;
import project.v1.entities.CampaignVolunteer;
import project.v1.entities.User;

public class CampaignVolunteerMapper {
  public static CampaignVolunteerDTO fromEntityToDTO(CampaignVolunteer data) {
    UserExtMinDTO userDto = UserMapper.fromEntityToExtMinimal(data.getUser());

    return CampaignVolunteerDTO.builder()
        .id(data.getId())
        .isAccepted(data.getIsAccepted())
        .user(userDto)
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
}
