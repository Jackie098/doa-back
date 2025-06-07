package project.v1.dtos.agent;

import io.smallrye.common.constraint.NotNull;
import lombok.Data;

@Data
public class AgentValidateDTO {
  @NotNull
  private Boolean isValid;
}
