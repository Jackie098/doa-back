package project.v1.services;

import java.util.Optional;

import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import project.common.mappers.PersonMapper;
import project.v1.dtos.person.PersonCreateDTO;
import project.v1.entities.Person;
import project.v1.repositories.PersonRepository;

@ApplicationScoped
public class PersonService {
  @Inject
  private PersonRepository personRepository;

  public Optional<Person> findByEmailOrDocumentOrPhone(String email, String document, String phoneNumber) {
    return personRepository.findByEmailOrDocumentOrPhone(email, document, phoneNumber);
  }

  public Person create(PersonCreateDTO dto) {
    Person person = PersonMapper.fromDTO(dto);

    personRepository.persist(person);

    return person;
  }
}
