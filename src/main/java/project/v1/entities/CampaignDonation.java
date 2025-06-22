package project.v1.entities;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import project.v1.entities.enums.CampaignDonationStatusEnum;

@Entity
@Table(name = "campaign_donations")
@NoArgsConstructor
@AllArgsConstructor
@Data
@Builder
@EqualsAndHashCode(callSuper = true)
public class CampaignDonation extends BaseEntity {
  @ManyToOne(optional = false)
  @JoinColumn(name = "campaign_id", nullable = false)
  private Campaign campaign;

  @ManyToOne(optional = false)
  @JoinColumn(name = "campaign_volunteer_id", nullable = false)
  private CampaignVolunteer volunteer;

  @Column(nullable = false)
  private String donorName;

  @Column(nullable = false)
  private String donorPhoneNumber;

  @Column(nullable = false)
  private Long ticketQuantity;

  @Enumerated(EnumType.STRING)
  @Column(nullable = false)
  @Builder.Default
  private CampaignDonationStatusEnum status = CampaignDonationStatusEnum.PENDING;

  @Builder.Default
  private Boolean isDonation = false;
}
