package project.v1.dtos.campaignVolunteer;

import io.smallrye.common.constraint.NotNull;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
public class CampaignVolunteerCreateDTO {
  @NotNull
  private Long userId;
  @NotNull
  private Long campaignId;
}
