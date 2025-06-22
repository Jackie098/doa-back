package project.v1.dtos.campaignVolunteer;

import java.time.Instant;

import lombok.Data;
import lombok.experimental.SuperBuilder;
import project.v1.dtos.user.UserExtMinDTO;

@Data
@SuperBuilder
public class CampaignVolunteerDTO {
  private Long id;
  private Boolean isAccepted;
  private Instant createdAt;
  private UserExtMinDTO user;
}
