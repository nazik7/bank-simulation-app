package com.cydeo.service.impl;

import com.cydeo.enums.AccountStatus;
import com.cydeo.enums.AccountType;
import com.cydeo.model.Account;
import com.cydeo.repository.AccountRepository;
import com.cydeo.service.AccountService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;
import java.util.UUID;
@Component
public class AccountServiceImpl implements AccountService {
    AccountRepository accountRepository;
    public AccountServiceImpl(AccountRepository accountRepository){
        this.accountRepository = accountRepository;
    }
    @Override
    public Account createNewAccount(BigDecimal balance, Date createDate, AccountType accountType, Long userId) {
        Account account = Account.builder().id(UUID.randomUUID()).userId(userId)
                .balance(balance).accountType(accountType).creationDate(createDate)
                .accountStatus(AccountStatus.ACTIVE).build();
        return accountRepository.save(account);
    }

    @Override
    public List<Account> listAllAccount() {
        return accountRepository.findAll();
    }

    @Override
    public void delete(UUID id) {
        accountRepository.findById(id).setAccountStatus(AccountStatus.DELETED);
    }

    @Override
    public void activate(UUID id) {
        accountRepository.findById(id).setAccountStatus(AccountStatus.ACTIVE);

    }

    @Override
    public Account retrieveById(UUID id) {
        return accountRepository.findById(id);
    }

}
