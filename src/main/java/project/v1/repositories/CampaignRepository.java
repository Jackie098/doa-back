package project.v1.repositories;

import java.util.List;
import java.util.Optional;

import io.quarkus.hibernate.orm.panache.PanacheQuery;
import io.quarkus.hibernate.orm.panache.PanacheRepository;
import jakarta.enterprise.context.ApplicationScoped;
import project.common.database.Pageable;
import project.v1.dtos.common.PageDTO;
import project.v1.entities.Campaign;
import project.v1.entities.enums.CampaignStatusEnum;

@ApplicationScoped
public class CampaignRepository implements PanacheRepository<Campaign> {
  public Pageable<Campaign> list(Long userId, PageDTO pageDTO, CampaignStatusEnum status) {
    PanacheQuery<Campaign> query = null;

    if (status == null) {
      query = find("agent.user.id = ?1", userId);
    } else {
      query = find("agent.user.id = ?1 AND status = ?2", userId, status);
    }

    query.page(pageDTO.getPagination());

    return new Pageable<Campaign>(query, pageDTO.getOneBasePage());
  }

  public List<Campaign> listByAgentIdInRange(Long agentId, List<Long> campaignIds) {
    return find("agent.user.id = ?1 AND id IN ?2", agentId, campaignIds).list();
  }

  public Optional<Campaign> verifySlugAvailability(String slug, Long agentId) {
    return find("slug = ?1 AND agent.id = ?2", slug, agentId).firstResultOptional();
  }
}
