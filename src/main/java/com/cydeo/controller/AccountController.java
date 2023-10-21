package com.cydeo.controller;

import com.cydeo.enums.AccountType;
import com.cydeo.model.Account;
import com.cydeo.service.AccountService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import java.util.Date;
import java.util.UUID;

@Controller
public class AccountController {
    private final AccountService accountService;

    public AccountController(AccountService accountService) {
        this.accountService = accountService;
    }

    @GetMapping("index")
    public String getAccounts(Model model){
        model.addAttribute("accountList",accountService.listAllAccount());
        return "account/index";
    }

    @GetMapping("/create-form")
    public String getCreateAccount(Model model){
        //need to provide empty account object
        model.addAttribute("account", Account.builder().build());
        //need to provide account type enums to view(to user)
        model.addAttribute("accountTypes", AccountType.values());
        return "/account/create-account";
    }

    //create a method to capture information from UI
    //print them on the console
    ///trigger createNewAccount method, create the account based on the user input
    //once user is created return back to index page

    @PostMapping("/create")
    public String createAccount(@ModelAttribute("account") Account account){
        //BigDecimal balance, Date createDate, AccountType accountType, Long userId
        accountService.createNewAccount(account.getBalance(),new Date(),account.getAccountType(),account.getUserId());
        return "redirect:/index";
    }

    @GetMapping("/delete/{id}")
    public String deleteAccount(@PathVariable("id")UUID id){
        System.out.println(id);
        accountService.delete(id);
        return "redirect:/index";
    }
    @GetMapping("/activate/{id}")
    public String activateAccount(@PathVariable("id")UUID id){
        System.out.println(id);
        accountService.activate(id);
        return "redirect:/index";
    }
}
