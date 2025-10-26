package project.common.mappers;

import java.util.List;

import project.v1.dtos.campaignVolunteerRanking.VolunteerRawRankingDTO;
import project.v1.dtos.campaignVolunteerRanking.VolunteerTicketsDTO;
import project.v1.entities.CampaignVolunteerRanking;

public class CampaignVolunteerRankingMapper {

  public static VolunteerRawRankingDTO fromEntityToDTO(CampaignVolunteerRanking data) {
    VolunteerTicketsDTO tickets = VolunteerTicketsDTO.builder()
        .collected(data.getTicketsCollected())
        .donated(data.getTicketsDonated())
        .build();

    return VolunteerRawRankingDTO.builder()
        .volunteerId(data.getVolunteerId())
        .name(data.getName())
        .tickets(tickets)
        .build();
  }

  public static List<VolunteerRawRankingDTO> fromEntityToListDTO(List<CampaignVolunteerRanking> data) {
    return data.stream().map(CampaignVolunteerRankingMapper::fromEntityToDTO).toList();
  }
}
