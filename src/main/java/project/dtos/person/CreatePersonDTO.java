package project.dtos.person;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class CreatePersonDTO {
  private String email;
  private String name;
  private String document;
  private String phoneNumber;
}
