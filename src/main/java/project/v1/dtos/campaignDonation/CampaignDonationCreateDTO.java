package project.v1.dtos.campaignDonation;

import org.hibernate.validator.constraints.Length;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import project.common.annotations.UnmaskNumber;
import project.common.utils.AppConstants;
import project.v1.entities.enums.CampaignDonationStatusEnum;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class CampaignDonationCreateDTO {
  @NotBlank
  @Length(max = AppConstants.NAME_MAX_LENGTH)
  private String donorName;

  @NotBlank
  @Length(min = 11, max = 14)
  @UnmaskNumber
  private String donorPhoneNumber;

  @NotNull(message = "A quantidade de tickets deve ser informada.")
  @Min(value = 1, message = "A quantidade de tickets deve ser pelo menos igual a 1.")
  private Long ticketQuantity;

  private CampaignDonationStatusEnum status = CampaignDonationStatusEnum.PENDING;

  private Boolean isDonation = false;
}
