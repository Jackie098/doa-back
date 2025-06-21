package project.v1.dtos.campaignVolunteer;

import java.time.Instant;

import lombok.Builder;
import lombok.Data;
import project.v1.dtos.campaign.CampaignDTO;
import project.v1.dtos.user.UserExtMinDTO;

@Data
@Builder
public class CampaignVolunteerExtDTO {
  private Long id;
  private Boolean isAccepted;
  private Instant createdAt;
  private UserExtMinDTO user;
  private CampaignDTO campaign;
}
