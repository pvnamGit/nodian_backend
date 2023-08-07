package com.nodian.nodian_backend.controller.login;

import com.nodian.nodian_backend.base.BaseController;
import com.nodian.nodian_backend.base.baseResponse.SuccessResponse;
import com.nodian.nodian_backend.jwt.JWTUtils;
import com.nodian.nodian_backend.model.account.Account;
import com.nodian.nodian_backend.payload.request.IdTokenRequestDto;
import com.nodian.nodian_backend.payload.response.LoginSuccessResponse;
import com.nodian.nodian_backend.service.account.AccountService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseCookie;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.oauth2.client.authentication.OAuth2AuthenticationToken;
import org.springframework.security.oauth2.core.oidc.user.OidcUser;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;
import java.security.GeneralSecurityException;

@RestController
@RequestMapping("/api")
public class LoginController extends BaseController {

  @Autowired
  private AccountService accountService;

  private AuthenticationManager authenticationManager;

  private JWTUtils jwtUtils;


  @PostMapping("/login/oauth/google")
  public ResponseEntity<?> LoginWithGoogleOauth2(@RequestBody IdTokenRequestDto body, HttpServletResponse response) throws GeneralSecurityException, IOException {
    // Get the authenticated user details using CustomOidcUserService
    String authToken = accountService.loginOAuthGoogle(body);
    final ResponseCookie cookie = ResponseCookie.from("AUTH-TOKEN", authToken)
        .httpOnly(true)
        .maxAge(7 * 24 * 3600)
        .path("/")
        .secure(false)
        .build();
    response.addHeader(HttpHeaders.SET_COOKIE, cookie.toString());

    return ResponseEntity.ok(new SuccessResponse(new LoginSuccessResponse(authToken)));
  }
}
