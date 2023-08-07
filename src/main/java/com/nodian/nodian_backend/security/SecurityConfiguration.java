package com.nodian.nodian_backend.security;

import com.nodian.nodian_backend.jwt.JWTRequestFilter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.oauth2.client.oidc.userinfo.OidcUserRequest;
import org.springframework.security.oauth2.client.oidc.userinfo.OidcUserService;
import org.springframework.security.oauth2.client.userinfo.OAuth2UserService;
import org.springframework.security.oauth2.core.OAuth2AccessToken;
import org.springframework.security.oauth2.core.oidc.user.DefaultOidcUser;
import org.springframework.security.oauth2.core.oidc.user.OidcUser;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;

import java.util.HashSet;
import java.util.Set;

@Configuration
@EnableWebSecurity
@EnableMethodSecurity(securedEnabled = true, jsr250Enabled = true)
public class SecurityConfiguration {

  private final JWTRequestFilter jwtRequestFilter;

  public SecurityConfiguration(JWTRequestFilter jwtRequestFilter) {
    this.jwtRequestFilter = jwtRequestFilter;
  }


  @Bean
  public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
    http
        .csrf(csrf -> csrf.disable())
        .cors(cors -> cors.disable())
        .authorizeHttpRequests(authz -> authz
            .requestMatchers(new AntPathRequestMatcher("/api/login/**")).permitAll()
            .requestMatchers(new AntPathRequestMatcher("/api/home/**")).permitAll()
            .anyRequest().authenticated()
        )
        .addFilterBefore(jwtRequestFilter, UsernamePasswordAuthenticationFilter.class)
    ;

        http.sessionManagement(session -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS));
    return http.build();
  }


//
//
//  private GrantedAuthoritiesMapper userAuthoritiesMapper() {
//    return (authorities) -> {
//      Set<GrantedAuthority> mappedAuthorities = new HashSet<>();
//
//      authorities.forEach(authority -> {
//        if (OidcUserAuthority.class.isInstance(authority)) {
//          OidcUserAuthority oidcUserAuthority = (OidcUserAuthority) authority;
//
//          OidcIdToken idToken = oidcUserAuthority.getIdToken();
//          OidcUserInfo userInfo = oidcUserAuthority.getUserInfo();
//
//          // Map the claims found in idToken and/or userInfo
//          // to one or more GrantedAuthority's and add it to mappedAuthorities
//
//        } else if (OAuth2UserAuthority.class.isInstance(authority)) {
//          OAuth2UserAuthority oauth2UserAuthority = (OAuth2UserAuthority) authority;
//
//          Map<String, Object> userAttributes = oauth2UserAuthority.getAttributes();
//
//          // Map the attributes found in userAttributes
//          // to one or more GrantedAuthority's and add it to mappedAuthorities
//
//        }
//      });
//
//      return mappedAuthorities;
//    };
//  }

  private OAuth2UserService<OidcUserRequest, OidcUser> oidcUserService() {
    final OidcUserService delegate = new OidcUserService();

    return (userRequest) -> {
      // Delegate to the default implementation for loading a user
      OidcUser oidcUser = delegate.loadUser(userRequest);

      OAuth2AccessToken accessToken = userRequest.getAccessToken();
      Set<GrantedAuthority> mappedAuthorities = new HashSet<>();

      // TODO
      // 1) Fetch the authority information from the protected resource using accessToken
      // 2) Map the authority information to one or more GrantedAuthority's and add it to mappedAuthorities

      // 3) Create a copy of oidcUser but use the mappedAuthorities instead
      oidcUser = new DefaultOidcUser(mappedAuthorities, oidcUser.getIdToken(), oidcUser.getUserInfo());

      return oidcUser;
    };
  }


}
