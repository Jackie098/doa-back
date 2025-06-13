package project.v1.resources;

import org.eclipse.microprofile.jwt.JsonWebToken;

import jakarta.annotation.security.PermitAll;
import jakarta.annotation.security.RolesAllowed;
import jakarta.inject.Inject;
import jakarta.transaction.Transactional;
import jakarta.validation.Valid;
import jakarta.ws.rs.Consumes;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.PATCH;
import jakarta.ws.rs.POST;
import jakarta.ws.rs.PUT;
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
import project.v1.dtos.agent.AgentCreateDTO;
import project.v1.dtos.campaign.CampaignCreateDTO;
import project.v1.dtos.campaign.CampaignDTO;
import project.v1.dtos.campaign.CampaignUpdateDTO;
import project.v1.dtos.campaignVolunteer.CampaignVolunteerDTO;
import project.v1.dtos.common.PageDTO;
import project.v1.entities.enums.CampaignStatusEnum;
import project.v1.services.AgentService;

@Path("/v1/agent")
@RolesAllowed({ "CHARITY_AGENT" })
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class AgentResource {
  @Inject
  private JsonWebToken jwt;

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

  @GET
  @Path("/campaign")
  public Response listCampaign(@Context SecurityContext ctx, @QueryParam("status") CampaignStatusEnum status,
      @QueryParam("page") Integer page,
      @QueryParam("size") Integer size) {
    Long agentId = Long.parseLong(jwt.getClaim("id").toString());

    Pageable<CampaignDTO> result = service.listCampaign(status, agentId, PageDTO.of(page, size));
    var response = ResponseModel.success(Status.OK.getStatusCode(), result);

    return Response.ok(response).build();
  }

  @POST
  @Path("/campaign")
  public Response createCampaign(@Context SecurityContext ctx, @Valid CampaignCreateDTO dto) {
    Long agentId = Long.parseLong(jwt.getClaim("id").toString());
    dto.setAgentId(agentId);

    var result = service.createCampaign(dto);
    var response = ResponseModel.success(Response.Status.OK.getStatusCode(), result);

    return Response.ok(response).build();
  }

  @PUT
  @Path("/campaign/{id}")
  public Response updateCampaign(@Context SecurityContext ctx, @PathParam("id") Long id, @Valid CampaignUpdateDTO dto) {
    Long agentId = Long.parseLong(jwt.getClaim("id").toString());

    dto.setId(id);
    dto.setAgentId(agentId);
    var result = service.updateCampaign(dto);
    var response = ResponseModel.success(Response.Status.OK.getStatusCode(),
        result);

    return Response.ok(response).build();
  }

  @PATCH
  @Path("/campaign/{id}/reactivate")
  public Response reactivateCampaign(@Context SecurityContext ctx, @PathParam("id") Long id) {
    Long agentId = Long.parseLong(jwt.getClaim("id").toString());
    service.reactivateCampaign(id, agentId);

    var response = ResponseModel.success(Response.Status.OK.getStatusCode(), "Campanha reativada com sucesso!");

    return Response.ok(response).build();
  }

  @PATCH
  @Path("/campaign/{id}/pause")
  public Response pauseCampaign(@Context SecurityContext ctx, @PathParam("id") Long id) {
    Long agentId = Long.parseLong(jwt.getClaim("id").toString());
    service.pauseCampaign(id, agentId);

    var response = ResponseModel.success(Response.Status.OK.getStatusCode(), "Campanha pausada com sucesso!");

    return Response.ok(response).build();
  }

  @PATCH
  @Path("/campaign/{id}/cancel")
  public Response cancelCampaign(@Context SecurityContext ctx, @PathParam("id") Long id) {
    Long agentId = Long.parseLong(jwt.getClaim("id").toString());
    service.cancelCampaign(id, agentId);

    var response = ResponseModel.success(Response.Status.OK.getStatusCode(), "Campanha cancelada com sucesso!");

    return Response.ok(response).build();
  }

  @PATCH
  @Path("/campaign/{id}/finish")
  public Response finishCampaign(@Context SecurityContext ctx, @PathParam("id") Long id) {
    Long agentId = Long.parseLong(jwt.getClaim("id").toString());
    service.finishCampaign(id, agentId);

    var response = ResponseModel.success(Response.Status.OK.getStatusCode(), "Campanha finalizada com sucesso");

    return Response.ok(response).build();
  }

  @GET
  @Path("/campaign/{id}/volunteer")
  public Response listCampaignVolunteers(@Context SecurityContext ctx,
      @PathParam("id") String campaignId,
      @QueryParam("accepted") Boolean isAccepted,
      @QueryParam("page") Integer page,
      @QueryParam("size") Integer size) {
    Long userId = Long.parseLong(jwt.getClaim("id").toString());

    Pageable<CampaignVolunteerDTO> result = service.listCampaignVolunteers(userId,
        Long.parseLong(campaignId), isAccepted, PageDTO.of(page, size));

    var response = ResponseModel.success(Response.Status.OK.getStatusCode(),
        result);

    return Response.ok(response).build();
  }
}
