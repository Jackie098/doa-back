package project.services;

import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import project.common.exceptions.MessageErrorEnum;
import project.common.exceptions.customs.BusinessException;
import project.common.utils.PasswordUtils;
import project.common.utils.TokenUtils;
import project.dtos.auth.AuthCreateDTO;
import project.dtos.auth.AuthDTO;
import project.entities.User;

@ApplicationScoped
public class AuthService {
  @Inject
  private UserService userService;

  public AuthDTO signIn(AuthCreateDTO dto) {
    User user = userService.findByEmailOrPhoneNumber(dto.getEmail(), null)
        .orElseThrow(() -> new BusinessException(MessageErrorEnum.USER_NOT_FOUND.getMessage(), 404));

    if (PasswordUtils.checkPass(user.getPassword(), dto.getPassword())) {
      throw new BusinessException(MessageErrorEnum.USER_PASS_NOT_MATCH.getMessage(), 400);
    }

    // Generate token here
    String token = TokenUtils.generateToken(user);

    return AuthDTO.builder().token(token).build();
  }
}
