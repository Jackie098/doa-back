package project.v1.resources;

import jakarta.annotation.security.RolesAllowed;
import jakarta.inject.Inject;
import jakarta.validation.Valid;
import jakarta.ws.rs.Consumes;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.PATCH;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.PathParam;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.QueryParam;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import jakarta.ws.rs.core.Response.Status;
import project.common.requests.ResponseModel;
import project.v1.dtos.agent.AgentValidateDTO;
import project.v1.entities.enums.AgentStatusEnum;
import project.v1.services.AdminService;

@Path("/v1/admin")
@RolesAllowed({ "ADM" })
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class AdminResource {
  @Inject
  private AdminService service;

  @GET
  @Path("/agent")
  public Response listAgents(@QueryParam("status") AgentStatusEnum status) {
    var result = service.listAgents(status);
    var response = ResponseModel.success(Status.OK.getStatusCode(), result);

    return Response.ok(response).build();
  }

  @PATCH
  @Path("/agent/{id}/validate")
  public Response validateAgent(@PathParam("id") Long agentId, @Valid AgentValidateDTO dto) {
    service.validateAgent(dto, agentId);
    return Response.status(Status.ACCEPTED.getStatusCode()).build();
  }
}
