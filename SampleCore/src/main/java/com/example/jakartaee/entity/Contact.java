package com.example.jakartaee.entity;


import java.io.Serializable;
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
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.NamedQuery;
import jakarta.persistence.NamedQueries;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;

@Entity
@Table(name = "contacts")
@NamedQueries({
    @NamedQuery(name = "Contact.findAll", query = "SELECT c FROM Contact c ORDER BY c.lastName, c.firstName"),
    @NamedQuery(name = "Contact.findByAccount", query = "SELECT c FROM Contact c WHERE c.account.id = :accountId"),
    @NamedQuery(name = "Contact.findByEmail", query = "SELECT c FROM Contact c WHERE c.email = :email")
})
public class Contact implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "first_name", nullable = false, length = EntityConstants.COLUMN_LENGTH_50)
    private String firstName;

    @Column(name = "last_name", nullable = false, length = EntityConstants.COLUMN_LENGTH_50)
    private String lastName;

    @Column(length = EntityConstants.COLUMN_LENGTH_50)
    private String phone;

    @Column(length = EntityConstants.COLUMN_EMAIL)
    private String email;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "account_id", nullable = false)
    private Account account;

    @OneToMany(mappedBy = "contact", cascade = CascadeType.ALL, orphanRemoval = true, fetch = FetchType.LAZY)
    private List<Address> addresses = new ArrayList<>();

    // Constructors
    public Contact() {
      // default constructor
    }

    public Contact(final String firstName, final String lastName, final String phone,
        final String email) {
      this.firstName = firstName;
      this.lastName = lastName;
      this.phone = phone;
      this.email = email;
    }

    // Getters and Setters
    public final Long getId() {
      return id;
    }

    public final void setId(final Long id) {
      this.id = id;
    }

    public final String getFirstName() {
      return firstName;
    }

    public final void setFirstName(final String firstName) {
      this.firstName = firstName;
    }

    public final String getLastName() {
      return lastName;
    }

    public final void setLastName(final String lastName) {
      this.lastName = lastName;
    }

    public final String getPhone() {
      return phone;
    }

    public final void setPhone(final String phone) {
      this.phone = phone;
    }

    public final String getEmail() {
      return email;
    }

    public final void setEmail(final String email) {
      this.email = email;
    }

    public final Account getAccount() {
      return account;
    }

    public final void setAccount(final Account account) {
      this.account = account;
    }

    public final List<Address> getAddresses() {
      return addresses;
    }

    public final void setAddresses(final List<Address> addresses) {
      this.addresses = addresses;
    }

    // Helper methods
    public final void addAddress(final Address address) {
      addresses.add(address);
      address.setContact(this);
    }

    public final void removeAddress(final Address address) {
      addresses.remove(address);
      address.setContact(null);
    }

    public final String getFullName() {
      return firstName + " " + lastName;
    }

    @Override
    public final boolean equals(final Object o) {
      if (this == o) {
        return true;
      }
      if (!(o instanceof Contact)) {
        return false;
      }
      Contact contact = (Contact) o;
      return id != null && id.equals(contact.id);
    }

    @Override
    public final int hashCode() {
      return getClass().hashCode();
    }

    @Override
    public final String toString() {
      return "Contact{" + "id=" + id + ", firstName='" + firstName + '\'' + ", lastName='"
          + lastName + '\'' + ", phone='" + phone + '\'' + ", email='" + email + '\'' + '}';
    }
}
