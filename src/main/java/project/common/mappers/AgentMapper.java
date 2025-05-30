package project.common.mappers;

import project.dtos.agent.AgentMinResponseDTO;
import project.dtos.agent.CreateAgentDataDTO;
import project.dtos.agent.CreateAgentResponseDTO;
import project.dtos.person.PersonMinResponseDTO;
import project.dtos.user.UserMinResponseDTO;
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

  public static CreateAgentResponseDTO fromEntityToResponse(User user, Person person, CharityAgent agent) {
    var userResponseDto = UserMinResponseDTO.builder()
        .id(user.getId())
        .email(user.getEmail())
        .name(user.getName())
        .type(user.getType())
        .createdAt(user.getCreatedAt())
        .build();

    var personResponseDto = PersonMinResponseDTO.builder()
        .id(user.getId())
        .name(user.getName())
        .email(user.getEmail())
        .build();

    var agentResponseDto = AgentMinResponseDTO.builder()
        .id(agent.getId())
        .status(agent.getStatus())
        .build();

    return CreateAgentResponseDTO.builder()
        .user(userResponseDto)
        .person(personResponseDto)
        .agent(agentResponseDto)
        .build();
  }
}
