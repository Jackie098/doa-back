package project.v1.repositories;

import java.util.Optional;

import io.quarkus.hibernate.orm.panache.PanacheQuery;
import io.quarkus.hibernate.orm.panache.PanacheRepository;
import jakarta.enterprise.context.ApplicationScoped;
import project.common.database.Pageable;
import project.v1.dtos.common.PageDTO;
import project.v1.entities.CharityAgent;
import project.v1.entities.enums.AgentStatusEnum;

@ApplicationScoped
public class AgentRepository implements PanacheRepository<CharityAgent> {
  public Pageable<CharityAgent> list(AgentStatusEnum status, PageDTO pageDTO) {
    PanacheQuery<CharityAgent> query = null;

    if (status == null) {
      query = findAll();
    } else {
      query = find("status", status);
    }

    query.page(pageDTO.getPagination());

    return new Pageable<CharityAgent>(query, pageDTO.getOneBasePage());
  }

  public Optional<CharityAgent> findAgentByDocument(String document) {
    return find("document = ?1", document).firstResultOptional();
  }

  public Optional<CharityAgent> findAgentByDocumentOrPix(String document, String pix) {
    return find("document = ?1 OR pixKey = ?2", document, pix).firstResultOptional();
  }
}
