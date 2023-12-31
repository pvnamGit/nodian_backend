package com.nodian.nodian_backend.controller.login;

import com.nodian.nodian_backend.base.BaseController;
import com.nodian.nodian_backend.base.baseResponse.SuccessResponse;
import com.nodian.nodian_backend.jwt.JWTUtils;
import com.nodian.nodian_backend.payload.request.IdTokenRequestDto;
import com.nodian.nodian_backend.payload.response.LoginSuccessResponse;
import com.nodian.nodian_backend.service.account.AccountService;
import jakarta.annotation.security.RolesAllowed;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseCookie;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
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

  @RequestMapping("/home")
  String home() {
    return "Hello HAHA World!";
  }

}
