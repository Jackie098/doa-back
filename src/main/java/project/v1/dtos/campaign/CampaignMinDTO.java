package project.v1.dtos.campaign;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import project.v1.dtos.agent.AgentMinDTO;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class CampaignMinDTO {
  private Long id;
  private String name;
  private AgentMinDTO agent;
}
