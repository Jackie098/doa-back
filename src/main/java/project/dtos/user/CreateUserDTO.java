package project.dtos.user;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import project.entities.enums.UserTypeEnum;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class CreateUserDTO {
  private String email;
  private String password;
  private String avatarUrl;
  private UserTypeEnum type;
  private String phoneNumber;
  private String name;
}
