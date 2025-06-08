package project.v1.services;

import java.util.Optional;

import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import project.common.mappers.UserMapper;
import project.common.utils.PasswordUtils;
import project.v1.dtos.user.UserCreateDTO;
import project.v1.entities.User;
import project.v1.repositories.UserRepository;

@ApplicationScoped
public class UserService {
  @Inject
  private UserRepository userRepository;

  public Optional<User> findByEmailOrPhoneNumber(String email, String phoneNumber) {
    return userRepository.findByEmailOrPhoneNumber(email, phoneNumber);
  }

  public User create(UserCreateDTO dto) {
    dto.setPassword(PasswordUtils.hashPass(dto.getPassword()));

    User user = UserMapper.fromDTO(dto);

    userRepository.persist(user);

    return user;
  }
}
