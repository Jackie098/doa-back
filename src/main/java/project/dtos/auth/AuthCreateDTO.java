package project.dtos.auth;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import project.common.validators.EnumConstraint;
import project.entities.enums.UserTypeEnum;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class AuthCreateDTO {
  @NotBlank
  @Email
  private String email;

  @NotBlank
  @Min(value = 8)
  private String password;

  @NotBlank
  @EnumConstraint(enumClass = UserTypeEnum.class, message = "Tipo de usuário inválido")
  private String type;
}
