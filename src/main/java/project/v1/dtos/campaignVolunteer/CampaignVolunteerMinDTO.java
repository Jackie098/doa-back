package project.v1.dtos.campaignVolunteer;

import java.time.Instant;

import lombok.Data;
import lombok.experimental.SuperBuilder;
import project.v1.dtos.user.UserExtMinDTO;

@Data
@SuperBuilder
public class CampaignVolunteerMinDTO {
  private Long id;
  private Instant createdAt;
  private UserExtMinDTO user;
}
