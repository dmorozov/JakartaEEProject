package com.example.jakartaee.action;

import com.example.jakartaee.entity.Account;
import com.example.jakartaee.service.AccountService;

import org.apache.struts2.ActionSupport;
import org.apache.struts2.ModelDriven;
import org.apache.struts2.Preparable;
import org.apache.struts2.interceptor.parameter.StrutsParameter;
import jakarta.ejb.EJB;
// import jakarta.inject.Named;

import java.util.List;

// @Named
public class AccountAction extends ActionSupport implements ModelDriven<Account>, Preparable {

  private static final long serialVersionUID = 1L;

  @EJB
  private AccountService accountService;

  private Account account = new Account();
  private List<Account> accounts;
  private Long id;

  /**
   * Prepares the account model before any action method is called.
   */
  @Override
  public void prepare() {
    System.out.println("=== prepare() called ===");
    System.out.println("id value: " + id);
    System.out.println("account object: " + account);
    // Do NOT initialize account here - let method-specific prepare handle it
  }

  /**
   * Method-specific prepare for view action - called AFTER first params interceptor.
   */
  public final void prepareView() {
    System.out.println("=== prepareView() called ===");
    System.out.println("id value in prepareView: " + id);
    if (id != null && id > 0) {
      account = accountService.findAccountById(id);
    }
  }

  /**
   * Method-specific prepare for edit action - called AFTER first params interceptor.
   */
  public final void prepareEdit() {
    System.out.println("=== prepareEdit() called ===");
    System.out.println("id value in prepareEdit: " + id);
    if (id != null && id > 0) {
      account = accountService.findAccountById(id);
    }
  }

  /**
   * Method-specific prepare for save action - called AFTER first params interceptor.
   */
  public final void prepareSave() {
    System.out.println("=== prepareSave() called ===");
    System.out.println("id value in prepareSave: " + id);
    if (id != null && id > 0) {
      account = accountService.findAccountById(id);
    } else {
      account = new Account();
    }
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

  /**
   * Sets the account.
   *
   * @param account
   */
  public void setAccount(final Account account) {
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

  @StrutsParameter
  public final void setId(final Long id) {
    this.id = id;
  }
}
