package project.v1.dtos.user;

import java.time.Instant;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import project.common.annotations.Mask;
import project.common.annotations.enums.MaskType;
import project.v1.entities.enums.UserTypeEnum;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class UserDTO {
  private Long id;
  private String email;
  private String name;

  @Mask(MaskType.PHONE)
  private String phoneNumber;
  private UserTypeEnum type;
  private Instant createdAt;
}
