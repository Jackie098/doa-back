package project.v1.entities;

import java.math.BigDecimal;
import java.time.Instant;
import java.util.List;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import project.v1.entities.enums.CampaignStatusEnum;
import project.v1.entities.enums.CampaignTypeEnum;

@Entity
@Table(name = "campaigns")
@NoArgsConstructor
@AllArgsConstructor
@Data
@Builder
@EqualsAndHashCode(callSuper = true)
public class Campaign extends BaseEntity {
  @ManyToOne(optional = false)
  @JoinColumn(name = "charity_agent_id", nullable = false)
  private CharityAgent agent;

  @Column(nullable = false)
  private String slug;
  @Column(nullable = false)
  private String name;
  @Column(nullable = false)
  private String phoneNumber;
  private String description;
  private String addresLineOne; // Street, block, number
  private String addresLineTwo; // city, state
  private String addresLineThree; // complement
  @Column(nullable = false)
  private Integer totalTickets;
  @Column(nullable = false)
  private BigDecimal ticketPrice;

  @Enumerated(EnumType.STRING)
  @Column(nullable = false)
  @Builder.Default
  private CampaignStatusEnum status = CampaignStatusEnum.SCHEDULED;

  @Enumerated(EnumType.STRING)
  @Column(nullable = false)
  @Builder.Default
  private CampaignTypeEnum eventType = CampaignTypeEnum.PICK_UP;

  @Column(nullable = false)
  private Instant startDate;
  @Column(nullable = false)
  private Instant dueDate;
  private Instant finishedDate;

  @OneToMany(mappedBy = "campaign")
  private List<CampaignVolunteer> volunteers;

  @OneToMany(mappedBy = "campaign")
  private List<CampaignDonation> donations;
}
