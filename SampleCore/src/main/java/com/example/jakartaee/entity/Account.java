package com.example.jakartaee.entity;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import com.example.jakartaee.EntityConstants;
import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.NamedQueries;
import jakarta.persistence.NamedQuery;
import jakarta.persistence.OneToMany;
import jakarta.persistence.PrePersist;
import jakarta.persistence.Table;

@Entity
@Table(name = "accounts")
@NamedQueries({
    @NamedQuery(name = "Account.findAll", query = "SELECT a FROM Account a ORDER BY a.name"),
    @NamedQuery(name = "Account.findByEmail", query = "SELECT a FROM Account a WHERE a.email = :email")
})
public class Account implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, length = EntityConstants.COLUMN_LENGTH_100)
    private String name;

    @Column(nullable = false, unique = true, length = EntityConstants.COLUMN_EMAIL)
    private String email;

    @Column(name = "created_date", nullable = false, updatable = false)
    private LocalDateTime createdDate;

    @OneToMany(mappedBy = "account", cascade = CascadeType.ALL, orphanRemoval = true, fetch = FetchType.LAZY)
    private List<Contact> contacts = new ArrayList<>();

    @PrePersist
    protected final void onCreate() {
      createdDate = LocalDateTime.now();
    }

    // Constructors
    public Account() {
      // default constructor
    }

    public Account(final String name, final String email) {
      this.name = name;
      this.email = email;
    }

    // Getters and Setters
    public final Long getId() {
      return id;
    }

    public final void setId(final Long id) {
      this.id = id;
    }

    public final String getName() {
      return name;
    }

    public final void setName(final String name) {
      this.name = name;
    }

    public final String getEmail() {
      return email;
    }

    public final void setEmail(final String email) {
      this.email = email;
    }

    public final LocalDateTime getCreatedDate() {
      return createdDate;
    }

    public final void setCreatedDate(final LocalDateTime createdDate) {
      this.createdDate = createdDate;
    }

    public final List<Contact> getContacts() {
      return contacts;
    }

    public final void setContacts(final List<Contact> contacts) {
      this.contacts = contacts;
    }

    // Helper methods
    public final void addContact(final Contact contact) {
      contacts.add(contact);
      contact.setAccount(this);
    }

    public final void removeContact(final Contact contact) {
      contacts.remove(contact);
      contact.setAccount(null);
    }

    @Override
    public final boolean equals(final Object o) {
      if (this == o) {
        return true;
      }
      if (!(o instanceof Account)) {
        return false;
      }
      Account account = (Account) o;
      return id != null && id.equals(account.id);
    }

    @Override
    public final int hashCode() {
      return getClass().hashCode();
    }

    @Override
    public final String toString() {
      return "Account{" + "id=" + id + ", name='" + name + '\'' + ", email='" + email + '\''
          + ", createdDate=" + createdDate + '}';
    }
}
