package project.common.mappers;

import java.util.List;

import project.dtos.agent.AgentMinResponseDTO;
import project.dtos.agent.CreateAgentDataDTO;
import project.dtos.agent.CreateAgentResponseDTO;
import project.dtos.agent.AgentResponseDTO;
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

  public static CreateAgentResponseDTO fromEntityToCreateResponse(CharityAgent agent) {
    var userResponseDto = UserMapper.fromEntityToMinimal(agent.getUser());
    var personResponseDto = PersonMapper.fromEntityToMinimal(agent.getLegalResponsible());
    var agentResponseDto = AgentMapper.fromEntityToMinimal(agent);

    return CreateAgentResponseDTO.builder()
        .user(userResponseDto)
        .person(personResponseDto)
        .agent(agentResponseDto)
        .build();
  }

  public static List<AgentResponseDTO> fromEntityToListResponseDTO(List<CharityAgent> agents) {
    return agents.stream()
        .map(agent -> fromEntityToResponse(agent))
        .toList();
  }

  public static AgentResponseDTO fromEntityToResponse(CharityAgent agent) {
    return AgentResponseDTO.builder()
        .id(agent.getId())
        .document(agent.getDocument())
        .status(agent.getStatus())
        .user(UserMapper.fromEntityToMinimal(agent.getUser()))
        .person(PersonMapper.fromEntityToMinimal(agent.getLegalResponsible()))
        .build();
  }

  public static AgentMinResponseDTO fromEntityToMinimal(CharityAgent agent) {
    return AgentMinResponseDTO.builder()
        .id(agent.getId())
        .status(agent.getStatus())
        .build();
  }
}
