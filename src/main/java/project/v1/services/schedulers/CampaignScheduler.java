package project.v1.services.schedulers;

import java.time.Instant;
import java.util.List;

import org.jboss.logging.Logger;

import io.quarkus.scheduler.Scheduled;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.transaction.Transactional;
import project.v1.entities.Campaign;
import project.v1.entities.enums.CampaignStatusEnum;
import project.v1.repositories.CampaignRepository;

@ApplicationScoped
public class CampaignScheduler {
  private static final Logger LOG = Logger.getLogger(CampaignScheduler.class);

  @Inject
  private CampaignRepository campaignRepository;

  @Transactional
  @Scheduled(every = "1m")
  public void activateScheduledCampaings() {
    LOG.infof("Executing Campaign Scheduler... \n");
    List<Campaign> campaigns = campaignRepository
        .find("status = ?1 AND startDate <= ?2", CampaignStatusEnum.AWAITING, Instant.now()).list();

    for (Campaign item : campaigns) {
      item.setStatus(CampaignStatusEnum.ACTIVE);
      LOG.infof("Campaign active: %s \n Slug: %s \n Agent:", item.getName(), item.getSlug(), item.getAgent().getId());
    }
  }
}
