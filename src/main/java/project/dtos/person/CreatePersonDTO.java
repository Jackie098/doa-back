package project.dtos.person;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class CreatePersonDTO {
  @Email
  @NotBlank
  private String email;

  @NotBlank
  private String name;

  @NotBlank
  private String document;

  @NotBlank
  private String phoneNumber;
}
