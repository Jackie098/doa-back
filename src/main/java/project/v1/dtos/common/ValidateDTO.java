package project.v1.dtos.common;

import io.smallrye.common.constraint.NotNull;
import lombok.Data;

@Data
public class ValidateDTO {
  @NotNull
  private Boolean isValid;
}
