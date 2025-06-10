package project.v1.dtos.campaignVolunteer;

import io.smallrye.common.constraint.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class CampaignVolunteerCreateMinDTO {
  @NotNull
  private Long userId;
  private Long campaignId;
}
