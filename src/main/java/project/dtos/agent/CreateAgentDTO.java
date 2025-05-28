package project.dtos.agent;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import project.dtos.user.CreateUserDTO;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class CreateAgentDTO {
  private CreateUserDTO user;
  private CreateAgentDataDTO agent;
  private CreateAgentPersonDTO responsibleLegal;
}
