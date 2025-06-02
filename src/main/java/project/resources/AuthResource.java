package project.resources;

import io.quarkus.security.Authenticated;
import jakarta.inject.Inject;
import jakarta.validation.Valid;
import jakarta.ws.rs.Consumes;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.POST;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.core.Context;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import jakarta.ws.rs.core.SecurityContext;
import project.common.requests.ResponseModel;
import project.dtos.auth.AuthCreateDTO;
import project.dtos.auth.AuthDTO;
import project.dtos.auth.AuthExtDTO;
import project.services.AuthService;

@Path("/auth")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class AuthResource {
  @Inject
  private AuthService service;

  @POST
  public Response signIn(@Valid AuthCreateDTO dto) {
    AuthDTO result = service.signIn(dto);

    var response = ResponseModel.success(Response.Status.OK.getStatusCode(), result);

    return Response.ok(response).build();
  }

  @GET
  @Path("/me")
  @Authenticated
  public Response me(@Context SecurityContext ctx) {
    AuthExtDTO result = service.me(ctx.getUserPrincipal().getName());

    var response = ResponseModel.success(Response.Status.OK.getStatusCode(), result);

    return Response.ok(response).build();
  }
}
