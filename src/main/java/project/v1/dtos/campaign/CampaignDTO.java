package project.v1.dtos.campaign;

import java.math.BigDecimal;
import java.time.Instant;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import project.common.annotations.Mask;
import project.common.annotations.enums.MaskType;
import project.v1.entities.enums.CampaignStatusEnum;
import project.v1.entities.enums.CampaignTypeEnum;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class CampaignDTO {
  private Long id;
  private String slug;
  private String name;

  @Mask(MaskType.PHONE)
  private String phoneNumber;

  private String description;
  private String addresLineOne; // Street, block, number
  private String addresLineTwo; // city, state
  private String addresLineThree; // complement

  private CampaignStatusEnum status;
  private CampaignTypeEnum type;

  private Integer totalTickets;

  private BigDecimal ticketPrice;

  private Instant startDate;
  private Instant dueDate;
  private Instant createdDate;
}
