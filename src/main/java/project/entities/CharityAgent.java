package project.entities;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import project.entities.enums.AgentStatusEnum;

@Entity
@Table(name = "charity_agents")
public class CharityAgent extends BaseEntity {
  @Id
  @GeneratedValue
  public Long id;

  @Column(nullable = false)
  public Long legalResponsibleId;

  @Column(nullable = false)
  public Long userId;

  @Column(nullable = false, unique = true)
  public String slug;

  public String url;

  @Enumerated(EnumType.STRING)
  @Column(nullable = false)
  public AgentStatusEnum status;

  @Column(unique = true)
  public String document;

  public String pixKey;
}
