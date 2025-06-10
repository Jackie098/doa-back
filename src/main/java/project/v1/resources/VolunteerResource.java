package project.v1.resources;

import org.eclipse.microprofile.jwt.JsonWebToken;

import jakarta.annotation.security.PermitAll;
import jakarta.annotation.security.RolesAllowed;
import jakarta.inject.Inject;
import jakarta.validation.Valid;
import jakarta.ws.rs.Consumes;
import jakarta.ws.rs.PATCH;
import jakarta.ws.rs.POST;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import project.v1.dtos.volunteer.VolunteerCreateDTO;
import project.v1.services.VolunteerService;

@Path("/v1/volunteer")
@RolesAllowed({ "VOLUNTEER" })
@Consumes(MediaType.APPLICATION_JSON)
@Produces(MediaType.APPLICATION_JSON)
public class VolunteerResource {
  @Inject
  private JsonWebToken jwt;

  @Inject
  private VolunteerService service;

  @POST
  @PermitAll
  public Response create(@Valid VolunteerCreateDTO dto) {
    service.create(dto);
    return Response.accepted().build();
  }

  @PATCH()
  @Path("/campaign/{id}/bind")
  public Response bindCampaign() {
    return Response.ok().build();
  }
}
