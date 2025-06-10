package project.v1.dtos.user;

import org.hibernate.validator.constraints.Length;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import project.common.annotations.UnmaskNumber;
import project.common.validators.EnumConstraint;
import project.v1.entities.enums.UserTypeEnum;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class UserCreateDTO {
  @NotBlank
  @Email
  private String email;

  @NotBlank
  @Length(min = 8, max = 16)
  private String password;

  private String avatarUrl;

  @NotBlank
  @EnumConstraint(enumClass = UserTypeEnum.class, message = "User type invalid")
  private String type;

  @NotBlank
  @Length(min = 11, max = 14)
  @UnmaskNumber
  private String phoneNumber;

  @NotBlank
  private String name;
}
