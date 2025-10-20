package com.example.jakartaee.action;

import com.example.jakartaee.entity.Account;
import com.example.jakartaee.service.AccountService;

import org.apache.struts2.ActionSupport;
import org.apache.struts2.ModelDriven;
import org.apache.struts2.Preparable;
import jakarta.ejb.EJB;
import jakarta.inject.Named;

import java.util.List;

@Named
public class AccountAction extends ActionSupport implements ModelDriven<Account>, Preparable {

  private static final long serialVersionUID = 1L;

  @EJB
  private AccountService accountService;

  private Account account = new Account();
  private List<Account> accounts;
  private Long id;

  @Override
  public void prepare() {
    // Prepare method for Preparable interface

  }

  public final String list() {
    try {
      accounts = accountService.findAllAccounts();
      return SUCCESS;
    } catch (Exception e) {
      addActionError("Error retrieving accounts: " + e.getMessage());
      return ERROR;
    }
  }

  public final String view() {
    if (id == null) {
      addActionError("Account ID is required");
      return INPUT;
    }

    try {
      account = accountService.findAccountById(id);
      if (account == null) {
        addActionError("Account not found with ID: " + id);
        return INPUT;
      }
      return SUCCESS;
    } catch (Exception e) {
      addActionError("Error retrieving account: " + e.getMessage());
      return ERROR;
    }
  }

  public final String create() {
    account = new Account();
    return SUCCESS;
  }

  public final String edit() {
    if (id == null) {
      addActionError("Account ID is required");
      return INPUT;
    }

    try {
      account = accountService.findAccountById(id);
      if (account == null) {
        addActionError("Account not found with ID: " + id);
        return INPUT;
      }
      return SUCCESS;
    } catch (Exception e) {
      addActionError("Error retrieving account: " + e.getMessage());
      return ERROR;
    }
  }

  public final String save() {
    try {
      if (account.getId() == null) {
        accountService.createAccount(account);
        addActionMessage("Account created successfully!");
      } else {
        accountService.updateAccount(account);
        addActionMessage("Account updated successfully!");
      }
      return SUCCESS;
    } catch (Exception e) {
      addActionError("Error saving account: " + e.getMessage());
      return INPUT;
    }
  }

  public final String delete() {
    if (id == null) {
      addActionError("Account ID is required");
      return ERROR;
    }

    try {
      accountService.deleteAccount(id);
      addActionMessage("Account deleted successfully!");
      return SUCCESS;
    } catch (Exception e) {
      addActionError("Error deleting account: " + e.getMessage());
      return ERROR;
    }
  }

  @Override
  public final Account getModel() {
    return account;
  }

  // Getters and Setters
  public final Account getAccount() {
    return account;
  }

  public final void setAccount(final Account account) {
    this.account = account;
  }

  public final List<Account> getAccounts() {
    return accounts;
  }

  public final void setAccounts(final List<Account> accounts) {
    this.accounts = accounts;
  }

  public final Long getId() {
    return id;
  }

  public final void setId(final Long id) {
    this.id = id;
  }
}
