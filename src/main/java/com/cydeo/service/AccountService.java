package com.cydeo.service;

import com.cydeo.enums.AccountType;
import com.cydeo.model.Account;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;
import java.util.UUID;

public interface AccountService {

     Account createNewAccount(BigDecimal balance, Date createDate, AccountType accountType, Long userId);

     List<Account> listAllAccount();

     void delete(UUID userId);

     void activate(UUID id);

     Account retrieveById(UUID id);
}
