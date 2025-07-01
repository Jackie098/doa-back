package project.v1.repositories;

import java.util.Optional;

import io.quarkus.hibernate.orm.panache.PanacheRepository;
import jakarta.enterprise.context.ApplicationScoped;
import project.v1.entities.CampaignMetrics;

@ApplicationScoped
public class CampaignMetricsRepository implements PanacheRepository<CampaignMetrics> {
  public Optional<CampaignMetrics> findByCampaignId(Long id) {
    return find("campaignId", id).firstResultOptional();
  }
}
