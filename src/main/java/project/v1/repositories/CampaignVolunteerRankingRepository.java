package project.v1.repositories;

import java.util.List;

import io.quarkus.hibernate.orm.panache.Panache;
import io.quarkus.hibernate.orm.panache.PanacheRepository;
import jakarta.enterprise.context.ApplicationScoped;
import project.v1.entities.CampaignVolunteerRanking;
import project.v1.entities.queries.CampaignVolunteerRankingQuery;

@ApplicationScoped
public class CampaignVolunteerRankingRepository implements PanacheRepository<CampaignVolunteerRanking> {

  public List<CampaignVolunteerRanking> listCampaignVolunteerRanking(Long campaignId) {
    return Panache.getSession()
        .createNativeQuery(CampaignVolunteerRankingQuery.FIND_BY_CAMPAIGN_ID,
            CampaignVolunteerRanking.class)
        .setParameter("campaignId", campaignId)
        .getResultList();
  }
}