package project.v1.services;

import java.util.Optional;

import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import project.common.database.Pageable;
import project.v1.dtos.campaignDonation.CampaignDonationUpdateDTO;
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

  public CampaignDonation updateWhenCampaignFinished(CampaignDonation donation, CampaignDonationUpdateDTO dto) {
    Optional.ofNullable(dto.getDonorName()).ifPresent(donation::setDonorName);
    Optional.ofNullable(dto.getDonorPhoneNumber()).ifPresent(donation::setDonorPhoneNumber);
    Optional.ofNullable(dto.getTicketQuantity()).ifPresent(donation::setTicketQuantity);
    Optional.ofNullable(dto.getStatus()).ifPresent(donation::setStatus);
    Optional.ofNullable(dto.getIsDonation()).ifPresent(donation::setIsDonation);

    return donation;
  }

  public boolean isEmptyUpdateDTO(CampaignDonationUpdateDTO dto) {
    return dto.getDonorName() == null &&
        dto.getDonorPhoneNumber() == null &&
        dto.getTicketQuantity() == null &&
        dto.getStatus() == null &&
        dto.getIsDonation() == null;
  }
}
