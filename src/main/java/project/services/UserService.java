package project.services;

import java.util.Optional;

import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import project.common.mappers.GenericMapper;
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

  public void create(CreateUserDTO dto) {
    User user = mapper.toObject(dto, User.class);

    userRepository.persist(user);
  }
}
