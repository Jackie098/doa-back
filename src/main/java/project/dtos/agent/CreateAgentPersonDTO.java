package project.dtos.agent;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class CreateAgentPersonDTO {
  private String email;
  private String name;
  private String document;
  private String phoneNumber;
}
