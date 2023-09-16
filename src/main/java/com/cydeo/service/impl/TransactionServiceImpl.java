package com.cydeo.service.impl;

import com.cydeo.enums.AccountType;
import com.cydeo.exception.AccountOwnerShipException;
import com.cydeo.exception.BadRequestEXception;
import com.cydeo.exception.BalanceNotSufficientException;
import com.cydeo.model.Account;
import com.cydeo.model.Transaction;
import com.cydeo.repository.AccountRepository;
import com.cydeo.service.TransactionService;
import org.apache.catalina.filters.AddDefaultCharsetFilter;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;
import java.util.UUID;
@Component
public class TransactionServiceImpl implements TransactionService {
    private final AccountRepository accountRepository;

    public TransactionServiceImpl(AccountRepository accountRepository) {
        this.accountRepository = accountRepository;
    }

    @Override
    public Transaction makeTransfer(Account sender, Account receiver, BigDecimal amount, Date creationDate, String message) {
        /*
            -if sender or receiver is null?
            -if sender and receiver us the same account?
            - if sender has enough balance to make transfer
            -if both accounts are checking, if not, one of them saving, it needs to be same userID
         */
        validateAccount(sender, receiver);
        checkAccountOwnership(sender, receiver);
        return null;

    }
    private void validateAccount(Account sender, Account receiver){
        /*
        -if any of the account is null
        -if account ids are the same(same account
        -if the account exist in the database
         */
        if(sender == null || receiver ==null){
            throw new BadRequestEXception("Sender or Receiver cannot be null");
        }

        //if the accounts are the same throw BadRequestException with saving accounts needs to be different
        if(sender.getId().equals(receiver.getId())){
            throw new BadRequestEXception("Sender account needs to be different than the receiver account");
        }
        //findAccountById(id);

    }

    private void findAccountById(UUID id) {
        accountRepository.findById(id);
    }

    private void checkAccountOwnership(Account sender, Account receiver){
        //
        if((sender.getAccountType().equals(AccountType.SAVING) ||
                receiver.getAccountType().equals(AccountType.SAVING))&&
        !sender.getUserId().equals(receiver.getUserId())){
            throw new AccountOwnerShipException("Accounts must be the same");
        }
    }
    private void executeBalanceAndUpdateIfRequired(BigDecimal amount, Account sender, Account receiver){
        if(checkSenderBalance(sender, amount)){
            sender.setBalance(sender.getBalance().subtract(amount));
            receiver.setBalance(receiver.getBalance().add(amount));
        }else{
            throw new BalanceNotSufficientException("Balance is not enough for transfer");
        }
    }


    private boolean checkSenderBalance(Account sender, BigDecimal amount){
        //return sender.getBalance().compareTo(amount)>= 0;
        return sender.getBalance().subtract(amount).compareTo(BigDecimal.ZERO) >=0 ;
    }

    @Override
    public List<Transaction> findAllTransaction() {
        return null;
    }
}
