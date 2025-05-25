package project.entities;

import java.time.LocalDate;

import io.quarkus.hibernate.orm.panache.PanacheEntityBase;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Entity
@Table(name = "users")
public class User extends PanacheEntityBase {
  @Id
  @GeneratedValue
  public Long id;

  public String email;
  public String password;
  public String name;
  public String avatarUrl;
  public String phoneNumber;
  public String type;
  public Boolean isActive;
  public Boolean firstAccess;
  public Boolean isCharityAgentMember;

  public LocalDate createdAt;
  public LocalDate updatedAt;
}
