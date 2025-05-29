package project.services;

import java.util.Optional;

import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.transaction.Transactional;
import jakarta.ws.rs.BadRequestException;
import project.common.mappers.AgentMapper;
import project.dtos.agent.CreateAgentDTO;
import project.entities.CharityAgent;
import project.repositories.AgentRepository;

@ApplicationScoped
public class AgentService {
  @Inject
  private UserService userService;
  @Inject
  private PersonService personService;

  @Inject
  private AgentRepository agentRepository;

  @Transactional
  public void createAgent(CreateAgentDTO dto) {
    userService
        .findByEmailOrPhoneNumber(dto.getUser().getEmail(), dto.getUser().getPhoneNumber())
        .ifPresent(user -> {
          throw new BadRequestException("Um usuário já existe com estes dados.");
        });

    personService
        .findByEmailOrDocumentOrPhone(dto.getResponsibleLegal().getEmail(),
            dto.getResponsibleLegal().getDocument(), dto.getResponsibleLegal().getPhoneNumber())
        .ifPresent(person -> {
          throw new BadRequestException("Um responsável legal já existe com estes dados.");
        });

    this.findByDocument(dto.getAgent().getDocument())
        .ifPresent(agent -> {
          throw new BadRequestException("Já existe um agente de caridade com este CNPJ.");
        });

    System.out.println("Não existe nenhum dado correspondente, o cadastro pode seguir daqui...");

    var user = userService.create(dto.getUser());
    System.out.println("user getId " + user.getId());
    var person = personService.create(dto.getResponsibleLegal());
    System.out.println("person getId " + person.getId());

    var agent = AgentMapper.fromDTO(dto.getAgent(), person, user);
    agentRepository.persist(agent);
  }

  public Optional<CharityAgent> findByDocument(String document) {
    return agentRepository.findAgentByDocument(document);
  }
}
