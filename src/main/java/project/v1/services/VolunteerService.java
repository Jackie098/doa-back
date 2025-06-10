package project.v1.services;

import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import project.v1.dtos.volunteer.VolunteerCreateDTO;

@ApplicationScoped
public class VolunteerService {
  @Inject
  private UserService userService;
  @Inject
  private CampaignVolunteerService campaignVolunteerService;

  public void create(VolunteerCreateDTO dto) {
    // []: Verify if campaign is SCHEDULED or ACTIVE
    // []: Verify if user data already exists
    // []: Create user
    // []: With userId, create campaign volunteer
  }
}
