package project.resources;

import jakarta.annotation.security.PermitAll;
import jakarta.annotation.security.RolesAllowed;
import jakarta.inject.Inject;
import jakarta.transaction.Transactional;
import jakarta.validation.Valid;
import jakarta.ws.rs.Consumes;
import jakarta.ws.rs.PATCH;
import jakarta.ws.rs.POST;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.core.Context;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import jakarta.ws.rs.core.SecurityContext;
import project.common.requests.ResponseModel;
import project.dtos.agent.AgentCreateDTO;
import project.services.AgentService;

@Path("/agent")
@RolesAllowed({ "CHARITY_AGENT" })
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class AgentResource {
  @Inject
  private AgentService service;

  @POST
  @PermitAll
  @Transactional
  public Response create(@Valid AgentCreateDTO dto) {
    var result = service.createAgent(dto);

    var response = ResponseModel.success(Response.Status.OK.getStatusCode(), result);

    return Response.ok(response).build();
  }

  @PATCH
  @Path("/first-access")
  public Response triggerFirstAccess(@Context SecurityContext ctx) {
    service.disableFirstAccess(ctx.getUserPrincipal().getName());

    return Response.accepted().build();
  }
}
