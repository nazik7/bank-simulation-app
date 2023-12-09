package com.cydeo.service.impl;

import com.cydeo.dto.AccountDTO;
import com.cydeo.dto.TransactionDTO;
import com.cydeo.enums.AccountType;
import com.cydeo.exception.AccountOwnerShipException;
import com.cydeo.exception.BadRequestEXception;
import com.cydeo.exception.BalanceNotSufficientException;
import com.cydeo.exception.UnderConstructionException;
import com.cydeo.mapper.TransactionMapper;
import com.cydeo.repository.AccountRepository;
import com.cydeo.repository.TransactionRepository;
import com.cydeo.service.AccountService;
import com.cydeo.service.TransactionService;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

@Component
public class TransactionServiceImpl implements TransactionService {
    @Value("${under_construction}")
    private boolean underConstruction;
    private final AccountService accountService;
    private final TransactionRepository transactionRepository;
    private final TransactionMapper transactionMapper;

    public TransactionServiceImpl(AccountService accountService,TransactionRepository transactionRepository
    ,TransactionMapper transactionMapper) {
        this.accountService= accountService;
        this.transactionRepository = transactionRepository;
        this.transactionMapper = transactionMapper;
    }

    @Override
    public TransactionDTO makeTransfer(AccountDTO sender, AccountDTO receiver, BigDecimal amount, Date creationDate, String message) {
        if(!underConstruction) {
        /*
            -if sender or receiver is null?
            -if sender and receiver us the same account?
            - if sender has enough balance to make transfer
            -if both accounts are checking, if not, one of them saving, it needs to be same userID
         */
            validateAccount(sender, receiver);
            checkAccountOwnership(sender, receiver);
            executeBalanceAndUpdateIfRequired(amount, sender, receiver);

            TransactionDTO transactionDTO = new TransactionDTO();
            transactionRepository.save(transactionMapper.convertToEntity(transactionDTO));
            return transactionDTO;
        }else{
            throw new UnderConstructionException("App is under construction, please try again later.");
        }

    }
    private void validateAccount(AccountDTO sender, AccountDTO receiver){
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
        findAccountById(sender.getId());
        findAccountById(receiver.getId());

    }

    private void findAccountById(Long id) {
        accountService.retrieveById(id);
    }

    private void checkAccountOwnership(AccountDTO sender, AccountDTO receiver){
        //
//        if((sender.getAccountType().equals(AccountType.SAVING) || receiver.getAccountType().equals(AccountType.SAVING))&&
//        !sender.getUserId().equals(receiver.getUserId())){
//            throw new AccountOwnerShipException("Accounts must be the same");
//        }

        if((sender.getAccountType().equals(AccountType.SAVING)||receiver.getAccountType().equals(AccountType.SAVING))
                && !sender.getUserId().equals(receiver.getUserId())){
            throw new AccountOwnerShipException("If one of the account is saving, user must be the same for sender and receiver");
        }
    }
    private void executeBalanceAndUpdateIfRequired(BigDecimal amount, AccountDTO sender, AccountDTO receiver){
        if(checkSenderBalance(sender, amount)){
            sender.setBalance(sender.getBalance().subtract(amount));
            receiver.setBalance(receiver.getBalance().add(amount));
        }else{
            throw new BalanceNotSufficientException("Balance is not enough for transfer");
        }
    }


    private boolean checkSenderBalance(AccountDTO sender, BigDecimal amount){
        //return sender.getBalance().compareTo(amount)>= 0;
        return sender.getBalance().subtract(amount).compareTo(BigDecimal.ZERO) >=0 ;
    }

    @Override
    public List<TransactionDTO> findAllTransaction() {
        return transactionRepository.findAll().stream()
                .map(transactionMapper::convertToDto).collect(Collectors.toList());
    }

    @Override
    public List<TransactionDTO> last10Transactions() {

        return transactionRepository.findLast10Transactions();
    }

    @Override
    public List<TransactionDTO> findTransactionsById(Long id) {

        return transactionRepository.findTransactionsById(id);
    }
}
