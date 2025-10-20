package project.v1.dtos.campaignMetrics;

import java.math.BigDecimal;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class CampaignMetricsDTO {
  private Long campaignId;
  private BigDecimal ticketPrice;
  private BigDecimal totalTickets;
  private BigDecimal ticketsSold;
  private BigDecimal ticketsAvailable;
  private BigDecimal ticketsDonation;
  private BigDecimal ticketsPending;
  private BigDecimal ticketsReceived;
  private BigDecimal ticketsSent;
  private BigDecimal ticketsValidated;
  private BigDecimal ticketsRefused;
  private BigDecimal ticketsPickUp;

  private BigDecimal amountTicketsSold;
  private BigDecimal amountTicketsPending;
  private BigDecimal amountTicketsReceived;
  private BigDecimal amountTicketsSent;
  private BigDecimal amountTicketsValidated;
  private BigDecimal amountTicketsGoal;

  private BigDecimal tckPendingByCampGoal;
  private BigDecimal tckReceivedByCampGoal;
  private BigDecimal tckValidatedByCampGoal;
  private BigDecimal tckDonationByTotalCollected;
}
