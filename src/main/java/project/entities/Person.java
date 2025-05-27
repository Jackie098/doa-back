package project.entities;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Entity
@Table(name = "persons")
public class Person extends BaseEntity {
  @Id
  @GeneratedValue
  public Long id;

  @Column(nullable = false, unique = true)
  public String email;

  @Column(nullable = false, unique = true)
  public String document;

  @Column(nullable = false, unique = true)
  public String name;

  @Column(nullable = true, unique = true)
  public String phoneNumber;
}
