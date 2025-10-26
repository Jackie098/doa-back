package project.v1.dtos.campaignVolunteerRanking;

import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;
import lombok.Builder;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class VolunteerTicketsDTO {
    private Long collected;
    private Long donated;
}
