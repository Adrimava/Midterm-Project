package com.ironhack.midtermproject.repository.other;

import com.ironhack.midtermproject.model.other.Transaction;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Date;
import java.util.List;

@Repository
public interface TransactionRepository extends JpaRepository<Transaction, Integer> {

	List<Transaction> findByAccountId(Integer accountId);

//	@Query("SELECT sectionId, COUNT(*) FROM Grade GROUP BY sectionId")
//	@Query("SELECT COUNT(*) FROM Transaction W")
	List<Transaction> findByDateBetween(Long date1, Long date2);
}
