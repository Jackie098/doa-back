package project.v1.repositories;

import io.quarkus.hibernate.orm.panache.PanacheRepository;
import jakarta.enterprise.context.ApplicationScoped;
import project.v1.entities.Campaign;

@ApplicationScoped
public class CampaignRepository implements PanacheRepository<Campaign> {

}
