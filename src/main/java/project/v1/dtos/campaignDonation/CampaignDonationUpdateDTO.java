package project.v1.dtos.campaignDonation;

import org.hibernate.validator.constraints.Length;

import jakarta.validation.constraints.Min;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import project.common.annotations.UnmaskNumber;
import project.common.utils.AppConstants;
import project.v1.entities.enums.CampaignDonationStatusEnum;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class CampaignDonationUpdateDTO {
  private Long id;

  @Length(max = AppConstants.NAME_MAX_LENGTH)
  private String donorName;

  @UnmaskNumber
  @Length(max = 14)
  private String donorPhoneNumber;

  @Min(value = 1, message = "A quantidade de tickets deve ser pelo menos igual a 1.")
  private Long ticketQuantity;

  private CampaignDonationStatusEnum status;

  private Boolean isDonation;
}
