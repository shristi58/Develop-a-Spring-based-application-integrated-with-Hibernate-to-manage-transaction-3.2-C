package com.example.bank;

import com.example.bank.model.Account;
import com.example.bank.service.AccountService;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import java.math.BigDecimal;

@SpringBootApplication
public class BankingApplication implements CommandLineRunner {

    private final AccountService accountService;

    public BankingApplication(AccountService accountService) {
        this.accountService = accountService;
    }

    public static void main(String[] args) {
        SpringApplication.run(BankingApplication.class, args);
    }

    @Override
    public void run(String... args) throws Exception {
        Account a1 = new Account("ACC1001", "Alice", new BigDecimal("1000.00"));
        Account a2 = new Account("ACC1002", "Bob", new BigDecimal("200.00"));
        accountService.createAccount(a1);
        accountService.createAccount(a2);

        System.out.println("Before transfer:");
        System.out.println("Alice: " + accountService.getByAccountNumber("ACC1001").getBalance());
        System.out.println("Bob: " + accountService.getByAccountNumber("ACC1002").getBalance());

        try {
            accountService.transfer("ACC1001", "ACC1002", new BigDecimal("300.00"));
        } catch (Exception ex) {
            System.out.println("Transfer failed: " + ex.getMessage());
        }

        System.out.println("After transfer:");
        System.out.println("Alice: " + accountService.getByAccountNumber("ACC1001").getBalance());
        System.out.println("Bob: " + accountService.getByAccountNumber("ACC1002").getBalance());

        try {
            accountService.transfer("ACC1002", "ACC1001", new BigDecimal("1000.00"));
        } catch (Exception ex) {
            System.out.println("Second transfer failed (should rollback): " + ex.getMessage());
        }

        System.out.println("Final balances:");
        System.out.println("Alice: " + accountService.getByAccountNumber("ACC1001").getBalance());
        System.out.println("Bob: " + accountService.getByAccountNumber("ACC1002").getBalance());
    }
}
