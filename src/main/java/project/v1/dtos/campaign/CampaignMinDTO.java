package project.v1.dtos.campaign;

import java.math.BigDecimal;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class CampaignMinDTO {
  private Long id;
  private String name;
  private BigDecimal ticketPrice;
}
