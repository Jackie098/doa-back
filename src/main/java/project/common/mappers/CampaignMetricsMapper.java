package project.common.mappers;

import java.math.BigDecimal;
import java.util.List;

import project.common.database.Pageable;
import project.v1.dtos.campaignMetrics.CampaignMetricsDTO;
import project.v1.dtos.campaignMetrics.AmountInfoDTO;
import project.v1.dtos.campaignMetrics.PercentageInfoDTO;
import project.v1.dtos.campaignMetrics.CampaignGoalPercentagesDTO;
import project.v1.dtos.campaignMetrics.TotalCollectedPercentagesDTO;
import project.v1.dtos.campaignMetrics.TicketInfoDTO;
import project.v1.dtos.campaignMetrics.TicketModeDTO;
import project.v1.dtos.campaignMetrics.TicketStatusDTO;
import project.v1.entities.CampaignMetrics;

public class CampaignMetricsMapper {
  public static CampaignMetricsDTO fromEntityToDTO(CampaignMetrics entity) {
    var amountInfoDTO = AmountInfoDTO.builder()
        .sold(entity.getAmountTicketsSold())
        .pending(entity.getAmountTicketsPending())
        .received(entity.getAmountTicketsReceived())
        .sent(entity.getAmountTicketsSent())
        .validated(entity.getAmountTicketsValidated())
        .total(entity.getAmountTicketsGoal())
        .donation(entity.getAmountTicketsDonation())
        .build();

    var percentageInfoDTO = PercentageInfoDTO.builder()
        .vsCampaignGoal(CampaignGoalPercentagesDTO.builder()
            .pending(entity.getTckPendingByCampGoal())
            .sent(entity.getTckSentByCampGoal())
            .received(entity.getTckReceivedByCampGoal())
            .validated(entity.getTckValidatedByCampGoal())
            .build())
        .vsTotalCollected(TotalCollectedPercentagesDTO.builder()
            .donation(entity.getTckDonationByTotalCollected())
            .pickUp(entity.getTckPickUpByTotalCollected())
            .build())
        .build();

    var ticketInfoDTO = TicketInfoDTO.builder()
        .price(entity.getTicketPrice())
        .total(entity.getTotalTickets())
        .sold(entity.getTicketsSold()) 
        .available(entity.getTicketsAvailable())
        .mode(TicketModeDTO.builder()
            .pickUp(entity.getTicketsPickUp())
            .donation(entity.getTicketsDonation())
            .delivery(BigDecimal.ZERO)
            .build())
        .status(TicketStatusDTO.builder()
            .pending(entity.getTicketsPending())
            .received(entity.getTicketsReceived())
            .sent(entity.getTicketsSent())
            .validated(entity.getTicketsValidated())
            .refused(entity.getTicketsRefused())
            .build())
        .amounts(amountInfoDTO)
        .percentages(percentageInfoDTO)
        .build();

    return CampaignMetricsDTO.builder()
        .campaignId(entity.getCampaignId())
        .tickets(ticketInfoDTO)
        .build();
  }

  public static List<CampaignMetricsDTO> fromEntityToListDTO(List<CampaignMetrics> entities) {
    return entities.stream()
        .map(entity -> fromEntityToDTO(entity))
        .toList();
  }

  public static Pageable<CampaignMetricsDTO> fromEntityToPageableDTO(Pageable<CampaignMetrics> data) {
    List<CampaignMetricsDTO> dto = data.getData().stream()
        .map(entity -> fromEntityToDTO(entity))
        .toList();

    Pageable.PageableBuilder<CampaignMetricsDTO> builder = Pageable.builder();
    builder.data(dto);
    builder.pageSize(data.getPageSize());
    builder.totalPages(data.getTotalPages());
    builder.totalElements(data.getTotalElements());
    builder.currentPage(data.getCurrentPage());

    return builder.build();
  }
}
