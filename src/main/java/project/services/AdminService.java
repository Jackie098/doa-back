package project.services;

import java.util.List;

import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import project.dtos.agent.AgentResponseDTO;

@ApplicationScoped
public class AdminService {
  @Inject
  private AgentService agentService;

  public List<AgentResponseDTO> listAgents() {
    return agentService.listAgents();
  }
}
