package project.common.mappers;

import java.util.List;

import project.v1.dtos.agent.AgentCreateDTO;
import project.v1.dtos.agent.AgentDTO;
import project.v1.dtos.agent.AgentMinDTO;
import project.v1.entities.CharityAgent;
import project.v1.entities.Person;
import project.v1.entities.User;

public class AgentMapper {
  public static CharityAgent fromDTO(AgentCreateDTO dto, Person person, User user) {
    return CharityAgent.builder()
        .slug(dto.getSlug())
        .url(dto.getUrl())
        .document(dto.getDocument())
        .pixKey(dto.getPixKey())
        .legalResponsible(person)
        .user(user)
        .build();
  }

  public static AgentDTO fromEntityToCreateResponse(CharityAgent data) {
    var userResponseDto = UserMapper.fromEntityToMinimal(data.getUser());
    var personResponseDto = PersonMapper.fromEntityToMinimal(data.getLegalResponsible());

    return AgentDTO.builder()
        .id(data.getId())
        .document(data.getDocument())
        .status(data.getStatus())
        .user(userResponseDto)
        .person(personResponseDto)
        .build();
  }

  public static List<AgentDTO> fromEntityToListResponseDTO(List<CharityAgent> agents) {
    return agents.stream()
        .map(agent -> fromEntityToResponse(agent))
        .toList();
  }

  public static AgentDTO fromEntityToResponse(CharityAgent agent) {
    return AgentDTO.builder()
        .id(agent.getId())
        .document(agent.getDocument())
        .status(agent.getStatus())
        .user(UserMapper.fromEntityToMinimal(agent.getUser()))
        .person(PersonMapper.fromEntityToMinimal(agent.getLegalResponsible()))
        .build();
  }

  public static AgentMinDTO fromEntityToMinimal(CharityAgent agent) {
    return AgentMinDTO.builder()
        .id(agent.getId())
        .document(agent.getDocument())
        .build();
  }
}
