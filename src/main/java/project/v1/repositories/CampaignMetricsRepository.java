package project.v1.repositories;

import java.util.List;
import java.util.Optional;

import project.common.database.Pageable;
import project.v1.dtos.common.PageDTO;

import io.quarkus.hibernate.orm.panache.PanacheQuery;
import io.quarkus.hibernate.orm.panache.PanacheRepository;
import jakarta.enterprise.context.ApplicationScoped;
import project.v1.entities.CampaignMetrics;

@ApplicationScoped
public class CampaignMetricsRepository implements PanacheRepository<CampaignMetrics> {
  public Optional<CampaignMetrics> findByCampaignId(Long id) {
    return find("campaignId", id).firstResultOptional();
  }

  public Pageable<CampaignMetrics> listInRange(List<Long> campaignIds, PageDTO pageDTO) {
    PanacheQuery<CampaignMetrics> query = find("campaignId IN ?1", campaignIds);

    query.page(pageDTO.getPagination());

    return new Pageable<CampaignMetrics>(query, pageDTO.getOneBasePage());
  }
}
