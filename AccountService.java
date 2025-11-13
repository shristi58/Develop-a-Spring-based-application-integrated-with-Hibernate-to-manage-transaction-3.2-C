package com.example.bank.service;

import com.example.bank.exception.InsufficientFundsException;
import com.example.bank.model.Account;
import com.example.bank.repository.AccountRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;

@Service
public class AccountService {

    private final AccountRepository repo;

    public AccountService(AccountRepository repo) {
        this.repo = repo;
    }

    public Account createAccount(Account a) {
        return repo.save(a);
    }

    public Account getByAccountNumber(String accNo) {
        return repo.findByAccountNumber(accNo).orElse(null);
    }

    @Transactional
    public void transfer(String fromAccNo, String toAccNo, BigDecimal amount) {
        Account from = repo.findByAccountNumber(fromAccNo)
                .orElseThrow(() -> new IllegalArgumentException("From account not found"));
        Account to = repo.findByAccountNumber(toAccNo)
                .orElseThrow(() -> new IllegalArgumentException("To account not found"));

        if (amount.compareTo(BigDecimal.ZERO) <= 0) throw new IllegalArgumentException("Amount must be > 0");

        if (from.getBalance().compareTo(amount) < 0) {
            throw new InsufficientFundsException("Insufficient funds in account " + fromAccNo);
        }

        from.setBalance(from.getBalance().subtract(amount));
        to.setBalance(to.getBalance().add(amount));

        repo.save(from);
        repo.save(to);
    }
}
