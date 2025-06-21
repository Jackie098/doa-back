package project.v1.entities;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import jakarta.persistence.UniqueConstraint;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Data
@Builder
@EqualsAndHashCode(callSuper = true)
@Entity
@Table(name = "campaign_volunteers", uniqueConstraints = {
    @UniqueConstraint(columnNames = { "campaign_id", "user_id" })
})
public class CampaignVolunteer extends BaseEntity {
  // FIXME: Add unique constraint campaignId AND userId

  @ManyToOne(optional = false)
  @JoinColumn(name = "campaign_id", nullable = false)
  private Campaign campaign;

  @ManyToOne(optional = false)
  @JoinColumn(name = "user_id", nullable = false)
  private User user;

  @Column(columnDefinition = "boolean default false")
  @Builder.Default
  private Boolean isAccepted = false;
}
