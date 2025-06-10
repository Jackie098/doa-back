package project.v1.services;

import java.util.Optional;

import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import project.common.mappers.CampaignVolunteerMapper;
import project.v1.entities.Campaign;
import project.v1.entities.CampaignVolunteer;
import project.v1.entities.User;
import project.v1.repositories.CampaignVolunteerRepository;

@ApplicationScoped
public class CampaignVolunteerService {
  @Inject
  private UserService userService;
  @Inject
  private CampaignVolunteerRepository campaignVolunteerRepository;

  public Optional<CampaignVolunteer> findBindByUser(Long userId, Long campaignId) {
    return campaignVolunteerRepository.find("user.id = ?1 AND campaign.id = ?2", userId, campaignId)
        .firstResultOptional();
  }

  public void bind(Campaign campaign, User user) {
    var entity = CampaignVolunteerMapper.fromRelatedToEntity(campaign, user);
    campaignVolunteerRepository.persist(entity);
  }
}
