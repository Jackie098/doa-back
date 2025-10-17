package project.v1.dtos.agent;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class AgentValidSlugDTO {
  private Boolean isAvailable;
  private String suggestedSlug;
}
