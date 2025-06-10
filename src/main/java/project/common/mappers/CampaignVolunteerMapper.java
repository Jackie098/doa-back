package project.common.mappers;

import project.v1.entities.Campaign;
import project.v1.entities.CampaignVolunteer;
import project.v1.entities.User;

public class CampaignVolunteerMapper {
  public static CampaignVolunteer fromRelatedToEntity(Campaign campaign, User user) {
    return CampaignVolunteer.builder()
        .campaign(campaign)
        .user(user)
        .build();
  }
}
