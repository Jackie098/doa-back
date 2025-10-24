package project.v1.dtos.campaignMetrics;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class PercentageInfoDTO {
    private CampaignGoalPercentagesDTO vsCampaignGoal;
    private TotalCollectedPercentagesDTO vsTotalCollected;
}