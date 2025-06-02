package project.services;

import java.util.List;
import java.util.Optional;

import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.transaction.Transactional;
import project.common.exceptions.MessageErrorEnum;
import project.common.exceptions.customs.BusinessException;
import project.common.mappers.AgentMapper;
import project.dtos.agent.AgentDTO;
import project.dtos.agent.AgentCreateDTO;
import project.entities.CharityAgent;
import project.entities.User;
import project.entities.enums.AgentStatusEnum;
import project.repositories.AgentRepository;

@ApplicationScoped
public class AgentService {
  @Inject
  private UserService userService;
  @Inject
  private PersonService personService;

  @Inject
  private AgentRepository agentRepository;

  public List<AgentDTO> listAgents(AgentStatusEnum status) {
    List<CharityAgent> result = null;
    if (status == null) {
      result = agentRepository.listAll();
    } else {
      result = agentRepository.find("status", status).list();
    }

    var mappedResult = AgentMapper.fromEntityToListResponseDTO(result);

    return mappedResult;
  }

  public Optional<CharityAgent> findById(Long id) {
    return agentRepository.findByIdOptional(id);
  }

  public Optional<CharityAgent> findByDocument(String document) {
    return agentRepository.findAgentByDocument(document);
  }

  public Optional<CharityAgent> findByDocumentAndPix(String document, String pix) {
    return agentRepository.findAgentByDocumentOrPix(document, pix);
  }

  @Transactional
  public AgentDTO createAgent(AgentCreateDTO dto) {
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

    this.findByDocumentAndPix(dto.getDocument(), dto.getPixKey())
        .ifPresent(agent -> {
          throw new BusinessException(MessageErrorEnum.AGENT_ALREADY_EXISTS.getMessage(), 409);
        });

    var user = userService.create(dto.getUser());
    var person = personService.create(dto.getResponsibleLegal());

    var agent = AgentMapper.fromDTO(dto, person, user);

    agentRepository.persistAndFlush(agent);

    return AgentMapper.fromEntityToCreateResponse(agent);
  }

  @Transactional
  public void disableFirstAccess(String email) {
    User user = userService.findByEmailOrPhoneNumber(email, null)
        .orElseThrow(() -> new BusinessException(MessageErrorEnum.USER_NOT_FOUND.getMessage(), 404));

    if (!user.getIsActive()) {
      throw new BusinessException(MessageErrorEnum.ACCOUNT_INVALID_TO_ACTION.getMessage(), 403);
    }

    user.setFirstAccess(false);
  }
}
