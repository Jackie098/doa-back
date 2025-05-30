package project.dtos.user;

import org.hibernate.validator.constraints.Length;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import project.common.annotations.UnmaskNumber;
import project.common.validators.EnumConstraint;
import project.entities.enums.UserTypeEnum;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class CreateUserDTO {
  @NotBlank
  @Email
  private String email;

  @NotBlank
  @Min(value = 8)
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
