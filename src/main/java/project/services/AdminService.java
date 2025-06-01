package project.services;

import java.util.List;

import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import project.dtos.agent.AgentDTO;
import project.entities.enums.AgentStatusEnum;

@ApplicationScoped
public class AdminService {
  @Inject
  private AgentService agentService;

  public List<AgentDTO> listAgents(AgentStatusEnum status) {
    return agentService.listAgents(status);
  }
}
