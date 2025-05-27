package project.entities;

import java.time.LocalDate;

import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import io.quarkus.hibernate.orm.panache.PanacheEntityBase;

public abstract class BaseEntity extends PanacheEntityBase {
  @CreationTimestamp
  public LocalDate createdAt;
  @UpdateTimestamp
  public LocalDate updatedAt;
}
