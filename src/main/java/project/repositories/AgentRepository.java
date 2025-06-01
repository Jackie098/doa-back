package project.repositories;

import java.util.Optional;

import io.quarkus.hibernate.orm.panache.PanacheRepository;
import jakarta.enterprise.context.ApplicationScoped;
import project.entities.CharityAgent;

@ApplicationScoped
public class AgentRepository implements PanacheRepository<CharityAgent> {
  public Optional<CharityAgent> findAgentByDocument(String document) {
    return find("document = ?1", document).firstResultOptional();
  }

  public Optional<CharityAgent> findAgentByDocumentOrPix(String document, String pix) {
    return find("document = ?1 OR pixKey = ?2", document, pix).firstResultOptional();
  }
}
