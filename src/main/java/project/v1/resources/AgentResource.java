package project.v1.resources;

import java.util.List;

import org.eclipse.microprofile.jwt.JsonWebToken;

import jakarta.annotation.security.PermitAll;
import jakarta.annotation.security.RolesAllowed;
import jakarta.inject.Inject;
import jakarta.transaction.Transactional;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
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
import project.common.utils.ParseQueryParams;
import project.v1.dtos.agent.AgentCreateDTO;
import project.v1.dtos.campaign.CampaignCreateDTO;
import project.v1.dtos.campaign.CampaignDTO;
import project.v1.dtos.campaign.CampaignUpdateDTO;
import project.v1.dtos.campaignDonation.CampaignDonationMinDTO;
import project.v1.dtos.campaignDonation.CampaignDonationUpdateDTO;
import project.v1.dtos.campaignMetrics.CampaignMetricsDTO;
import project.v1.dtos.campaignVolunteer.CampaignVolunteerDTO;
import project.v1.dtos.campaignVolunteerRanking.VolunteerRawRankingDTO;
import project.v1.dtos.common.ManyReferencesDTO;
import project.v1.dtos.common.PageDTO;
import project.v1.dtos.common.ValidateDTO;
import project.v1.entities.enums.CampaignDonationStatusEnum;
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

  @GET
  @Path("/slug/verify")
  public Response verifySlug(@QueryParam("slug") @NotBlank String slug) {
    var result = service.verifySlug(slug);

    var response = ResponseModel.success(Response.Status.OK.getStatusCode(), result);

    return Response.ok(response).build();
  }

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

  @GET
  @Path("campaign/{id}")
  public Response getCampaign(@Context SecurityContext ctx, @PathParam("id") Long id) {
    Long agentId = Long.parseLong(jwt.getClaim("id").toString());
    var result = service.findCampaignById(id, agentId);

    var response = ResponseModel.success(Response.Status.OK.getStatusCode(), result);

    return Response.ok(response).build();
  }

  @GET
  @Path("/campaign/slug/verify")
  public Response verifyCampaignSlug(@QueryParam("slug") @NotBlank String slug) {
    Long agentId = Long.parseLong(jwt.getClaim("id").toString());

    var result = service.verifyCampaignSlug(slug, agentId);

    var response = ResponseModel.success(Response.Status.OK.getStatusCode(), result);

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

  @PATCH
  @Path("campaign/{id}/volunteer/accept")
  public Response acceptVolunteers(@Context SecurityContext ctx, @PathParam("id") String campaignId,
      @Valid ManyReferencesDTO dto) {
    Long userId = Long.parseLong(jwt.getClaim("id").toString());

    service.acceptVolunteers(userId, Long.parseLong(campaignId), dto);

    return Response.accepted().build();
  }

  @GET
  @Path("campaign/{id}/volunteer/ranking")
  public Response listCamapignVolunteerRanking(@Context SecurityContext ctx, @PathParam("id") String campaignId) {
    Long userId = Long.parseLong(jwt.getClaim("id").toString());

    List<VolunteerRawRankingDTO> result = service.listVolunteerRankingCampaign(userId, Long.parseLong(campaignId));

    var response = ResponseModel.success(Response.Status.OK.getStatusCode(),
        result);

    return Response.ok(response).build();
  }

  @GET
  @Path("campaign/{id}/volunteer/ranking/share")
  public Response shareCampaignVolunteerRanking(@Context SecurityContext ctx, @PathParam("id") String campaignId) {
    Long userId = Long.parseLong(jwt.getClaim("id").toString());

    String result = service.shareCampaignVolunteerRanking(userId, Long.parseLong(campaignId));

    var response = ResponseModel.success(Response.Status.OK.getStatusCode(),
        result);

    return Response.ok(response).build();
  }

  @GET
  @Path("/metrics/campaign")
  public Response campaignMetrics(@Context SecurityContext ctx,
      @QueryParam("campaignIds") @NotBlank String campaignIdsStr, @QueryParam("page") Integer page,
      @QueryParam("size") Integer size) {
    Long agentId = Long.parseLong(jwt.getClaim("id").toString());
    List<Long> campaignIds = ParseQueryParams.validateAndParseCampaignIds(campaignIdsStr, 20);

    Pageable<CampaignMetricsDTO> result = service.listCampaignMetricsInRange(agentId, campaignIds,
        PageDTO.of(page, size));
    var response = ResponseModel.success(Response.Status.OK.getStatusCode(), result);

    return Response.ok(response).build();
  }

  @GET
  @Path("campaign/{id}/donation")
  public Response listCampaignDonations(@Context SecurityContext ctx, @PathParam("id") String campaignId,
      @QueryParam("status") CampaignDonationStatusEnum status,
      @QueryParam("page") Integer page,
      @QueryParam("size") Integer size) {

    Long agentId = Long.parseLong(jwt.getClaim("id").toString());
    Pageable<CampaignDonationMinDTO> result = service.listCampaignDonations(agentId,
        Long.parseLong(campaignId),
        status,
        PageDTO.of(page, size));

    var response = ResponseModel.success(Response.Status.OK.getStatusCode(), result);

    return Response.ok(response).build();
  }

  @PATCH
  @Path("campaign/{campaignId}/donation/{donationId}/validate")
  public Response validateCampaignDonation(
      @Context SecurityContext ctx,
      @PathParam("campaignId") String campaignId,
      @PathParam("donationId") String donationId,
      @Valid ValidateDTO dto) {

    Long agentId = Long.parseLong(jwt.getClaim("id").toString());
    service.validateCampaignDonation(
        agentId,
        Long.parseLong(campaignId),
        Long.parseLong(donationId),
        dto);

    var response = ResponseModel.success(Response.Status.OK.getStatusCode());

    return Response.ok(response).build();
  }

  @PUT
  @Path("campaign/{campaignId}/donation/{donationId}")
  public Response updateCampaignDonation(@Context SecurityContext ctx, @PathParam("campaignId") String campaignId,
      @PathParam("donationId") String donationId, @Valid CampaignDonationUpdateDTO dto) {
    Long agentId = Long.parseLong(jwt.getClaim("id").toString());

    CampaignDonationMinDTO result = service.updateCampaignDonation(agentId, Long.parseLong(campaignId),
        Long.parseLong(donationId), dto);

    var response = ResponseModel.success(Response.Status.OK.getStatusCode(), result);
    return Response.ok(response).build();
  }
}
