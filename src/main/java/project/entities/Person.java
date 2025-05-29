package project.entities;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "persons")
@NoArgsConstructor
@AllArgsConstructor
@Data
@Builder
@EqualsAndHashCode(callSuper = true)
public class Person extends BaseEntity {
  @Id
  @GeneratedValue
  public Long id;

  @Column(nullable = false, unique = true)
  public String email;

  @Column(nullable = false, unique = true)
  public String document;

  @Column(nullable = false)
  public String name;

  @Column(nullable = true, unique = true)
  public String phoneNumber;
}
