package project.services;

import java.util.Optional;

import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import project.common.mappers.PersonMapper;
import project.dtos.person.CreatePersonDTO;
import project.entities.Person;
import project.repositories.PersonRepository;

@ApplicationScoped
public class PersonService {
  @Inject
  private PersonRepository personRepository;

  public Optional<Person> findByEmailOrDocumentOrPhone(String email, String document, String phoneNumber) {
    return personRepository.findByEmailOrDocumentOrPhone(email, document, phoneNumber);
  }

  public Person create(CreatePersonDTO dto) {
    Person person = PersonMapper.fromDTO(dto);

    personRepository.persist(person);

    return person;
  }
}
