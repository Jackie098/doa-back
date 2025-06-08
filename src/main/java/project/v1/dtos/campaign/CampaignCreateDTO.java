package project.v1.dtos.campaign;

import java.math.BigDecimal;
import java.time.Instant;

import org.hibernate.validator.constraints.Length;

import com.fasterxml.jackson.annotation.JsonFormat;

import jakarta.validation.constraints.DecimalMax;
import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.Digits;
import jakarta.validation.constraints.Future;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import project.common.annotations.UnmaskNumber;
import project.common.utils.AppConstants;
import project.v1.entities.enums.CampaignStatusEnum;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class CampaignCreateDTO {
  private Long agentId;

  @NotBlank
  @Length(max = AppConstants.SLUG_MAX_LENGTH)
  private String slug;

  @NotBlank
  @Length(max = AppConstants.NAME_MAX_LENGTH)
  private String name;

  @NotBlank
  @Length(min = 11, max = 14)
  @UnmaskNumber
  private String phoneNumber;

  private String description;
  private String addresLineOne; // Street, block, number
  private String addresLineTwo; // city, state
  private String addresLineThree; // complement

  private CampaignStatusEnum status;
  // private CampaignTypeEnum type = CampaignTypeEnum.PICK_UP;

  @NotNull
  @Min(1)
  private Integer totalTickets;

  @NotNull
  @DecimalMin("5.00")
  @DecimalMax("500.00")
  @Digits(integer = 3, fraction = 2)
  private BigDecimal ticketPrice;

  @NotNull
  private Boolean startNow;

  // private Instant startDate;

  // @Future
  // private Instant dueDate;

  @JsonFormat(pattern = "yyyy-MM-dd'T'HH:mm:ss.SSS", timezone = "UTC")
  private Instant startDate;

  @JsonFormat(pattern = "yyyy-MM-dd'T'HH:mm:ss.SSS", timezone = "UTC")
  @Future
  private Instant dueDate;
}
