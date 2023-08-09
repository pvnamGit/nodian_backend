package com.nodian.nodian_backend.controller.account;

import com.nodian.nodian_backend.base.baseResponse.FailedResponse;
import com.nodian.nodian_backend.base.baseResponse.SuccessResponse;
import com.nodian.nodian_backend.model.account.Account;
import com.nodian.nodian_backend.service.account.AccountService;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api")
public class AccountController {

  @Autowired
  private AccountService accountService;


  @GetMapping("/user/info")
  public ResponseEntity<?> getUserInfo(@RequestHeader("Authorization") String authToken, HttpServletResponse response) {
    try {
      Account account = accountService.getCurrentAccountInfo(authToken);
      return ResponseEntity.ok().body(new SuccessResponse(account, true));
    } catch (Error error) {
      return ResponseEntity.badRequest().body(new FailedResponse(error.getMessage(), false));
    }
  }

}
