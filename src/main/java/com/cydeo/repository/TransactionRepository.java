package com.cydeo.repository;

import com.cydeo.model.Transaction;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Component
public class TransactionRepository {

    public static List<Transaction> transactionList = new ArrayList<>();
    public Transaction save(Transaction transaction){
        transactionList.add(transaction);
        return transaction;
    }


    public List<Transaction> findAll() {
        return transactionList;
    }

    public List<Transaction> findLast10Transactions(){
        return findAll().stream()
                .sorted(Comparator.comparing(Transaction::getCreateDate).reversed())
                .limit(10)
                .collect(Collectors.toList());
    }

    public List<Transaction> findTransactionsById(UUID id){

        return findAll().stream()
                .filter(transaction -> transaction.getReceiver().equals(id) ||
                        transaction.getSender().equals(id))
                .collect(Collectors.toList());
    }
}
