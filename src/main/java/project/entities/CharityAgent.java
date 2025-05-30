package project.entities;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import project.common.annotations.UnmaskNumber;
import project.entities.enums.AgentStatusEnum;

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
  public Person legalResponsible;

  @OneToOne(optional = false)
  @JoinColumn(name = "user_id", nullable = false)
  public User user;

  @Column(nullable = false, unique = true)
  public String slug;

  public String url;

  @Enumerated(EnumType.STRING)
  @Column(nullable = false)
  @Builder.Default
  public AgentStatusEnum status = AgentStatusEnum.AWAITING_VALIDATION;

  @Column(unique = true)
  @UnmaskNumber
  public String document;

  @Column(unique = true)
  public String pixKey;
}
