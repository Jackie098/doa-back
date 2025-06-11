package project.common.database.seeds;

import java.math.BigDecimal;
import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import java.util.concurrent.ThreadLocalRandom;

import io.quarkus.runtime.Startup;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.transaction.Transactional;
import project.v1.entities.Campaign;
import project.v1.entities.CampaignVolunteer;
import project.v1.entities.CharityAgent;
import project.v1.entities.Person;
import project.v1.entities.User;
import project.v1.entities.enums.AgentStatusEnum;
import project.v1.entities.enums.CampaignStatusEnum;
import project.v1.entities.enums.CampaignTypeEnum;
import project.v1.entities.enums.UserTypeEnum;

@Startup
@ApplicationScoped
public class SeederService {
  private final Integer QUANTITY_AGENTS = 30;
  private final Integer QUANTITY_CAMPAIGNS_BY_ACTIVE_AGENT = 4;
  private final Integer QUANTITY_VOLUNTEERS = 100;
  private final Integer QUANTITY_VOLUNTEERS_SPECIFIC_CAMPAIGN = 20;
  private final Integer VOLUNTEERS_SPECIFIC_CAMPAIGN_ID = 16;

  @Transactional
  public void seed() {
    if (User.count() > 0 || Person.count() > 0 || CharityAgent.count() > 0 || Campaign.count() > 0) {
      return;
    }

    List<User> users = new ArrayList<>();
    List<Person> people = new ArrayList<>();
    List<CharityAgent> agents = new ArrayList<>();
    List<Campaign> campaigns = new ArrayList<>();
    List<CampaignVolunteer> campaignVolunteers = new ArrayList<>();

    for (int i = 1; i <= QUANTITY_AGENTS; i++) {
      User user = new User();
      user.setName("Agente " + i);
      user.setEmail("agente" + i + "@email.com");
      user.setPassword("$2a$12$AUBavi4Bm4tmciYwUKvK3O.RRvOx3LlSAe.fT8p3OE/ZLa8yEYU/q");
      user.setPhoneNumber("8999111111" + i);
      user.setType(UserTypeEnum.CHARITY_AGENT);
      user.setIsActive(i % 2 == 0);
      user.setCreatedAt(Instant.now());
      user.setUpdatedAt(Instant.now());
      users.add(user);

      Person person = new Person();
      person.setEmail("person" + i + "@email.com");
      person.setName("Person " + i);
      person.setPhoneNumber("8999111111" + i);
      person.setDocument("1234567890" + i);
      person.setCreatedAt(Instant.now());
      person.setUpdatedAt(Instant.now());
      people.add(person);

      CharityAgent agent = new CharityAgent();
      agent.setDocument("9876543210" + i);
      agent.setSlug("agent-" + i + "-slug");
      agent.setStatus(i % 2 == 0 ? AgentStatusEnum.ACTIVE : AgentStatusEnum.AWAITING_VALIDATION);
      agent.setUser(user);
      agent.setLegalResponsible(person);
      agent.setCreatedAt(Instant.now());
      agent.setUpdatedAt(Instant.now());
      agents.add(agent);

      if (agent.getStatus() == AgentStatusEnum.ACTIVE) {
        for (int j = 1; j <= QUANTITY_CAMPAIGNS_BY_ACTIVE_AGENT; j++) {
          CampaignStatusEnum status;
          Instant startDate;
          Instant dueDate;
          Instant finishedDate = null;

          if (j <= 3) {
            status = CampaignStatusEnum.SCHEDULED;
            startDate = Instant.now().plus(7, ChronoUnit.DAYS);
            dueDate = startDate.plus(15, ChronoUnit.DAYS);
          } else if (j <= 6) {
            status = CampaignStatusEnum.ACTIVE;
            startDate = Instant.now();
            dueDate = startDate.plus(15, ChronoUnit.DAYS);
          } else if (j <= 7) {
            status = CampaignStatusEnum.CANCELED;
            startDate = Instant.now().minus(20, ChronoUnit.DAYS);
            dueDate = startDate.plus(10, ChronoUnit.DAYS);
            finishedDate = Instant.now().minus(5, ChronoUnit.DAYS);
          } else {
            status = CampaignStatusEnum.PAUSED;
            startDate = Instant.now().minus(10, ChronoUnit.DAYS);
            dueDate = startDate.plus(15, ChronoUnit.DAYS);
          }

          String name = "Campanha " + j + " do Agente " + i;
          String rawSlug = name.toLowerCase(Locale.ROOT).replaceAll("[^a-z0-9 ]", "").replaceAll(" ", "-");
          String slug = rawSlug.length() > 25 ? rawSlug.substring(0, 25) : rawSlug;

          Campaign campaign = new Campaign();
          campaign.setAgent(agent);
          campaign.setName(name);
          campaign.setSlug(slug);
          campaign.setPhoneNumber("8999111111" + i);
          campaign.setDescription("Descrição da " + name);
          campaign.setAddresLineOne("Rua Exemplo, 123");
          campaign.setAddresLineTwo("Cidade, Estado");
          campaign.setAddresLineThree("Complemento");
          campaign.setTotalTickets(100 * j);
          campaign.setTicketPrice(BigDecimal.TEN.multiply(BigDecimal.valueOf(j)));
          campaign.setStatus(status);
          campaign.setEventType(CampaignTypeEnum.PICK_UP);
          campaign.setStartDate(startDate);
          campaign.setDueDate(dueDate);
          campaign.setFinishedDate(finishedDate);
          campaign.setCreatedAt(Instant.now());
          campaign.setUpdatedAt(Instant.now());
          campaigns.add(campaign);
        }
      }
    }

    for (int i = 1; i <= QUANTITY_VOLUNTEERS; i++) {
      User user = new User();
      user.setName("Voluntário " + i);
      user.setEmail("vol" + i + "@email.com");
      user.setPassword("$2a$12$AUBavi4Bm4tmciYwUKvK3O.RRvOx3LlSAe.fT8p3OE/ZLa8yEYU/q");
      user.setPhoneNumber("8699111111" + i);
      user.setType(UserTypeEnum.VOLUNTEER);
      user.setIsActive(i % 2 == 0);
      user.setCreatedAt(Instant.now());
      user.setUpdatedAt(Instant.now());
      users.add(user);

      int randomIndex = ThreadLocalRandom.current().nextInt(0, campaigns.size());
      Campaign campaign = campaigns.get(randomIndex);

      CampaignVolunteer campaignVolunteer = new CampaignVolunteer();
      campaignVolunteer.setUser(user);
      campaignVolunteer.setCampaign(campaign);
      campaignVolunteer.setIsAccepted((randomIndex + 1) % 2 == 0);
      campaignVolunteers.add(campaignVolunteer);
    }

    for (int i = 1; i <= QUANTITY_VOLUNTEERS_SPECIFIC_CAMPAIGN; i++) {
      User user = new User();
      user.setName("Voluntário spec" + i);
      user.setEmail("vol_spec" + i + "@email.com");
      user.setPassword("$2a$12$AUBavi4Bm4tmciYwUKvK3O.RRvOx3LlSAe.fT8p3OE/ZLa8yEYU/q");
      user.setPhoneNumber("8299111111" + i);
      user.setType(UserTypeEnum.VOLUNTEER);
      user.setIsActive(i % 2 == 0);
      user.setCreatedAt(Instant.now());
      user.setUpdatedAt(Instant.now());
      users.add(user);

      Campaign campaign = campaigns.get(VOLUNTEERS_SPECIFIC_CAMPAIGN_ID);

      CampaignVolunteer campaignVolunteer = new CampaignVolunteer();
      campaignVolunteer.setUser(user);
      campaignVolunteer.setCampaign(campaign);
      campaignVolunteer.setIsAccepted(i % 2 == 0);
      campaignVolunteers.add(campaignVolunteer);
    }

    User.persist(users);
    Person.persist(people);
    CharityAgent.persist(agents);
    Campaign.persist(campaigns);
    CampaignVolunteer.persist(campaignVolunteers);

    saveAdmin();
  }

  public void saveAdmin() {
    User user = new User();
    user.setName("Carlos Augusto");
    user.setEmail("carlosmiranda19122@gmail.com");
    user.setPassword("$2a$12$AUBavi4Bm4tmciYwUKvK3O.RRvOx3LlSAe.fT8p3OE/ZLa8yEYU/q"); // bcrypt de "12345678"
    user.setPhoneNumber("89994138240");
    user.setType(UserTypeEnum.ADM);
    user.setIsActive(true);
    user.setCreatedAt(Instant.now());
    user.setUpdatedAt(Instant.now());
    user.persist();
  }
}
