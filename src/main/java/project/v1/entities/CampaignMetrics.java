package project.v1.entities;

import java.math.BigDecimal;

import org.hibernate.annotations.Immutable;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.PostLoad;
import jakarta.persistence.Table;
import jakarta.persistence.Transient;
import lombok.Data;

@Entity
@Immutable
@Table(name = "campaign_metrics")
@Data
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
  public BigDecimal amountTicketsRefused;
  @Transient
  public BigDecimal amountTicketsGoal;

  @Transient
  public BigDecimal amountTicketsPendingPercentage;
  @Transient
  public BigDecimal amountTicketsReceivedPercentage;
  @Transient
  public BigDecimal amountTicketsValidatedPercentage;

  @PostLoad
  private void afterLoad() {
    BigDecimal ONE_HUNDRED = new BigDecimal(100);

    this.ticketsPickUp = ticketsSold.subtract(ticketsDonation);

    this.amountTicketsSold = ticketPrice.multiply(ticketsSold);
    this.amountTicketsPending = ticketPrice.multiply(ticketsPending);
    this.amountTicketsReceived = ticketPrice.multiply(ticketsReceived);
    this.amountTicketsSent = ticketPrice.multiply(ticketsSent);
    this.amountTicketsValidated = ticketPrice.multiply(ticketsValidated);
    this.amountTicketsRefused = ticketPrice.multiply(ticketsRefused);
    this.amountTicketsGoal = ticketPrice.multiply(totalTickets);

    this.amountTicketsPendingPercentage = ticketsPending.multiply(ONE_HUNDRED)
        .divide(totalTickets.multiply(ONE_HUNDRED)); // (ticketsPending * 100) / (totalTickets * 100); // retorna em
                                                     // decimal
    this.amountTicketsReceivedPercentage = ticketsReceived.multiply(ONE_HUNDRED)
        .divide(totalTickets.multiply(ONE_HUNDRED));
    this.amountTicketsValidatedPercentage = ticketsValidated.multiply(ONE_HUNDRED)
        .divide(totalTickets.multiply(ONE_HUNDRED));
  }
}
