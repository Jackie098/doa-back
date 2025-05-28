package project.dtos.agent;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class CreateAgentDataDTO {
  private String slug;
  private String document;
  private String pixKey;
  private String url;
}
