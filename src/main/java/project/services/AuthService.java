package project.services;

import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import project.common.exceptions.MessageErrorEnum;
import project.common.exceptions.customs.BusinessException;
import project.common.mappers.AuthMapper;
import project.common.utils.PasswordUtils;
import project.common.utils.TokenUtils;
import project.dtos.auth.AuthCreateDTO;
import project.dtos.auth.AuthDTO;
import project.dtos.auth.AuthExtDTO;
import project.entities.CharityAgent;
import project.entities.User;
import project.entities.enums.UserTypeEnum;

@ApplicationScoped
public class AuthService {
  @Inject
  private UserService userService;
  @Inject
  private AgentService agentService;

  public AuthDTO signIn(AuthCreateDTO dto) {
    User user = userService.findByEmailOrPhoneNumber(dto.getEmail(), null)
        .orElseThrow(() -> new BusinessException(MessageErrorEnum.USER_NOT_FOUND.getMessage(), 404));

    if (PasswordUtils.checkPass(user.getPassword(), dto.getPassword())) {
      throw new BusinessException(MessageErrorEnum.USER_PASS_NOT_MATCH.getMessage(), 400);
    }

    String token = TokenUtils.generateToken(user);

    return AuthDTO.builder().accessToken(token).build();
  }

  public AuthExtDTO me(String email) {
    User user = userService.findByEmailOrPhoneNumber(email, null)
        .orElseThrow(() -> new BusinessException(MessageErrorEnum.USER_NOT_FOUND.getMessage(), 404));

    CharityAgent agent = null;
    if (user.getType().equals(UserTypeEnum.CHARITY_AGENT)) {
      agent = agentService.findByUser(user)
          .orElseThrow(() -> new BusinessException("Nenhum agent encontrado para este usuário", 404));
    }

    return AuthMapper.fromUserToDTO(user, agent);
  }
}
