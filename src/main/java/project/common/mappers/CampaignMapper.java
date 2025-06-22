package project.common.mappers;

import java.util.List;

import jakarta.enterprise.context.ApplicationScoped;
import project.common.database.Pageable;
import project.v1.dtos.campaign.CampaignCreateDTO;
import project.v1.dtos.campaign.CampaignDTO;
import project.v1.entities.Campaign;
import project.v1.entities.CharityAgent;

@ApplicationScoped
public class CampaignMapper {
  public static Campaign fromDTO(CampaignCreateDTO dto, CharityAgent agent) {
    return Campaign.builder()
        .agent(agent)
        .slug(dto.getSlug())
        .name(dto.getName())
        .phoneNumber(dto.getPhoneNumber())
        .description(dto.getDescription())
        .addresLineOne(dto.getAddresLineOne())
        .addresLineTwo(dto.getAddresLineTwo())
        .addresLineThree(dto.getAddresLineThree())
        .totalTickets(dto.getTotalTickets())
        .ticketPrice(dto.getTicketPrice())
        // .eventType(dto.getType())
        .status(dto.getStatus())
        .startDate(dto.getStartDate())
        .dueDate(dto.getDueDate())
        .build();
  }

  public static CampaignDTO fromEntityToCampaignDTO(Campaign data) {
    return CampaignDTO.builder()
        .id(data.getId())
        .slug(data.getSlug())
        .name(data.getName())
        .phoneNumber(data.getPhoneNumber())
        .description(data.getDescription())
        .addresLineOne(data.getAddresLineOne())
        .addresLineTwo(data.getAddresLineTwo())
        .addresLineThree(data.getAddresLineThree())
        .status(data.getStatus())
        .type(data.getEventType())
        .totalTickets(data.getTotalTickets())
        .ticketPrice(data.getTicketPrice())
        .startDate(data.getStartDate())
        .dueDate(data.getDueDate())
        .createdDate(data.getCreatedAt())
        .build();
  }

  public static List<CampaignDTO> fromEntityToListCampaignDTO(List<Campaign> campaigns) {
    return campaigns.stream()
        .map(campaign -> fromEntityToCampaignDTO(campaign))
        .toList();
  }

  public static Pageable<CampaignDTO> fromEntityToPageableCampaignDTO(Pageable<Campaign> data) {
    List<CampaignDTO> dto = data.getData().stream()
        .map(campaign -> fromEntityToCampaignDTO(campaign))
        .toList();

    Pageable.PageableBuilder<CampaignDTO> builder = Pageable.builder();
    builder.data(dto);
    builder.pageSize(data.getPageSize());
    builder.totalPages(data.getTotalPages());
    builder.totalElements(data.getTotalElements());
    builder.currentPage(data.getCurrentPage());

    return builder.build();
  }
}
