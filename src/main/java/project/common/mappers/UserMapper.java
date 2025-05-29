package project.common.mappers;

import project.dtos.user.CreateUserDTO;
import project.entities.User;

public class UserMapper {
  public static User fromDTO(CreateUserDTO dto) {
    return User.builder()
        .email(dto.getEmail())
        .password(dto.getPassword())
        .avatarUrl(dto.getAvatarUrl())
        .type(dto.getType())
        .phoneNumber(dto.getPhoneNumber())
        .name(dto.getName())
        .build();
  }
}
