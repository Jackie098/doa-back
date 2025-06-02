package project.common.utils;

import io.smallrye.jwt.build.Jwt;
import project.entities.User;

public class TokenUtils {
  static public String generateToken(User user) {
    String token = Jwt.issuer("sign-in")
        .upn(user.getEmail())
        .groups(user.getType().toString())
        .sign();

    return token;
  }
}
