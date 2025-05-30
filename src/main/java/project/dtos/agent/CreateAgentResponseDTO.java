package project.dtos.agent;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import project.dtos.person.PersonMinResponseDTO;
import project.dtos.user.UserMinResponseDTO;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class CreateAgentResponseDTO {
  private UserMinResponseDTO user;
  private PersonMinResponseDTO person;
  private AgentMinResponseDTO agent;
}
