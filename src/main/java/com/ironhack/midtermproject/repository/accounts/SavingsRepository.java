package com.ironhack.midtermproject.repository.accounts;

import com.ironhack.midtermproject.model.accounts.Savings;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface SavingsRepository extends JpaRepository<Savings, Integer> {
}
