package project.v1.services;

import java.util.List;

import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import project.v1.entities.CampaignVolunteerRanking;
import project.v1.repositories.CampaignVolunteerRankingRepository;

@ApplicationScoped
public class CampaignVolunteerRankingService {
  @Inject
  private CampaignVolunteerRankingRepository repository;

  public List<CampaignVolunteerRanking> listCampaignVolunteerRanking(Long campaignId) {
    return repository.listCampaignVolunteerRanking(campaignId);
  }
}
