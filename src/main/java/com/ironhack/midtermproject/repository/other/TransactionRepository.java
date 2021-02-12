package com.ironhack.midtermproject.repository.other;

import com.ironhack.midtermproject.model.other.Transaction;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface TransactionRepository extends JpaRepository<Transaction, Integer> {

	List<Transaction> findByAccountId(Integer accountId);
}
