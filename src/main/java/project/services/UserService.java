package project.services;

import java.util.Optional;

import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import project.common.mappers.GenericMapper;
import project.common.mappers.UserMapper;
import project.dtos.user.CreateUserDTO;
import project.entities.User;
import project.repositories.UserRepository;

@ApplicationScoped
public class UserService {
  @Inject
  private UserRepository userRepository;

  @Inject
  GenericMapper mapper;

  public Optional<User> findByEmailOrPhoneNumber(String email, String phoneNumber) {
    return userRepository.findByEmailOrPhoneNumber(email, phoneNumber);
  }

  public User create(CreateUserDTO dto) {
    User user = UserMapper.fromDTO(dto);

    userRepository.persist(user);

    return user;
  }
}
