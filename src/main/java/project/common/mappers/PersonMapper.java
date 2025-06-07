package project.common.mappers;

import project.v1.dtos.person.PersonCreateDTO;
import project.v1.dtos.person.PersonMinDTO;
import project.v1.entities.Person;

public class PersonMapper {
  public static Person fromDTO(PersonCreateDTO dto) {
    return Person.builder()
        .email(dto.getEmail())
        .phoneNumber(dto.getPhoneNumber())
        .name(dto.getName())
        .document(dto.getDocument())
        .build();
  }

  public static PersonMinDTO fromEntityToMinimal(Person person) {
    return PersonMinDTO.builder()
        .id(person.getId())
        .name(person.getName())
        .email(person.getEmail())
        .build();
  }
}
