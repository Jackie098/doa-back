package project.v1.services;

import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import project.common.exceptions.MessageErrorEnum;
import project.common.exceptions.customs.BadRequestException;
import project.common.exceptions.customs.NotFoundException;
import project.common.mappers.AuthMapper;
import project.common.utils.PasswordUtils;
import project.common.utils.TokenUtils;
import project.v1.dtos.auth.AuthCreateDTO;
import project.v1.dtos.auth.AuthDTO;
import project.v1.dtos.auth.AuthExtDTO;
import project.v1.entities.CharityAgent;
import project.v1.entities.User;
import project.v1.entities.enums.UserTypeEnum;

@ApplicationScoped
public class AuthService {
  @Inject
  private UserService userService;
  @Inject
  private AgentService agentService;

  public AuthDTO signIn(AuthCreateDTO dto) {
    User user = userService.findByEmailOrPhoneNumber(dto.getEmail(), null)
        .orElseThrow(() -> new NotFoundException(MessageErrorEnum.USER_NOT_FOUND.getMessage()));

    if (PasswordUtils.checkPass(user.getPassword(), dto.getPassword())) {
      throw new BadRequestException(MessageErrorEnum.USER_PASS_NOT_MATCH.getMessage());
    }

    String token = TokenUtils.generateToken(user);

    return AuthDTO.builder().accessToken(token).build();
  }

  public AuthExtDTO me(String email) {
    User user = userService.findByEmailOrPhoneNumber(email, null)
        .orElseThrow(() -> new NotFoundException(MessageErrorEnum.USER_NOT_FOUND.getMessage()));

    CharityAgent agent = null;
    if (user.getType().equals(UserTypeEnum.CHARITY_AGENT)) {
      agent = agentService.findByUser(user)
          .orElseThrow(() -> new BadRequestException(MessageErrorEnum.USER_LINKED_NON_EXISTENT_AGENT.getMessage()));
    }

    return AuthMapper.fromUserToDTO(user, agent);
  }
}
