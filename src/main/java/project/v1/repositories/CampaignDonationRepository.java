package project.v1.repositories;

import io.quarkus.hibernate.orm.panache.PanacheQuery;
import io.quarkus.hibernate.orm.panache.PanacheRepositoryBase;
import jakarta.enterprise.context.ApplicationScoped;
import project.common.database.Pageable;
import project.v1.dtos.common.PageDTO;
import project.v1.entities.CampaignDonation;
import project.v1.entities.enums.CampaignDonationStatusEnum;

@ApplicationScoped
public class CampaignDonationRepository implements PanacheRepositoryBase<CampaignDonation, Long> {
  public Pageable<CampaignDonation> listByCampaign(Long campaignId, Long agentId,
      CampaignDonationStatusEnum status, PageDTO pageDTO) {
    PanacheQuery<CampaignDonation> query = null;

    if (status == null) {
      query = find("campaign.id = ?1 AND campaign.agent.user.id = ?2", campaignId, agentId);
    } else {
      query = find("campaign.id = ?1 AND campaign.agent.user.id = ?2 AND status = ?3", campaignId, agentId, status);
    }

    query.page(pageDTO.getPagination());

    return new Pageable<CampaignDonation>(query, pageDTO.getOneBasePage());
  }

}