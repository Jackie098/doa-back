package project.v1.repositories;

import java.util.Optional;

import io.quarkus.hibernate.orm.panache.PanacheRepository;
import jakarta.enterprise.context.ApplicationScoped;
import project.v1.entities.Person;

@ApplicationScoped
public class PersonRepository implements PanacheRepository<Person> {
  public Optional<Person> findByEmailOrDocumentOrPhone(String email, String document, String phoneNumber) {
    return find("email = ?1 OR document = ?2 OR phoneNumber = ?3", email, document, phoneNumber).firstResultOptional();
  }
}
