package com.booking_house_be.controller;

import com.booking_house_be.entity.Account;
import com.booking_house_be.entity.dto.AccountToken;
import com.booking_house_be.service.IAccountService;
import com.booking_house_be.service.JwtService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

@CrossOrigin("*")
@RestController
@RequestMapping("/api")
public class LoginController {
    @Autowired
    AuthenticationManager authenticationManager;

    @Autowired
    JwtService jwtService;

    @Autowired
    IAccountService accountService;

    @GetMapping("/test")
    public String test() {
        return "test successfully";
    }

    @PostMapping("/login")
    public AccountToken getLogin(@RequestBody Account account) {
        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(account.getUsername(), account.getPassword()));

        SecurityContextHolder.getContext().setAuthentication(authentication);
        account = accountService.getAccountLogin(account.getUsername(), account.getPassword());
        String token = jwtService.createToken(authentication);
        AccountToken accountToken = new AccountToken(account.getId(), account.getUsername(), token, account.getFirstname(), account.getLastname(), account.getAddress(),
                                                   account.getEmail(), account.getPhone(), account.getAvatar(), account.getWallet(), account.getStatus(), account.getRole());
        return accountToken;
    }

}