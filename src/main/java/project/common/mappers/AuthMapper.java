package project.common.mappers;

import project.dtos.agent.AgentMinDTO;
import project.dtos.auth.AuthExtDTO;
import project.entities.CharityAgent;
import project.entities.User;
import project.entities.enums.UserTypeEnum;

public class AuthMapper {
  public static AuthExtDTO fromUserToDTO(User user, CharityAgent agent) {
    AgentMinDTO agentBuild = null;

    if (user.getType().equals(UserTypeEnum.CHARITY_AGENT)) {
      agentBuild = AgentMinDTO.builder()
          .id(agent.getId())
          .document(agent.getDocument())
          .build();
    }

    var authExtBuild = AuthExtDTO.builder()
        .id(user.getId())
        .email(user.getEmail())
        .name(user.getName())
        .avatarUrl(user.getAvatarUrl())
        .phoneNumber(user.getPhoneNumber())
        .type(user.getType())
        .createdAt(user.getCreatedAt())
        .isActive(user.getIsActive())
        .firstAccess(user.getFirstAccess())
        .isCharityAgentMember(user.getIsCharityAgentMember())
        .agent(agentBuild)
        .build();

    return authExtBuild;
  }
}
