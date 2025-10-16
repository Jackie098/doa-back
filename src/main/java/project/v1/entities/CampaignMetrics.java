package project.v1.entities;

import java.math.BigDecimal;

import org.hibernate.annotations.Immutable;
import org.hibernate.annotations.Synchronize;
import org.hibernate.annotations.View;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.PostLoad;
import jakarta.persistence.Table;
import jakarta.persistence.Transient;
import lombok.Data;
import project.v1.entities.views.CampaignMetricsView;

@Data
@Entity
@Table(name = "campaign_metrics")
@Immutable
@View(query = CampaignMetricsView.QUERY)
@Synchronize({"campaign_donations", "campaigns"})
public class CampaignMetrics {
  @Id
  public Long campaignId;
  public BigDecimal ticketPrice;
  public BigDecimal totalTickets; // int
  public BigDecimal ticketsSold; // int
  public BigDecimal ticketsAvailable; // int

  public BigDecimal ticketsDonation; // Doação sem retirada //int

  public BigDecimal ticketsPending; // int
  public BigDecimal ticketsReceived; // int
  public BigDecimal ticketsSent; // int
  public BigDecimal ticketsValidated; // int
  public BigDecimal ticketsRefused; // int

  @Transient
  public BigDecimal ticketsPickUp; // Retirada no local //int
  @Transient
  public BigDecimal amountTicketsSold;
  @Transient
  public BigDecimal amountTicketsPending;
  @Transient
  public BigDecimal amountTicketsReceived;
  @Transient
  public BigDecimal amountTicketsSent;
  @Transient
  public BigDecimal amountTicketsValidated;
  @Transient
  public BigDecimal amountTicketsGoal;

  @Transient
  public BigDecimal tckPendingByCampGoal;
  @Transient
  public BigDecimal tckReceivedByCampGoal;
  @Transient
  public BigDecimal tckValidatedByCampGoal;
  @Transient
  public BigDecimal tckDonationByTotalCollected;

  @PostLoad
  private void afterLoad() {
    this.ticketsPickUp = ticketsSold.subtract(ticketsDonation); // ok

    this.amountTicketsSold = ticketPrice.multiply(ticketsSold);
    this.amountTicketsPending = ticketPrice.multiply(ticketsPending);
    this.amountTicketsReceived = ticketPrice.multiply(ticketsReceived);
    this.amountTicketsSent = ticketPrice.multiply(ticketsSent);
    this.amountTicketsValidated = ticketPrice.multiply(ticketsValidated);
    this.amountTicketsGoal = ticketPrice.multiply(totalTickets);

    // percentage based on campaign goal - totalTickets
    this.tckPendingByCampGoal = ticketsPending
        .divide(totalTickets);
    this.tckReceivedByCampGoal = ticketsReceived
        .divide(totalTickets);
    this.tckValidatedByCampGoal = ticketsValidated
        .divide(totalTickets);

    // percentage based on total collection - ticketsSold
    this.tckDonationByTotalCollected = ticketsDonation.divide(ticketsSold);
  }
}
