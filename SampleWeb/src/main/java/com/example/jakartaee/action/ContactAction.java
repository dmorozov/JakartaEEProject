package com.example.jakartaee.action;

import com.example.jakartaee.entity.Contact;
import com.example.jakartaee.entity.Account;
import com.example.jakartaee.service.ContactService;
import com.example.jakartaee.service.AccountService;
import org.apache.struts2.ActionSupport;
import org.apache.struts2.ModelDriven;
import org.apache.struts2.Preparable;
import jakarta.ejb.EJB;
import jakarta.inject.Named;

import java.util.List;

@Named
public class ContactAction extends ActionSupport implements ModelDriven<Contact>, Preparable {

    private static final long serialVersionUID = 1L;

    @EJB
    private ContactService contactService;

    @EJB
    private AccountService accountService;

    private Contact contact = new Contact();
    private List<Contact> contacts;
    private List<Account> accounts;
    private Long id;
    private Long accountId;

    @Override
    public void prepare() {
        // Load accounts for dropdown in form
        try {
            accounts = accountService.findAllAccounts();
        } catch (Exception e) {
            addActionError("Error loading accounts: " + e.getMessage());
        }
    }

    public String list() {
        try {
            if (accountId != null) {
                contacts = contactService.findContactsByAccountId(accountId);
            } else {
                contacts = contactService.findAllContacts();
            }
            return SUCCESS;
        } catch (Exception e) {
            addActionError("Error retrieving contacts: " + e.getMessage());
            return ERROR;
        }
    }

    public String view() {
        if (id == null) {
            addActionError("Contact ID is required");
            return INPUT;
        }

        try {
            contact = contactService.findContactById(id);
            if (contact == null) {
                addActionError("Contact not found with ID: " + id);
                return INPUT;
            }
            return SUCCESS;
        } catch (Exception e) {
            addActionError("Error retrieving contact: " + e.getMessage());
            return ERROR;
        }
    }

    public String create() {
        contact = new Contact();
        return SUCCESS;
    }

    public String edit() {
        if (id == null) {
            addActionError("Contact ID is required");
            return INPUT;
        }

        try {
            contact = contactService.findContactById(id);
            if (contact == null) {
                addActionError("Contact not found with ID: " + id);
                return INPUT;
            }
            if (contact.getAccount() != null) {
                accountId = contact.getAccount().getId();
            }
            return SUCCESS;
        } catch (Exception e) {
            addActionError("Error retrieving contact: " + e.getMessage());
            return ERROR;
        }
    }

    public String save() {
        try {
            // Set account if accountId is provided
            if (accountId != null) {
                Account account = accountService.findAccountById(accountId);
                if (account != null) {
                    contact.setAccount(account);
                } else {
                    addActionError("Invalid account selected");
                    return INPUT;
                }
            }

            if (contact.getId() == null) {
                contactService.createContact(contact);
                addActionMessage("Contact created successfully!");
            } else {
                contactService.updateContact(contact);
                addActionMessage("Contact updated successfully!");
            }
            return SUCCESS;
        } catch (Exception e) {
            addActionError("Error saving contact: " + e.getMessage());
            return INPUT;
        }
    }

    public String delete() {
        if (id == null) {
            addActionError("Contact ID is required");
            return ERROR;
        }

        try {
            contactService.deleteContact(id);
            addActionMessage("Contact deleted successfully!");
            return SUCCESS;
        } catch (Exception e) {
            addActionError("Error deleting contact: " + e.getMessage());
            return ERROR;
        }
    }

    @Override
    public Contact getModel() {
        return contact;
    }

    // Getters and Setters
    public Contact getContact() {
        return contact;
    }

    public void setContact(Contact contact) {
        this.contact = contact;
    }

    public List<Contact> getContacts() {
        return contacts;
    }

    public void setContacts(List<Contact> contacts) {
        this.contacts = contacts;
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

    public Long getAccountId() {
        return accountId;
    }

    public void setAccountId(Long accountId) {
        this.accountId = accountId;
    }
}
