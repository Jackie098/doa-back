package project.v1.repositories;

import io.quarkus.hibernate.orm.panache.PanacheQuery;
import io.quarkus.hibernate.orm.panache.PanacheRepository;
import jakarta.enterprise.context.ApplicationScoped;
import project.common.database.Pageable;
import project.v1.dtos.common.PageDTO;
import project.v1.entities.Campaign;
import project.v1.entities.enums.CampaignStatusEnum;

@ApplicationScoped
public class CampaignRepository implements PanacheRepository<Campaign> {
  public Pageable<Campaign> list(CampaignStatusEnum status, Long userId, PageDTO pageDTO) {
    PanacheQuery<Campaign> query = null;

    if (status == null) {
      query = find("agent.user.id = ?1", userId);
    } else {
      query = find("agent.user.id = ?1 AND status = ?2", userId, status);
    }

    query.page(pageDTO.getPagination());

    return new Pageable<Campaign>(query, pageDTO.getOneBasePage());
  }
}
