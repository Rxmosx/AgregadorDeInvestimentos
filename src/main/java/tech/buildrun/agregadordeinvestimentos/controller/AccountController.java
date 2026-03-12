package tech.buildrun.agregadordeinvestimentos.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import tech.buildrun.agregadordeinvestimentos.controller.dto.CreateAccountDto;
import tech.buildrun.agregadordeinvestimentos.entity.Account;
import tech.buildrun.agregadordeinvestimentos.entity.User;
import tech.buildrun.agregadordeinvestimentos.service.AccountService;

import java.net.URI;
import java.util.List;

@RestController
@RequestMapping("v1/{userId}/accounts")
public class AccountController {

    private AccountService accountService;

    public AccountController(AccountService accountService) {
        this.accountService = accountService;
    }

}
