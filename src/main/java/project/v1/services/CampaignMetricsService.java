package project.v1.services;

import java.util.List;
import java.util.Optional;

import project.common.database.Pageable;
import project.v1.dtos.common.PageDTO;

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

  public Pageable<CampaignMetrics> listMetricsInRange(List<Long> campaignIds, PageDTO pageDTO) {
    return repository.listInRange(campaignIds, pageDTO);
  }
}
