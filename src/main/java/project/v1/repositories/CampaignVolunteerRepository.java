package project.v1.repositories;

import java.util.HashMap;
import java.util.Map;

import io.quarkus.hibernate.orm.panache.PanacheQuery;
import io.quarkus.hibernate.orm.panache.PanacheRepository;
import io.quarkus.panache.common.Page;
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
        .page(Page.of(pageDTO.getPage(), pageDTO.getSize()));

    Pageable.PageableBuilder<CampaignVolunteer> builder = Pageable.builder();
    builder.page(query.list());
    builder.pageSize(query.list().size());
    builder.totalElements(query.count());
    builder.totalPages(query.pageCount());

    return builder.build();
  }
}
