package project.v1.dtos.campaign;

import java.math.BigDecimal;
import java.time.Instant;

import org.hibernate.validator.constraints.Length;

import com.fasterxml.jackson.annotation.JsonFormat;

import jakarta.validation.constraints.Future;
import jakarta.validation.constraints.FutureOrPresent;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import project.common.annotations.UnmaskNumber;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class CampaignUpdateDTO {
  private Long id;
  private Long agentId;
  private String name;

  @UnmaskNumber
  @Length(min = 11, max = 14, message = "O telefone deve conter entre 11 e 14 caracteres.")
  private String phoneNumber;

  private String description;
  private String addresLineOne; // Street, block, number
  private String addresLineTwo; // city, state
  private String addresLineThree; // complement

  private Integer totalTickets;

  private BigDecimal ticketPrice;

  @JsonFormat(pattern = "yyyy-MM-dd'T'HH:mm:ss", timezone = "UTC")
  @FutureOrPresent(message = "A data de início não pode estar no passado.")
  private Instant startDate;
  @JsonFormat(pattern = "yyyy-MM-dd'T'HH:mm:ss", timezone = "UTC")
  @Future
  private Instant dueDate;
}
