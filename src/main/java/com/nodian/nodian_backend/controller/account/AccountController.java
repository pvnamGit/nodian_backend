package com.nodian.nodian_backend.controller.account;

import com.nodian.nodian_backend.base.BaseController;
import com.nodian.nodian_backend.base.baseResponse.FailedResponse;
import com.nodian.nodian_backend.base.baseResponse.SuccessResponse;
import com.nodian.nodian_backend.model.account.Account;
import com.nodian.nodian_backend.service.account.AccountService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.Collections;

public class AccountController extends BaseController {

  @Autowired
  private AccountService accountService;

  @GetMapping("/user")
  public ResponseEntity<?> user(@AuthenticationPrincipal OAuth2User principal) {
    try {
      return ResponseEntity.ok().body(new SuccessResponse(Collections.singletonMap("name", principal.getAttribute("name")), true));
    } catch (Error error) {
      return ResponseEntity.badRequest().body(new FailedResponse(error.getMessage(), false));
    }
  }

//  @GetMapping("/user/info/{email}")
//  public ResponseEntity<?> getUserInfo(@PathVariable String email) {
//    try {
//      Account account = accountService.(email);
//      return ResponseEntity.ok().body(new SuccessResponse(account, true));
//    } catch (Error error) {
//      return ResponseEntity.badRequest().body(new FailedResponse(error.getMessage(), false));
//    }
//  }

}
