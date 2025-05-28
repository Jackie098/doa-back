package project.repositories;

import java.util.Optional;

import io.quarkus.hibernate.orm.panache.PanacheRepositoryBase;
import jakarta.enterprise.context.ApplicationScoped;
import project.entities.User;

@ApplicationScoped
public class UserRepository implements PanacheRepositoryBase<User, Long> {
  public Optional<User> findByEmailOrPhoneNumber(String email, String phoneNumber) {
    return find("email = ?1 OR phoneNumber = ?2", email, phoneNumber).firstResultOptional();
  }
}
