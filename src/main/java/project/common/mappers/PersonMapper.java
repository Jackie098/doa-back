package project.common.mappers;

import project.dtos.person.CreatePersonDTO;
import project.entities.Person;

public class PersonMapper {
  public static Person fromDTO(CreatePersonDTO dto) {
    return Person.builder()
        .email(dto.getEmail())
        .phoneNumber(dto.getPhoneNumber())
        .name(dto.getName())
        .document(dto.getDocument())
        .build();
  }
}
