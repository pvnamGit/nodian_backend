package com.nodian.nodian_backend.jwt;

import com.nodian.nodian_backend.model.account.Account;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.JwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.stereotype.Component;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Component
public class JWTUtils {

  private Long EXPIRATION_TIME = Long.valueOf(360000000); // 7 days

  @Value("${security.jwt.secret_key}")
  private String SECRETE_TOKEN;


  public String generateTokenFromEmail(Account account) {

    Map<String, Object> claims = new HashMap<>();
    claims.put("role", account.getRole());
    return Jwts.builder().setSubject(account.getEmail()).setIssuedAt(new Date())
        .setExpiration(new Date((new Date())
            .getTime() + EXPIRATION_TIME))
        .signWith(SignatureAlgorithm.HS512, SECRETE_TOKEN)
        .compact();
  }

  public Authentication verifyAndGetAuthentication(String token) {
    try {
      Claims claims = Jwts.parser()
          .setSigningKey(SECRETE_TOKEN)
          .parseClaimsJws(token)
          .getBody();
      List<GrantedAuthority> authorities = AuthorityUtils.commaSeparatedStringToAuthorityList(claims.get("role", String.class));
      return new UsernamePasswordAuthenticationToken(claims.getSubject(), token, authorities);
    } catch (JwtException | IllegalArgumentException ignored) {
      return null;
    }
  }
}
