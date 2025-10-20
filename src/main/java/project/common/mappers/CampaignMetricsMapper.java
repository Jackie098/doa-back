package project.common.mappers;

import java.util.List;

import project.common.database.Pageable;
import project.v1.dtos.campaignMetrics.CampaignMetricsDTO;
import project.v1.entities.CampaignMetrics;

public class CampaignMetricsMapper {
    public static CampaignMetricsDTO fromEntityToDTO(CampaignMetrics entity) {
      return CampaignMetricsDTO.builder()
          .campaignId(entity.getCampaignId())
          .ticketPrice(entity.getTicketPrice())
          .totalTickets(entity.getTotalTickets())
          .ticketsSold(entity.getTicketsSold())
          .ticketsAvailable(entity.getTicketsAvailable())
          .ticketsDonation(entity.getTicketsDonation())
          .ticketsPending(entity.getTicketsPending())
          .ticketsReceived(entity.getTicketsReceived())
          .ticketsSent(entity.getTicketsSent())
          .ticketsValidated(entity.getTicketsValidated())
          .ticketsRefused(entity.getTicketsRefused())
          .ticketsPickUp(entity.getTicketsPickUp())
          .amountTicketsSold(entity.getAmountTicketsSold())
          .amountTicketsPending(entity.getAmountTicketsPending())
          .amountTicketsReceived(entity.getAmountTicketsReceived())
          .amountTicketsSent(entity.getAmountTicketsSent())
          .amountTicketsValidated(entity.getAmountTicketsValidated())
          .amountTicketsGoal(entity.getAmountTicketsGoal())
          .tckPendingByCampGoal(entity.getTckPendingByCampGoal())
          .tckReceivedByCampGoal(entity.getTckReceivedByCampGoal())
          .tckValidatedByCampGoal(entity.getTckValidatedByCampGoal())
          .tckDonationByTotalCollected(entity.getTckDonationByTotalCollected())
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
