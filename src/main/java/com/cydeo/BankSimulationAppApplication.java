package com.cydeo;

import com.cydeo.enums.AccountType;
import com.cydeo.dto.AccountDTO;
import com.cydeo.service.AccountService;
import com.cydeo.service.TransactionService;
import org.modelmapper.ModelMapper;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Bean;

import java.math.BigDecimal;
import java.util.Date;

@SpringBootApplication
public class BankSimulationAppApplication {

    public static void main(String[] args) {

        ApplicationContext container = SpringApplication.run(BankSimulationAppApplication.class, args);
        //get account and transaction service beans
//        AccountService accountService = container.getBean(AccountService.class);
//        TransactionService transactionService = container.getBean(TransactionService.class);
//
//        //create 2 accounts
//        AccountDTO sender = accountService.createNewAccount(BigDecimal.valueOf(70),new Date(), AccountType.CHECKING,1L);
//        AccountDTO receiver = accountService.createNewAccount(BigDecimal.valueOf(50),new Date(), AccountType.CHECKING,2L);
//        accountService.listAllAccount().forEach(System.out::println);
//
//        transactionService.makeTransfer(sender,receiver,new BigDecimal(40),new Date(),"Transaction 1");
//        accountService.listAllAccount().forEach(System.out::println);
    }
    @Bean
    public ModelMapper modelMapper(){
        return new ModelMapper();
    }

}
