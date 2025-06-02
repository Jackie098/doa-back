package project.dtos.auth;

import java.time.Instant;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import project.common.annotations.Mask;
import project.common.annotations.enums.MaskType;
import project.dtos.agent.AgentMinDTO;
import project.entities.enums.UserTypeEnum;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@JsonInclude(Include.NON_EMPTY)
public class AuthExtDTO {
  private Long id;
  private String email;
  private String name;
  private String avatarUrl;

  @Mask(MaskType.PHONE)
  private String phoneNumber;
  private UserTypeEnum type;

  private Instant createdAt;

  private Boolean isActive;
  private Boolean firstAccess;
  private Boolean isCharityAgentMember;

  private AgentMinDTO agent;
}
