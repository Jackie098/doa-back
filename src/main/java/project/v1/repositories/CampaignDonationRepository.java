package project.v1.repositories;

import io.quarkus.hibernate.orm.panache.PanacheRepositoryBase;
import jakarta.enterprise.context.ApplicationScoped;
import project.v1.entities.CampaignDonation;

@ApplicationScoped
public class CampaignDonationRepository implements PanacheRepositoryBase<CampaignDonation, Long> {

}