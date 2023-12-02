package com.cydeo.controller;

import com.cydeo.dto.AccountDTO;
import com.cydeo.dto.TransactionDTO;
import com.cydeo.service.AccountService;
import com.cydeo.service.TransactionService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import javax.validation.Valid;
import java.util.Date;
import java.util.UUID;

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
        model.addAttribute("transaction", new TransactionDTO());
        model.addAttribute("accounts",accountService.listAllAccount());
        model.addAttribute("lastTransactions", transactionService.last10Transactions());
        return "/transaction/make-transfer";
    }

    @PostMapping("/transfer")
    public String makeTransfer(@Valid @ModelAttribute("transaction") TransactionDTO transactionDTO, BindingResult bindingResult, Model model){
        if(bindingResult.hasErrors()){
            model.addAttribute("accounts",accountService.listAllAccount());
            model.addAttribute("lastTransactions", transactionService.last10Transactions());
            return "/transaction/make-transfer";
        }
        AccountDTO sender = accountService.retrieveById(transactionDTO.getSender());
        AccountDTO receiver = accountService.retrieveById(transactionDTO.getReceiver());
        transactionService.makeTransfer(sender, receiver, transactionDTO.getAmount(), new Date(), transactionDTO.getMessage());
        return "redirect:/make-transfer";
    }

    @GetMapping("/transactions/{id}")
    public String getTransactions(@PathVariable("id")UUID id, Model model){
        model.addAttribute("transactions",transactionService.findTransactionsById(id));
        return "transaction/transactions";
    }
}
