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
public class TicketStatusDTO {
    private BigDecimal pending;
    private BigDecimal received;
    private BigDecimal sent;
    private BigDecimal validated;
    private BigDecimal refused;
}