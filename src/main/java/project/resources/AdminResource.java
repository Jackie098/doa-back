package project.resources;

import jakarta.inject.Inject;
import jakarta.ws.rs.Consumes;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import project.common.requests.ResponseModel;
import project.services.AdminService;

@Path("/admin")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class AdminResource {
  @Inject
  private AdminService service;

  @GET
  @Path("/agent")
  public Response listAgents() {
    var result = service.listAgents();

    var response = ResponseModel.success(Response.Status.OK.getStatusCode(), result);
    // Return a list of agents
    return Response.ok(response).build();
  }
}
