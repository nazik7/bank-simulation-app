package com.cydeo.repository;

import com.cydeo.dto.AccountDTO;
import com.cydeo.exception.RecordNotFoundExcdeption;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
public class AccountRepository {

    public static List<AccountDTO> accountDTOList = new ArrayList<>();
    public AccountDTO save(AccountDTO accountDTO){
        accountDTOList.add(accountDTO);
        return accountDTO;
    }

//    public void delete(UUID id){
//        Account accountFound = accountList.stream()
//                .filter(account -> account.getId()==id)
//                .findFirst().get();
//
//        accountList.remove(accountFound);
//    }

    public List<AccountDTO> findAll(){
        return accountDTOList;
    }

    public AccountDTO findById(Long id) {
       return accountDTOList.stream()
                .filter(account -> account.getId().equals(id))
                .findAny()
                .orElseThrow(()->new RecordNotFoundExcdeption("Account is not found in the database "));
    }
}
