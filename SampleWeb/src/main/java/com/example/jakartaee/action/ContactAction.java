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
    public final void prepare() {
      // Load accounts for dropdown in form
      try {
        accounts = accountService.findAllAccounts();
      } catch (Exception e) {
        addActionError("Error loading accounts: " + e.getMessage());
      }
    }

    public final String list() {
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

    public final String view() {
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

    public final String create() {
      contact = new Contact();
      return SUCCESS;
    }

    public final String edit() {
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

    public final String save() {
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

    public final String delete() {
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
    public final Contact getModel() {
      return contact;
    }

    // Getters and Setters
    public final Contact getContact() {
      return contact;
    }

    public final void setContact(final Contact contact) {
      this.contact = contact;
    }

    public final List<Contact> getContacts() {
      return contacts;
    }

    public final void setContacts(final List<Contact> contacts) {
      this.contacts = contacts;
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

    public final Long getAccountId() {
      return accountId;
    }

    public final void setAccountId(final Long accountId) {
        this.accountId = accountId;
    }
}
