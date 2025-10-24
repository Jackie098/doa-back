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
public class TicketModeDTO {
    private BigDecimal donation; // Doação //int
    private BigDecimal pickUp; // Retirada no local //int
    private BigDecimal delivery; // Entrega //int
}
