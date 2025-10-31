package project.v1.dtos.campaignDonation;

import java.time.Instant;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import project.v1.dtos.campaignVolunteer.CampaignVolunteerMinDTO;
import project.v1.entities.enums.CampaignDonationStatusEnum;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class CampaignDonationDTO {
  private Long id;
  private String donorName;
  private String donorPhoneNumber;
  private Long ticketQuantity;
  private CampaignDonationStatusEnum status;
  private Boolean isDonation;
  private Instant createdAt;

  private CampaignVolunteerMinDTO volunteer;
}
