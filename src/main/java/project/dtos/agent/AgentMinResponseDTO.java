package project.dtos.agent;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import project.entities.enums.AgentStatusEnum;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class AgentMinResponseDTO {
  private Long id;
  private AgentStatusEnum status;
}
