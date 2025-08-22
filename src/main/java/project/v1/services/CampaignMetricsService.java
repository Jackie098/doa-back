package project.v1.services;

import java.util.Optional;

import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import project.v1.entities.CampaignMetrics;
import project.v1.repositories.CampaignMetricsRepository;

@ApplicationScoped
public class CampaignMetricsService {
  @Inject
  private CampaignMetricsRepository repository;

  public Optional<CampaignMetrics> findByCampaignId(Long campaignId) {
    return repository.findByCampaignId(campaignId);
  }
}
