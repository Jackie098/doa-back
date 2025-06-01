package project.services;

import java.util.Optional;

import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.transaction.Transactional;
import project.common.exceptions.MessageErrorEnum;
import project.common.exceptions.customs.BusinessException;
import project.common.mappers.AgentMapper;
import project.dtos.agent.CreateAgentDTO;
import project.dtos.agent.CreateAgentResponseDTO;
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
  public CreateAgentResponseDTO createAgent(CreateAgentDTO dto) {
    userService
        .findByEmailOrPhoneNumber(dto.getUser().getEmail(), dto.getUser().getPhoneNumber())
        .ifPresent(user -> {
          throw new BusinessException(MessageErrorEnum.USER_ALREADY_EXISTS.getMessage(), 409);
        });

    personService
        .findByEmailOrDocumentOrPhone(dto.getResponsibleLegal().getEmail(),
            dto.getResponsibleLegal().getDocument(), dto.getResponsibleLegal().getPhoneNumber())
        .ifPresent(person -> {
          throw new BusinessException(MessageErrorEnum.LEGAL_RESPONSIBLE_ALREADY_EXISTS.getMessage(), 409);
        });

    this.findByDocumentAndPix(dto.getAgent().getDocument(), dto.getAgent().getPixKey())
        .ifPresent(agent -> {
          throw new BusinessException(MessageErrorEnum.AGENT_ALREADY_EXISTS.getMessage(), 409);
        });

    var user = userService.create(dto.getUser());
    var person = personService.create(dto.getResponsibleLegal());

    var agent = AgentMapper.fromDTO(dto.getAgent(), person, user);

    agentRepository.persistAndFlush(agent);

    return AgentMapper.fromEntityToResponse(user, person, agent);
  }

  public Optional<CharityAgent> findByDocument(String document) {
    return agentRepository.findAgentByDocument(document);
  }

  public Optional<CharityAgent> findByDocumentAndPix(String document, String pix) {
    return agentRepository.findAgentByDocumentOrPix(document, pix);
  }
}
