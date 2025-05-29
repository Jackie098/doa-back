package project.common.mappers;

import project.dtos.agent.CreateAgentDataDTO;
import project.entities.CharityAgent;
import project.entities.Person;
import project.entities.User;

public class AgentMapper {
  public static CharityAgent fromDTO(CreateAgentDataDTO dto, Person person, User user) {
    return CharityAgent.builder()
        .legalResponsible(person)
        .user(user)
        .slug(dto.getSlug())
        .url(dto.getUrl())
        .document(dto.getDocument())
        .pixKey(dto.getPixKey())
        .build();
  }
}
