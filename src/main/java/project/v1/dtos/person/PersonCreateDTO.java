package project.v1.dtos.person;

import org.hibernate.validator.constraints.Length;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import project.common.annotations.UnmaskNumber;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class PersonCreateDTO {
  @Email
  @NotBlank
  private String email;

  @NotBlank
  private String name;

  @NotBlank
  @UnmaskNumber
  private String document;

  @NotBlank
  @Length(min = 11, max = 14)
  @UnmaskNumber
  private String phoneNumber;
}
