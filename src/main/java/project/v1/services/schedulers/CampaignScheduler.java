package project.v1.services.schedulers;

import java.time.Instant;
import java.util.List;

import org.jboss.logging.Logger;

import io.quarkus.logging.Log;
import io.quarkus.panache.common.Parameters;
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
    LOG.infof("Executing Campaign Scheduler JOB... \n");
    List<Campaign> campaigns = campaignRepository
        .find("status = ?1 AND startDate <= ?2", CampaignStatusEnum.SCHEDULED, Instant.now()).list();

    for (Campaign item : campaigns) {
      item.setStatus(CampaignStatusEnum.ACTIVE);
      LOG.infof("Campaign active: %s \n Slug: %s \n Agent:", item.getName(), item.getSlug(), item.getAgent().getId());
    }
  }

  @Transactional
  @Scheduled(every = "1m")
  public void finishCampaign() {
    LOG.infof("Executing finishCampaign JOB... \n");

    Parameters params = Parameters.with("active", CampaignStatusEnum.ACTIVE)
        .and("now", Instant.now());

    List<Campaign> campaigns = campaignRepository
        .find("status = :active AND dueDate <= :now", params)
        .list();

    for (Campaign item : campaigns) {
      item.setStatus(CampaignStatusEnum.FINISHED);
      item.setFinishedDate(Instant.now());
      Log.infof("Finalizado a campanha: %s com data de vencimento em %s < que %s", item.getName(), item.getDueDate(),
          Instant.now());
    }
  }
}
