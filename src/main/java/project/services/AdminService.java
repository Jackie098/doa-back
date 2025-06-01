package project.services;

import java.util.List;

import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import project.dtos.agent.AgentDTO;

@ApplicationScoped
public class AdminService {
  @Inject
  private AgentService agentService;

  public List<AgentDTO> listAgents() {
    return agentService.listAgents();
  }
}
