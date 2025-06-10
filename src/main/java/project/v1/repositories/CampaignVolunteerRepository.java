package project.v1.repositories;

import io.quarkus.hibernate.orm.panache.PanacheRepository;
import jakarta.enterprise.context.ApplicationScoped;
import project.v1.entities.CampaignVolunteer;

@ApplicationScoped
public class CampaignVolunteerRepository implements PanacheRepository<CampaignVolunteer> {

}
