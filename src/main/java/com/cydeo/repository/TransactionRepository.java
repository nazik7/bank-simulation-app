package com.cydeo.repository;

import com.cydeo.dto.TransactionDTO;
import com.cydeo.entity.Transaction;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;


public interface TransactionRepository extends JpaRepository<Transaction, Long> {

    //List<Transaction> findAllByOrderByCreateDateDescLimit10();
    @Query(value = "Select * from transactions ORDER BY create_date DESC LIMIT 10",nativeQuery = true)
    List<Transaction> findLast10Transactions();

    //List<Transaction> findAllBySender_IdOrReceiver_Id(Long id);

    @Query("SELECT t FROM Transaction t WHERE t.sender.id = ?1 OR t.receiver.id = ?1")
    List<Transaction> findTransactionListByAccountId(Long id);
}
