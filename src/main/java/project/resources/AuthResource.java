package project.resources;

import jakarta.inject.Inject;
import jakarta.validation.Valid;
import jakarta.ws.rs.Consumes;
import jakarta.ws.rs.POST;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import project.dtos.auth.AuthCreateDTO;
import project.dtos.auth.AuthDTO;
import project.services.AuthService;

@Path("/auth")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class AuthResource {
  @Inject
  private AuthService service;

  @POST
  public Response signIn(@Valid AuthCreateDTO dto) {
    AuthDTO response = service.signIn(dto);
    return Response.ok(response).build();
  }
}
