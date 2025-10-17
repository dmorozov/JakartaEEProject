package com.example.jakartaee.service;

import com.example.jakartaee.entity.Account;
import jakarta.ejb.Local;
import java.util.List;

@Local
public interface AccountService {

    Account createAccount(Account account);

    Account updateAccount(Account account);

    void deleteAccount(Long id);

    Account findAccountById(Long id);

    Account findAccountByEmail(String email);

    List<Account> findAllAccounts();

    long countAccounts();
}
