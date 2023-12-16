package com.cydeo.service.impl;

import com.cydeo.dto.AccountDTO;
import com.cydeo.entity.Account;
import com.cydeo.enums.AccountStatus;
import com.cydeo.enums.AccountType;
import com.cydeo.mapper.AccountMapper;
import com.cydeo.repository.AccountRepository;
import com.cydeo.service.AccountService;
import org.springframework.stereotype.Component;

import javax.security.auth.login.AccountNotFoundException;
import java.math.BigDecimal;
import java.util.Date;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Component
public class AccountServiceImpl implements AccountService {
    AccountRepository accountRepository;
    private final AccountMapper accountMapper;
    public AccountServiceImpl(AccountRepository accountRepository, AccountMapper accountMapper){
        this.accountRepository = accountRepository;
        this.accountMapper = accountMapper;
    }
    @Override
    public void createNewAccount(AccountDTO accountDTO) {
        accountDTO.setCreationDate(new Date());
        accountDTO.setAccountStatus(AccountStatus.ACTIVE);
        accountRepository.save(accountMapper.convertToEntity(accountDTO));

    }

    @Override
    public List<AccountDTO> listAllAccount() {
        return accountRepository.findAll().stream().map(accountMapper::convertToDto).collect(Collectors.toList());
    }

    @Override
    public void delete(Long id) {
        //Account account = accountRepository.findById(id).orElseThrow(new AccountNotFoundException("Account not found"));
        Account account = accountRepository.findById(id).get();
        account.setAccountStatus(AccountStatus.DELETED);
        accountRepository.save(account);
    }

    @Override
    public void activate(Long id) {
        Account account = accountRepository.findById(id).get();
        account.setAccountStatus(AccountStatus.ACTIVE);
        accountRepository.save(account);


    }

    @Override
    public AccountDTO retrieveById(Long id) {

        return accountMapper.convertToDto(accountRepository.findById(id).get());
    }

    @Override
    public List<AccountDTO> listAllActiveAccount() {
        return accountRepository.findAllByAccountStatus(AccountStatus.ACTIVE).stream()
                .map(accountMapper::convertToDto).collect(Collectors.toList());
    }

    @Override
    public void updateAccount(AccountDTO accountDTO) {
        accountRepository.save(accountMapper.convertToEntity(accountDTO));
    }

}
