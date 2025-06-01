package project.dtos.user;

import java.time.Instant;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import project.common.annotations.Mask;
import project.common.annotations.enums.MaskType;
import project.entities.enums.UserTypeEnum;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class UserExtDTO {
  private Long id;
  private String email;
  private String name;
  private String avatarUrl;

  @Mask(MaskType.PHONE)
  private String phoneNumber;
  private UserTypeEnum type;

  private Instant createdAt;
  private Instant updatedAt;

  private Boolean isActive;
  private Boolean isFirstAccess;
  private Boolean isCharityAgentMember;
}
