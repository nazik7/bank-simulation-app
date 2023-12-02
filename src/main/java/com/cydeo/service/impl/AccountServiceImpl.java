package com.cydeo.service.impl;

import com.cydeo.dto.AccountDTO;
import com.cydeo.enums.AccountStatus;
import com.cydeo.enums.AccountType;
import com.cydeo.repository.AccountRepository;
import com.cydeo.service.AccountService;
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
    public AccountDTO createNewAccount(BigDecimal balance, Date createDate, AccountType accountType, Long userId) {
        AccountDTO accountDTO = AccountDTO.builder().id(UUID.randomUUID()).userId(userId)
                .balance(balance).accountType(accountType).creationDate(createDate)
                .accountStatus(AccountStatus.ACTIVE).build();
        return accountRepository.save(accountDTO);
    }

    @Override
    public List<AccountDTO> listAllAccount() {
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
    public AccountDTO retrieveById(UUID id) {
        return accountRepository.findById(id);
    }

}
