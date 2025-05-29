package project.common.mappers;

import project.dtos.agent.CreateAgentPersonDTO;
import project.entities.Person;

public class PersonMapper {
  public static Person fromDTO(CreateAgentPersonDTO dto) {
    return Person.builder()
        .email(dto.getEmail())
        .phoneNumber(dto.getPhoneNumber())
        .name(dto.getName())
        .document(dto.getDocument())
        .build();
  }
}
