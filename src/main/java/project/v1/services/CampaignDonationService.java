package project.v1.services;

import java.util.Optional;

import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import project.common.database.Pageable;
import project.v1.dtos.common.PageDTO;
import project.v1.entities.CampaignDonation;
import project.v1.entities.enums.CampaignDonationStatusEnum;
import project.v1.repositories.CampaignDonationRepository;

@ApplicationScoped
public class CampaignDonationService {
  @Inject
  private CampaignDonationRepository repository;

  public void create(CampaignDonation donation) {
    repository.persist(donation);
  }

  public Optional<CampaignDonation> findById(Long id) {
    return repository.findByIdOptional(id);
  }

  public Pageable<CampaignDonation> listByCampaign(Long campaignId, Long agentId, CampaignDonationStatusEnum status,
      PageDTO pageDTO) {
    return repository.listByCampaign(campaignId, agentId, status, pageDTO);
  }
}
