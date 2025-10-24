package project.v1.dtos.campaignMetrics;

import java.math.BigDecimal;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class TicketInfoDTO {
    private BigDecimal price;
    private BigDecimal total;
    private BigDecimal sold;
    private BigDecimal available;

    private TicketModeDTO mode;
    private TicketStatusDTO status;

    private AmountInfoDTO amounts;
    private PercentageInfoDTO percentages;
}
