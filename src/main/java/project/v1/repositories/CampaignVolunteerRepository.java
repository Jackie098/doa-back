package project.v1.repositories;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import io.quarkus.hibernate.orm.panache.PanacheQuery;
import io.quarkus.hibernate.orm.panache.PanacheRepository;
import jakarta.enterprise.context.ApplicationScoped;
import project.common.database.Pageable;
import project.v1.dtos.common.PageDTO;
import project.v1.entities.CampaignVolunteer;

@ApplicationScoped
public class CampaignVolunteerRepository implements PanacheRepository<CampaignVolunteer> {

  public Pageable<CampaignVolunteer> listVolunteersByCampaign(Long campaignId, Boolean isAccepted, PageDTO pageDTO) {
    StringBuilder stringBuilder = new StringBuilder("campaign.id = :id");

    Map<String, Object> params = new HashMap<>();
    params.put("id", campaignId);

    if (isAccepted != null) {
      stringBuilder.append(" AND isAccepted = :isAccepted");
      params.put("isAccepted", isAccepted);
    }

    PanacheQuery<CampaignVolunteer> query = find(stringBuilder.toString(), params)
        .page(pageDTO.getPagination());

    return new Pageable<CampaignVolunteer>(query, pageDTO.getOneBasePage());
  }

  public List<CampaignVolunteer> listVolunteersInRange(Long campaignId, List<Long> ids) {
    return find("campaign.id = ?1 AND id IN ?2 AND isAccepted = false", campaignId, ids).list();
  }

  public Pageable<CampaignVolunteer> listCampaignsByVolunteer(Long userId, Boolean isAccepted, PageDTO pageDTO) {
    StringBuilder stringBuilder = new StringBuilder("user.id = :id");

    Map<String, Object> params = new HashMap<>();
    params.put("id", userId);

    if (isAccepted != null) {
      stringBuilder.append(" AND isAccepted = :isAccepted");
      params.put("isAccepted", isAccepted);
    }

    PanacheQuery<CampaignVolunteer> query = find(stringBuilder.toString(), params)
        .page(pageDTO.getPagination());

    return new Pageable<CampaignVolunteer>(query, pageDTO.getOneBasePage());
  }
}
