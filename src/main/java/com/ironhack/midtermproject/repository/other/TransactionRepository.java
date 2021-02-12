package com.ironhack.midtermproject.repository.other;

import com.ironhack.midtermproject.model.other.Transaction;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TransactionRepository extends JpaRepository<Transaction, Integer> {
}
