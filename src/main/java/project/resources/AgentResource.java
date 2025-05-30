package project.resources;

import jakarta.inject.Inject;
import jakarta.transaction.Transactional;
import jakarta.validation.Valid;
import jakarta.ws.rs.Consumes;
import jakarta.ws.rs.POST;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import project.dtos.agent.CreateAgentDTO;
import project.services.AgentService;

@Path("/agent")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class AgentResource {
  @Inject
  private AgentService service;

  @POST
  @Transactional
  public Response create(@Valid CreateAgentDTO dto) {
    service.createAgent(dto);

    return Response.noContent().build();
  }
}
