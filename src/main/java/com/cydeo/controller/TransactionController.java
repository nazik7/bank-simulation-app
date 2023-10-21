package com.cydeo.controller;

import com.cydeo.model.Account;
import com.cydeo.model.Transaction;
import com.cydeo.service.AccountService;
import com.cydeo.service.TransactionService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

import java.util.Date;

@Controller
public class TransactionController {

    private final TransactionService transactionService;
    private final AccountService accountService;
    public TransactionController(TransactionService transactionService, AccountService accountService){
        this.transactionService = transactionService;
        this.accountService = accountService;
    }
    @GetMapping("/make-transfer")
    public String getMakeTransfer(Model model){
        model.addAttribute("transaction", Transaction.builder().build());
        model.addAttribute("accounts",accountService.listAllAccount());
        model.addAttribute("lastTransactions", transactionService.last10Transactions());
        return "/transaction/make-transfer";
    }

    @PostMapping("/transfer")
    public String makeTransfer(@ModelAttribute("transaction")Transaction transaction){
        Account sender = accountService.retrieveById(transaction.getSender());
        Account receiver = accountService.retrieveById(transaction.getReceiver());
        transactionService.makeTransfer(sender, receiver,transaction.getAmount(), new Date(), transaction.getMessage());
        return "redirect:/make-transfer";
    }

    @GetMapping("/transactions")
    public String getTransactions(){
        return "transaction/transactions";
    }
}
