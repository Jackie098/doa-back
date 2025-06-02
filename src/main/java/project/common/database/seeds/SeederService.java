package project.common.database.seeds;

import java.time.Instant;

import io.quarkus.runtime.Startup;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.transaction.Transactional;
import project.entities.CharityAgent;
import project.entities.Person;
import project.entities.User;
import project.entities.enums.AgentStatusEnum;
import project.entities.enums.UserTypeEnum;

@Startup
@ApplicationScoped
public class SeederService {

  @Transactional
  public void seed() {
    if (User.count() > 0 || Person.count() > 0 || CharityAgent.count() > 0) {
      return;
    }

    // List<User> users = null;
    // List<Person> persons = null;
    // List<CharityAgent> agents = null;

    for (int i = 1; i <= 5; i++) {
      // Criar usuário
      User user = new User();
      user.setName("Agente " + i);
      user.setEmail("agente" + i + "@email.com");
      user.setPassword("$2a$12$AUBavi4Bm4tmciYwUKvK3O.RRvOx3LlSAe.fT8p3OE/ZLa8yEYU/q"); // bcrypt de "12345678"
      user.setPhoneNumber("8999111111" + i);
      user.setType(UserTypeEnum.CHARITY_AGENT);
      user.setIsActive(i % 2 == 0);
      user.setCreatedAt(Instant.now());
      user.setUpdatedAt(Instant.now());
      // users.add(user);
      user.persist();

      // Criar pessoa
      Person person = new Person();
      person.setEmail("person" + i + "@email.com");

      person.setName("Person " + i);
      person.setPhoneNumber("8999111111" + i);
      person.setDocument("1234567890" + i);
      person.setCreatedAt(Instant.now());
      person.setUpdatedAt(Instant.now());
      // persons.add(person);
      person.persist();

      // Criar agente
      CharityAgent agent = new CharityAgent();
      agent.setDocument("9876543210" + i);
      agent.setSlug("agent-" + i + "-slug");
      agent.setStatus(i % 2 == 0 ? AgentStatusEnum.ACTIVE : AgentStatusEnum.AWAITING_VALIDATION);
      agent.setUser(user);
      agent.setLegalResponsible(person);
      agent.setCreatedAt(Instant.now());
      agent.setUpdatedAt(Instant.now());
      // agents.add(agent);
      agent.persist();
    }

    // User.persist(users);
    // Person.persist(persons);
    // CharityAgent.persist(agents);
  }
}
