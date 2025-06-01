package project.common.mappers;

import project.dtos.user.UserCreateDTO;
import project.dtos.user.UserMinDTO;
import project.entities.User;
import project.entities.enums.UserTypeEnum;

public class UserMapper {
  public static User fromDTO(UserCreateDTO dto) {
    return User.builder()
        .email(dto.getEmail())
        .password(dto.getPassword())
        .avatarUrl(dto.getAvatarUrl())
        .type(UserTypeEnum.valueOf(dto.getType().toUpperCase()))
        .phoneNumber(dto.getPhoneNumber())
        .name(dto.getName())
        .build();
  }

  public static UserMinDTO fromEntityToMinimal(User user) {
    return UserMinDTO.builder()
        .id(user.getId())
        .email(user.getEmail())
        .name(user.getName())
        .type(user.getType())
        .createdAt(user.getCreatedAt())
        .build();
  }
}
