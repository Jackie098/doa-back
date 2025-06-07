package project.v1.services;

import java.util.List;

import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.transaction.Transactional;
import project.common.exceptions.MessageErrorEnum;
import project.common.exceptions.customs.BusinessException;
import project.v1.dtos.agent.AgentDTO;
import project.v1.dtos.agent.AgentValidateDTO;
import project.v1.entities.CharityAgent;
import project.v1.entities.enums.AgentStatusEnum;

@ApplicationScoped
public class AdminService {
  @Inject
  private AgentService agentService;

  public List<AgentDTO> listAgents(AgentStatusEnum status) {
    return agentService.listAgents(status);
  }

  @Transactional
  public void validateAgent(AgentValidateDTO dto, Long agentId) {
    CharityAgent agent = agentService.findById(agentId)
        .orElseThrow(() -> new BusinessException(MessageErrorEnum.AGENT_NOT_FOUND.getMessage(), 404));

    agent.setStatus(AgentStatusEnum.ACTIVE);
    agent.getUser().setIsActive(true);
  }
}
