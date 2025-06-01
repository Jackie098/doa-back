package project.dtos.agent;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import project.common.annotations.Mask;
import project.common.annotations.enums.MaskType;
import project.dtos.person.PersonMinResponseDTO;
import project.dtos.user.UserMinResponseDTO;
import project.entities.enums.AgentStatusEnum;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class AgentResponseDTO {
  private Long id;

  @Mask(MaskType.CNPJ)
  private String document;
  private AgentStatusEnum status;

  private UserMinResponseDTO user;
  private PersonMinResponseDTO person;
}
