package project.v1.dtos.common;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class ValidSlugDTO {
  private Boolean isAvailable;
  private String suggestedSlug;
}
