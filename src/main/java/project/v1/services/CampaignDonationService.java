package project.v1.services;

import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import project.v1.entities.CampaignDonation;
import project.v1.repositories.CampaignDonationRepository;

@ApplicationScoped
public class CampaignDonationService {
  @Inject
  private CampaignDonationRepository repository;

  public void create(CampaignDonation donation) {
    repository.persist(donation);
  }
}
