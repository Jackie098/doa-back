package project.common.mappers;

import project.v1.dtos.user.UserCreateDTO;
import project.v1.dtos.user.UserExtMinDTO;
import project.v1.dtos.user.UserMinDTO;
import project.v1.dtos.volunteer.VolunteerCreateDTO;
import project.v1.entities.User;
import project.v1.entities.enums.UserTypeEnum;

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

  public static UserExtMinDTO fromEntityToExtMinimal(User user) {
    return UserExtMinDTO.builder()
        .id(user.getId())
        .name(user.getName())
        .build();
  }

  public static UserCreateDTO fromVolunteerCreateToUserCreateDTO(VolunteerCreateDTO dto) {
    return UserCreateDTO.builder()
        .email(dto.getEmail())
        .password(dto.getPassword())
        .avatarUrl(dto.getAvatarUrl())
        .type(dto.getType().toUpperCase())
        .phoneNumber(dto.getPhoneNumber())
        .name(dto.getName())
        .build();
  }
}
