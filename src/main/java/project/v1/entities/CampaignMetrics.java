package project.v1.entities;

import java.math.BigDecimal;
import java.math.RoundingMode;

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
@Synchronize({ "campaign_donations", "campaigns" })
public class CampaignMetrics {
  @Id
  private Long campaignId;
  private BigDecimal ticketPrice;
  private BigDecimal totalTickets; // int
  private BigDecimal ticketsSold; // int
  private BigDecimal ticketsAvailable; // int

  private BigDecimal ticketsDonation; // Doação sem retirada //int

  private BigDecimal ticketsPending; // int
  private BigDecimal ticketsReceived; // int
  private BigDecimal ticketsSent; // int
  private BigDecimal ticketsValidated; // int
  private BigDecimal ticketsRefused; // int

  @Transient
  private BigDecimal ticketsPickUp; // Retirada no local //int
  @Transient
  private BigDecimal amountTicketsSold;
  @Transient
  private BigDecimal amountTicketsPending;
  @Transient
  private BigDecimal amountTicketsReceived;
  @Transient
  private BigDecimal amountTicketsSent;
  @Transient
  private BigDecimal amountTicketsValidated;
  @Transient
  private BigDecimal amountTicketsGoal;
  @Transient
  private BigDecimal amountTicketsDonation;

  @Transient
  private BigDecimal tckPendingByCampGoal;
  @Transient
  private BigDecimal tckSentByCampGoal;
  @Transient
  private BigDecimal tckReceivedByCampGoal;
  @Transient
  private BigDecimal tckValidatedByCampGoal;
  @Transient
  private BigDecimal tckDonationByTotalCollected;
  @Transient
  private BigDecimal tckPickUpByTotalCollected;

  @PostLoad
  private void afterLoad() {
    this.ticketsPickUp = ticketsSold.subtract(ticketsDonation); // ok

    this.amountTicketsSold = ticketPrice.multiply(ticketsSold);
    this.amountTicketsPending = ticketPrice.multiply(ticketsPending);
    this.amountTicketsReceived = ticketPrice.multiply(ticketsReceived);
    this.amountTicketsSent = ticketPrice.multiply(ticketsSent);
    this.amountTicketsValidated = ticketPrice.multiply(ticketsValidated);
    this.amountTicketsGoal = ticketPrice.multiply(totalTickets);
    this.amountTicketsDonation = ticketPrice.multiply(ticketsDonation);
    
    // percentage based on campaign goal - totalTickets
    this.tckPendingByCampGoal = ticketsPending
        .divide(totalTickets, 2, RoundingMode.FLOOR);
    this.tckSentByCampGoal = ticketsSent
        .divide(totalTickets, 2, RoundingMode.FLOOR);
    this.tckReceivedByCampGoal = ticketsReceived
        .divide(totalTickets, 2, RoundingMode.FLOOR);
    this.tckValidatedByCampGoal = ticketsValidated
        .divide(totalTickets, 2, RoundingMode.FLOOR);

    // percentage based on total collection - ticketsSold
    this.tckDonationByTotalCollected = ticketsDonation.divide(ticketsSold, 2, RoundingMode.FLOOR);
    this.tckPickUpByTotalCollected = ticketsPickUp.divide(ticketsSold, 2, RoundingMode.FLOOR);
  }
}
