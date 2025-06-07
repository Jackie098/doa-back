package project.common.utils;

import java.time.Duration;

import io.smallrye.jwt.build.Jwt;
import project.v1.entities.User;

public class TokenUtils {
  static public String generateToken(User user) {
    String token = Jwt.issuer("sign-in")
        .upn(user.getEmail())
        .groups(user.getType().toString())
        .expiresIn(Duration.ofDays(2))
        .sign();

    return token;
  }
}
