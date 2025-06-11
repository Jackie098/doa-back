package project.v1.services;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
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
  private CampaignVolunteerRepository campaignVolunteerRepository;

  public Optional<CampaignVolunteer> findBindByUser(Long userId, Long campaignId) {
    return campaignVolunteerRepository.find("user.id = ?1 AND campaign.id = ?2", userId, campaignId)
        .firstResultOptional();
  }

  public void bind(Campaign campaign, User user) {
    var entity = CampaignVolunteerMapper.fromRelatedToEntity(campaign, user);
    campaignVolunteerRepository.persist(entity);
  }

  public List<CampaignVolunteer> listVolunteersByCampaign(Long campaignId, Boolean isAccepted) {
    StringBuilder query = new StringBuilder("campaign.id = :id");

    Map<String, Object> params = new HashMap<>();
    params.put("id", campaignId);

    if (isAccepted != null) {
      query.append(" AND isAccepted = :isAccepted");
      params.put("isAccepted", isAccepted);
    }

    return campaignVolunteerRepository.find(query.toString(), params).list();
  }
}
