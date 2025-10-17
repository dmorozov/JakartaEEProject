package com.example.jakartaee.action;

import com.example.jakartaee.entity.Account;
import com.example.jakartaee.service.AccountService;

import org.apache.struts2.ActionSupport;
import org.apache.struts2.ModelDriven;
import org.apache.struts2.Preparable;
import jakarta.ejb.EJB;
import jakarta.inject.Inject;
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

    public String list() {
        try {
            accounts = accountService.findAllAccounts();
            return SUCCESS;
        } catch (Exception e) {
            addActionError("Error retrieving accounts: " + e.getMessage());
            return ERROR;
        }
    }

    public String view() {
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

    public String create() {
        account = new Account();
        return SUCCESS;
    }

    public String edit() {
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

    public String save() {
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

    public String delete() {
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
    public Account getModel() {
        return account;
    }

    // Getters and Setters
    public Account getAccount() {
        return account;
    }

    public void setAccount(Account account) {
        this.account = account;
    }

    public List<Account> getAccounts() {
        return accounts;
    }

    public void setAccounts(List<Account> accounts) {
        this.accounts = accounts;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }
}
