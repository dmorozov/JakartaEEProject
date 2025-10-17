package com.example.jakartaee.services;

import java.util.List;

import com.example.jakartaee.entity.Account;

import jakarta.ejb.EJB;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Named;

@Named
@ApplicationScoped
public class AccountEjbService {
  @EJB
  private AccountEjbService accountService;

  public List<Account> findAllAccounts() {
    return accountService.findAllAccounts();
  }
}
