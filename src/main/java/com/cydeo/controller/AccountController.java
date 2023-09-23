package com.cydeo.controller;

import com.cydeo.service.AccountService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping()
public class AccountController {
    private AccountService accountService;
    @GetMapping("/index")
    public String getAccounts(Model model){
        model.addAttribute(accountService.listAllAccount());
        return "/account/index";
    }
}
