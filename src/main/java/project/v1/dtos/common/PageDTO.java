package project.v1.dtos.common;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

@Data
@AllArgsConstructor
@Builder
public class PageDTO {
  private Integer page;
  private Integer size;
}
