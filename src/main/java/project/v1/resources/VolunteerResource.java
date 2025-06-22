package project.v1.resources;

import java.net.URI;

import org.eclipse.microprofile.jwt.JsonWebToken;

import jakarta.annotation.security.PermitAll;
import jakarta.annotation.security.RolesAllowed;
import jakarta.inject.Inject;
import jakarta.validation.Valid;
import jakarta.ws.rs.Consumes;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.PATCH;
import jakarta.ws.rs.POST;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.PathParam;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.QueryParam;
import jakarta.ws.rs.core.Context;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import jakarta.ws.rs.core.SecurityContext;
import jakarta.ws.rs.core.Response.Status;
import project.common.database.Pageable;
import project.common.requests.ResponseModel;
import project.v1.dtos.campaignDonation.CampaignDonationCreateDTO;
import project.v1.dtos.campaignVolunteer.CampaignVolunteerExtDTO;
import project.v1.dtos.common.PageDTO;
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

  @GET
  @Path("/campaign")
  public Response listCampaigns(@Context SecurityContext ctx,
      @QueryParam("accepted") Boolean isAccepted,
      @QueryParam("page") Integer page,
      @QueryParam("size") Integer size) {
    Long userId = Long.parseLong(jwt.getClaim("id").toString());

    Pageable<CampaignVolunteerExtDTO> result = service.listCampaigns(userId, isAccepted, PageDTO.of(page, size));
    var response = ResponseModel.success(Status.OK.getStatusCode(), result);

    return Response.ok(response).build();
  }

  @PATCH
  @Path("/campaign/{id}/bind")
  public Response bindCampaign(@Context SecurityContext ctx, @PathParam("id") Long campaignId) {
    Long userId = Long.parseLong(jwt.getClaim("id").toString());

    service.bindVolunteerCampaign(userId, campaignId);

    return Response.accepted().build();
  }

  @POST
  @Path("/campaign/{id}/donation")
  public Response createDonation(@Context SecurityContext ctx, @PathParam("id") Long campaignId,
      @Valid CampaignDonationCreateDTO dto) {
    Long userId = Long.parseLong(jwt.getClaim("id").toString());

    service.createDonation(userId, campaignId, dto);

    return Response.ok(ResponseModel.success()).build();
  }
}
