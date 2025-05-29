package project.dtos.agent;

import org.hibernate.validator.constraints.Length;

import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import project.common.utils.AppConstants;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class CreateAgentDataDTO {
  @NotBlank
  @Length(max = AppConstants.SLUG_MAX_LENGTH)
  private String slug;

  @NotBlank
  private String document;
  private String pixKey;
  private String url;
}
