package com.ironhack.midtermproject.repository.accounts;

import com.ironhack.midtermproject.model.accounts.Account;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AccountRepository extends JpaRepository<Account, Integer> {
}
