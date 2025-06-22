package project.v1.dtos.campaignVolunteer;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.SuperBuilder;
import project.v1.dtos.campaign.CampaignDTO;

@Data
@SuperBuilder
@EqualsAndHashCode(callSuper = true)
public class CampaignVolunteerExtDTO extends CampaignVolunteerDTO {
  private CampaignDTO campaign;
}
