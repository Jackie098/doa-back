package project.v1.services;

import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import project.v1.repositories.CampaignVolunteerRepository;

@ApplicationScoped
public class CampaignVolunteerService {
  @Inject
  private UserService userService;
  @Inject
  private CampaignVolunteerRepository campaignVolunteerRepository;
}
