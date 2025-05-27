package project.entities;

import java.time.LocalDate;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import project.entities.enums.UserTypeEnum;

@Entity
@Table(name = "users")
public class TesteClass {
  @Id
  @GeneratedValue
  public Long id;

  @Column(nullable = false, unique = true)
  public String email;

  @Column(nullable = false, unique = true)
  public String password;

  @Column(nullable = false, unique = true)
  public String name;

  public String avatarUrl;

  public String phoneNumber;

  @Enumerated(EnumType.STRING)
  @Column(nullable = false)
  public UserTypeEnum type;

  @Column(columnDefinition = "boolean default true")
  public Boolean isActive;
  public Boolean firstAccess;
  public Boolean isCharityAgentMember;

  public LocalDate createdAt;
  public LocalDate updatedAt;
}
