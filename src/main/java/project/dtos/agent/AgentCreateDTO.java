package project.dtos.agent;

import org.hibernate.validator.constraints.Length;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import project.common.annotations.UnmaskNumber;
import project.common.utils.AppConstants;
import project.dtos.person.PersonCreateDTO;
import project.dtos.user.UserCreateDTO;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class AgentCreateDTO {
  @NotBlank
  @Length(max = AppConstants.SLUG_MAX_LENGTH)
  private String slug;

  @NotBlank
  @UnmaskNumber
  private String document;
  private String pixKey;
  private String url;

  @Valid
  private UserCreateDTO user;
  @Valid
  private PersonCreateDTO responsibleLegal;
}
