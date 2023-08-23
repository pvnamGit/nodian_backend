package com.nodian.nodian_backend.service.account;

import com.google.api.client.googleapis.auth.oauth2.GoogleIdToken;
import com.google.api.client.googleapis.auth.oauth2.GoogleIdTokenVerifier;
import com.google.api.client.http.HttpTransport;
import com.google.api.client.http.javanet.NetHttpTransport;
import com.google.api.client.json.JsonFactory;
import com.google.api.client.json.gson.GsonFactory;
import com.nodian.nodian_backend.jwt.JWTUtils;
import com.nodian.nodian_backend.model.account.Account;
import com.nodian.nodian_backend.payload.request.IdTokenRequestDto;
import com.nodian.nodian_backend.repository.account.AccountRepository;
import jakarta.annotation.PostConstruct;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.oauth2.client.oidc.userinfo.OidcUserService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.io.IOException;
import java.security.GeneralSecurityException;
import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;


@Service
public class AccountService extends OidcUserService {

  private final JWTUtils jwtUtils;

  private final AccountRepository accountRepository;


  private GoogleIdTokenVerifier verifier; // null here

  @Value("${spring.security.oauth2.client.registration.google.client-id}")
  private String CLIENT_ID;

  @PostConstruct
  public void init() {
    final JsonFactory jsonFactory = GsonFactory.getDefaultInstance();
    final HttpTransport transport = new NetHttpTransport();
    final Collection<String> validIssuers = Arrays.asList("https://accounts.google.com", "accounts.google.com");

    this.verifier = new GoogleIdTokenVerifier.Builder(transport, jsonFactory)
        .setAudience(Collections.singletonList(CLIENT_ID))
        .setIssuers(validIssuers)
        .build();
  }

  @Autowired
  public AccountService(JWTUtils jwtUtils, AccountRepository accountRepo) {
    this.jwtUtils = jwtUtils;
    this.accountRepository = accountRepo;
  }


  public String loginOAuthGoogle(IdTokenRequestDto requestBody) {
    Account account = verifyIDToken(requestBody.getIdToken());
    if (account == null) {
      throw new IllegalArgumentException();
    }
    account = createOrUpdateUser(account);
    return jwtUtils.generateTokenFromEmail(account);
  }

  @Transactional
  public Account createOrUpdateUser(Account account) {
    Account existingAccount = accountRepository.findByEmail(account.getEmail()).orElseThrow(() -> new UsernameNotFoundException("User not found"));
    if (existingAccount == null) {
      accountRepository.save(account);
      return account;
    }
    return existingAccount;
  }

  private Account verifyIDToken(String idToken) {
    try {
      GoogleIdToken idTokenObj = verifier.verify(idToken);
      if (idTokenObj == null) {
        return null;
      }
      GoogleIdToken.Payload payload = idTokenObj.getPayload();
      String firstName = (String) payload.get("given_name");
      String lastName = (String) payload.get("family_name");
      String email = payload.getEmail();
      String pictureUrl = (String) payload.get("picture");

      return new Account(email, pictureUrl, firstName, lastName);
    } catch (GeneralSecurityException | IOException e) {
      return null;
    }
  }

  public Account getCurrentAccountInfo(String authToken) {
    String jwtToken = authToken.split(" ")[1];
    String email = jwtUtils.getEmailFromToken(jwtToken);
    Account account = accountRepository.findByEmail(email).orElseThrow(() -> new UsernameNotFoundException("User not found"));
    return account;
  }
}
