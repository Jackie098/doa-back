package project.v1.dtos.agent;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import project.common.annotations.Mask;
import project.common.annotations.enums.MaskType;
import project.v1.dtos.person.PersonMinDTO;
import project.v1.dtos.user.UserMinDTO;
import project.v1.entities.enums.AgentStatusEnum;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class AgentDTO {
  private Long id;

  @Mask(MaskType.CNPJ)
  private String document;
  private AgentStatusEnum status;

  private UserMinDTO user;
  private PersonMinDTO person;
}
