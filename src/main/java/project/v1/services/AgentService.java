package project.v1.services;

import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.transaction.Transactional;
import project.common.database.Pageable;
import project.common.exceptions.MessageErrorEnum;
import project.common.exceptions.customs.BadRequestException;
import project.common.exceptions.customs.ConflictException;
import project.common.exceptions.customs.ForbiddenException;
import project.common.exceptions.customs.NotFoundException;
import project.common.mappers.AgentMapper;
import project.common.mappers.CampaignMapper;
import project.common.mappers.CampaignVolunteerMapper;
import project.v1.dtos.agent.AgentCreateDTO;
import project.v1.dtos.agent.AgentDTO;
import project.v1.dtos.campaign.CampaignCreateDTO;
import project.v1.dtos.campaign.CampaignDTO;
import project.v1.dtos.campaign.CampaignUpdateDTO;
import project.v1.dtos.campaignVolunteer.CampaignVolunteerDTO;
import project.v1.dtos.common.ManyReferencesDTO;
import project.v1.dtos.common.PageDTO;
import project.v1.entities.Campaign;
import project.v1.entities.CampaignVolunteer;
import project.v1.entities.CharityAgent;
import project.v1.entities.User;
import project.v1.entities.enums.AgentStatusEnum;
import project.v1.entities.enums.CampaignStatusEnum;
import project.v1.repositories.AgentRepository;

@ApplicationScoped
public class AgentService {
  @Inject
  private UserService userService;
  @Inject
  private PersonService personService;
  @Inject
  private CampaignService campaignService;
  @Inject
  private CampaignVolunteerService campaignVolunteerService;

  @Inject
  private AgentRepository agentRepository;

  public Pageable<AgentDTO> listAgents(AgentStatusEnum status, PageDTO pageDTO) {
    Pageable<CharityAgent> result = agentRepository.list(status, pageDTO);

    var mappedResult = AgentMapper.fromEntityToPageableResponseDTO(result);

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

  public Optional<CharityAgent> findByUser(User user) {
    return agentRepository.find("user", user).firstResultOptional();
  }

  @Transactional
  public AgentDTO createAgent(AgentCreateDTO dto) {
    userService
        .findByEmailOrPhoneNumber(dto.getUser().getEmail(), dto.getUser().getPhoneNumber())
        .ifPresent(user -> {
          throw new ConflictException(MessageErrorEnum.USER_ALREADY_EXISTS.getMessage());
        });

    personService
        .findByEmailOrDocumentOrPhone(dto.getResponsibleLegal().getEmail(),
            dto.getResponsibleLegal().getDocument(), dto.getResponsibleLegal().getPhoneNumber())
        .ifPresent(person -> {
          throw new ConflictException(MessageErrorEnum.LEGAL_RESPONSIBLE_ALREADY_EXISTS.getMessage());
        });

    this.findByDocumentAndPix(dto.getDocument(), dto.getPixKey())
        .ifPresent(agent -> {
          throw new ConflictException(MessageErrorEnum.AGENT_ALREADY_EXISTS.getMessage());
        });

    var user = userService.create(dto.getUser());
    var person = personService.create(dto.getResponsibleLegal());

    var agent = AgentMapper.fromDTO(dto, person, user);

    agentRepository.persistAndFlush(agent);

    return AgentMapper.fromEntityToAgentDTO(agent);
  }

  @Transactional
  public void disableFirstAccess(String email) {
    User user = userService.findByEmailOrPhoneNumber(email, null)
        .orElseThrow(() -> new NotFoundException(MessageErrorEnum.USER_NOT_FOUND.getMessage()));

    if (!user.getIsActive()) {
      throw new ForbiddenException(MessageErrorEnum.ACCOUNT_INVALID_TO_ACTION.getMessage());
    }

    user.setFirstAccess(false);
  }

  public Pageable<CampaignDTO> listCampaign(CampaignStatusEnum status, Long userId, PageDTO pageDTO) {
    var result = campaignService.list(status, userId, pageDTO);
    var mapped = CampaignMapper.fromEntityToPageableCampaignDTO(result);

    return mapped;
  }

  @Transactional
  public CampaignDTO createCampaign(CampaignCreateDTO dto) {
    Campaign campaign = campaignService.create(dto);

    CampaignDTO mapped = CampaignMapper.fromEntityToCampaignDTO(campaign);
    return mapped;
  }

  @Transactional
  public CampaignDTO updateCampaign(CampaignUpdateDTO dto) {
    Campaign campaign = campaignService.update(dto);

    CampaignDTO mapped = CampaignMapper.fromEntityToCampaignDTO(campaign);
    return mapped;
  }

  @Transactional
  public void reactivateCampaign(Long id, Long agentId) {
    campaignService.reactivate(id, agentId);
  }

  @Transactional
  public void pauseCampaign(Long id, Long agentId) {
    campaignService.pause(id, agentId);
  }

  @Transactional
  public void cancelCampaign(Long id, Long agentId) {
    campaignService.cancel(id, agentId);
  }

  @Transactional
  public void finishCampaign(Long id, Long agentId) {
    campaignService.finish(id, agentId);
  }

  @Transactional
  public Pageable<CampaignVolunteerDTO> listCampaignVolunteers(Long userId, Long campaingId, Boolean isAccepted,
      PageDTO pageDTO) {
    campaignService.findById(campaingId).ifPresentOrElse((data) -> {
      if (!data.getAgent().getUser().getId().equals(userId)) {
        throw new ForbiddenException(MessageErrorEnum.CAMPAIGN_DONT_BELONG_USER.getMessage());
      }
    }, () -> {
      throw new NotFoundException(MessageErrorEnum.CAMPAIGN_NOT_FOUND.getMessage());
    });

    Pageable<CampaignVolunteer> volunteers = campaignVolunteerService.listVolunteersByCampaign(campaingId,
        isAccepted, pageDTO);
    Pageable<CampaignVolunteerDTO> mapped = CampaignVolunteerMapper.fromEntityToPageableDTO(volunteers);

    return mapped;
  }

  @Transactional
  public void acceptVolunteers(Long userId, Long campaignId, ManyReferencesDTO dto) {
    // validar se campanha pertence ao usuário
    Campaign campaign = campaignService.findById(campaignId)
        .orElseThrow(() -> new NotFoundException(MessageErrorEnum.CAMPAIGN_NOT_FOUND.getMessage()));

    if (campaign.getAgent().getId() != userId) {
      throw new BadRequestException(MessageErrorEnum.CAMPAIGN_DONT_BELONG_USER.getMessage());
    }

    // validar cada id usuário selecionado
    List<CampaignVolunteer> volunteers = campaignVolunteerService.listVolunteersInRange(campaignId, dto);
    Set<Long> foundIds = volunteers.stream()
        .map(cv -> cv.getId())
        .collect(Collectors.toSet());

    List<Long> notFound = dto.getData().stream()
        .filter(id -> !foundIds.contains(id))
        .toList();

    if (!notFound.isEmpty()) {
      throw new BadRequestException(MessageErrorEnum.VOLUNTEERS_DONT_BELONGS_CAMPAIGN.getMessage());
    }

    // alterar todos pra accepted = true
    volunteers.forEach(v -> v.setIsAccepted(true));
  }
}
