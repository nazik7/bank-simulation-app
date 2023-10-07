package com.cydeo.controller;

import com.cydeo.service.AccountService;
import com.cydeo.service.TransactionService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class TransactionController {

    TransactionService transactionService;
    AccountService accountService;
    public TransactionController(TransactionService transactionService, AccountService accountService){
        this.transactionService = transactionService;
        this.accountService = accountService;
    }
    @GetMapping("/make-transfer")
    public String makeTransfer(Model model){
        model.addAttribute("accounts",accountService.listAllAccount());
        return "/transaction/make-transfer";
    }
}
