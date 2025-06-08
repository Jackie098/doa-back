package project.v1.entities;

import java.util.List;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.OneToMany;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import project.common.annotations.UnmaskNumber;
import project.v1.entities.enums.AgentStatusEnum;

@Entity
@Table(name = "charity_agents")
@NoArgsConstructor
@AllArgsConstructor
@Data
@Builder
@EqualsAndHashCode(callSuper = true)
public class CharityAgent extends BaseEntity {
  @OneToOne(optional = false)
  @JoinColumn(name = "legal_responsible_id", nullable = false)
  private Person legalResponsible;

  @OneToOne(optional = false)
  @JoinColumn(name = "user_id", nullable = false)
  private User user;

  @Column(nullable = false, unique = true)
  private String slug;

  private String url;

  @Enumerated(EnumType.STRING)
  @Column(nullable = false)
  @Builder.Default
  private AgentStatusEnum status = AgentStatusEnum.AWAITING_VALIDATION;

  @Column(unique = true)
  @UnmaskNumber
  private String document;

  @Column(unique = true)
  private String pixKey;

  @OneToMany(mappedBy = "agent")
  private List<Campaign> campaigns;
}
