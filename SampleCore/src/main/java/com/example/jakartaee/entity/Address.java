package com.example.jakartaee.entity;

import java.io.Serializable;
import com.example.jakartaee.EntityConstants;
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
import jakarta.persistence.Table;

@Entity
@Table(name = "addresses")
@NamedQueries({
    @NamedQuery(name = "Address.findAll",
        query = "SELECT a FROM Address a ORDER BY a.city, a.state"),
    @NamedQuery(name = "Address.findByContact",
        query = "SELECT a FROM Address a WHERE a.contact.id = :contactId"),
    @NamedQuery(name = "Address.findByCity",
        query = "SELECT a FROM Address a WHERE a.city = :city")})
public class Address implements Serializable {

  private static final long serialVersionUID = 1L;

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  @Column(nullable = false, length = EntityConstants.COLUMN_LENGTH_200)
  private String street;

  @Column(nullable = false, length = EntityConstants.COLUMN_LENGTH_100)
  private String city;

  @Column(length = EntityConstants.COLUMN_LENGTH_50)
  private String state;

  @Column(name = "zip_code", length = EntityConstants.COLUMN_LENGTH_50)
  private String zipCode;

  @Column(nullable = false, length = EntityConstants.COLUMN_LENGTH_100)
  private String country;

  @ManyToOne(fetch = FetchType.LAZY)
  @JoinColumn(name = "contact_id", nullable = false)
  private Contact contact;

  // Constructors
  public Address() {
    // default constructor
  }

  public Address(final String street, final String city, final String state, final String zipCode,
      final String country) {
    this.street = street;
    this.city = city;
    this.state = state;
    this.zipCode = zipCode;
    this.country = country;
  }

  // Getters and Setters
  public final Long getId() {
    return id;
  }

  public final void setId(final Long id) {
    this.id = id;
  }

  public final String getStreet() {
    return street;
  }

  public final void setStreet(final String street) {
    this.street = street;
  }

  public final String getCity() {
    return city;
  }

  public final void setCity(final String city) {
    this.city = city;
  }

  public final String getState() {
    return state;
  }

  public final void setState(final String state) {
    this.state = state;
  }

  public final String getZipCode() {
    return zipCode;
  }

  public final void setZipCode(final String zipCode) {
    this.zipCode = zipCode;
  }

  public final String getCountry() {
    return country;
  }

  public final void setCountry(final String country) {
    this.country = country;
  }

  public final Contact getContact() {
    return contact;
  }

  public final void setContact(final Contact contact) {
    this.contact = contact;
  }

  // Helper method
  public final String getFullAddress() {
    StringBuilder sb = new StringBuilder();
    sb.append(street);
    if (city != null && !city.isEmpty()) {
      sb.append(", ").append(city);
    }
    if (state != null && !state.isEmpty()) {
      sb.append(", ").append(state);
    }
    if (zipCode != null && !zipCode.isEmpty()) {
      sb.append(" ").append(zipCode);
    }
    if (country != null && !country.isEmpty()) {
      sb.append(", ").append(country);
    }
    return sb.toString();
  }

  @Override
  public final boolean equals(final Object o) {
    if (this == o) {
      return true;
    }
    if (!(o instanceof Address)) {
      return false;
    }
    Address address = (Address) o;
    return id != null && id.equals(address.id);
  }

  @Override
  public final int hashCode() {
    return getClass().hashCode();
  }

  @Override
  public final String toString() {
    return "Address{" + "id=" + id + ", street='" + street + '\'' + ", city='" + city + '\''
        + ", state='" + state + '\'' + ", zipCode='" + zipCode + '\'' + ", country='" + country
        + '\'' + '}';
  }
}
