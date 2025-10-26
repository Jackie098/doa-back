package project.v1.dtos.campaignVolunteerRanking;

import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;
import lombok.Builder;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class VolunteerRawRankingDTO {
    private Long volunteerId;
    private String name;
    private VolunteerTicketsDTO tickets;
}
