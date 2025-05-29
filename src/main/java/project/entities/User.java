package project.entities;

import org.hibernate.annotations.ColumnDefault;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import project.entities.enums.UserTypeEnum;

@Entity
@Table(name = "users")
@NoArgsConstructor
@AllArgsConstructor
@Data
@Builder
@EqualsAndHashCode(callSuper = true)
public class User extends BaseEntity {
  @Id
  @GeneratedValue
  private Long id;

  @Column(nullable = false, unique = true)
  private String email;

  @Column(nullable = false)
  private String password;

  @Column(nullable = false)
  private String name;

  private String avatarUrl;

  private String phoneNumber;

  @Enumerated(EnumType.STRING)
  @Column(nullable = false)
  private UserTypeEnum type;

  @Column(columnDefinition = "boolean default false")
  @Builder.Default
  private Boolean isActive = false;

  @Column(columnDefinition = "boolean default true")
  @Builder.Default
  private Boolean firstAccess = true;

  @Column(columnDefinition = "boolean default false")
  @Builder.Default
  private Boolean isCharityAgentMember = false;
}
