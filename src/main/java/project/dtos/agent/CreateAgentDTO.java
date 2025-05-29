package project.dtos.agent;

import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import project.dtos.person.CreatePersonDTO;
import project.dtos.user.CreateUserDTO;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class CreateAgentDTO {
  @Valid
  private CreateUserDTO user;
  @Valid
  private CreateAgentDataDTO agent;
  @Valid
  private CreatePersonDTO responsibleLegal;
}
