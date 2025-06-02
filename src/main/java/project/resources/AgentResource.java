package project.resources;

import io.quarkus.security.Authenticated;
import jakarta.inject.Inject;
import jakarta.transaction.Transactional;
import jakarta.validation.Valid;
import jakarta.ws.rs.Consumes;
import jakarta.ws.rs.POST;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import project.common.requests.ResponseModel;
import project.dtos.agent.AgentCreateDTO;
import project.services.AgentService;

@Path("/agent")
@Authenticated
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class AgentResource {
  @Inject
  private AgentService service;

  @POST
  @Transactional
  public Response create(@Valid AgentCreateDTO dto) {
    var result = service.createAgent(dto);

    var response = ResponseModel.success(Response.Status.OK.getStatusCode(), result);

    return Response.ok(response).build();
  }
}
