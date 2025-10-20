package com.example.jakartaee.repository;

import com.example.jakartaee.EntityConstants;
import com.example.jakartaee.entity.Contact;
import jakarta.annotation.Resource;
import jakarta.enterprise.context.ApplicationScoped;
import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

/**
 * JDBC-based repository for Contact entity (alternative to JPA).
 */
@ApplicationScoped
public class ContactRepository {

  @Resource(lookup = "java:comp/env/jdbc/SampleDS")
  private DataSource dataSource;

  public final Contact save(final Contact contact) throws SQLException {
    if (contact.getId() == null) {
      return insert(contact);
    } else {
      return update(contact);
    }
  }

  @SuppressWarnings("checkstyle:MagicNumberCheck")
  private Contact insert(final Contact contact) throws SQLException {
    String sql = "INSERT INTO contacts (first_name, last_name, phone, email, account_id) "
        + "VALUES (?, ?, ?, ?, ?) RETURNING id";

    try (Connection conn = dataSource.getConnection();
        PreparedStatement stmt = conn.prepareStatement(sql)) {

      stmt.setString(EntityConstants.INDEX_1, contact.getFirstName());
      stmt.setString(EntityConstants.INDEX_2, contact.getLastName());
      stmt.setString(EntityConstants.INDEX_3, contact.getPhone());
      stmt.setString(EntityConstants.INDEX_4, contact.getEmail());
      stmt.setLong(EntityConstants.INDEX_5, contact.getAccount().getId());

      try (ResultSet rs = stmt.executeQuery()) {
        if (rs.next()) {
          contact.setId(rs.getLong("id"));
        }
      }
    }

    return contact;
  }

  private Contact update(final Contact contact) throws SQLException {
    String sql =
        "UPDATE contacts SET first_name = ?, last_name = ?, phone = ?, email = ?, account_id = ? "
            + "WHERE id = ?";

    try (Connection conn = dataSource.getConnection();
        PreparedStatement stmt = conn.prepareStatement(sql)) {

      stmt.setString(EntityConstants.INDEX_1, contact.getFirstName());
      stmt.setString(EntityConstants.INDEX_2, contact.getLastName());
      stmt.setString(EntityConstants.INDEX_3, contact.getPhone());
      stmt.setString(EntityConstants.INDEX_4, contact.getEmail());
      stmt.setLong(EntityConstants.INDEX_5, contact.getAccount().getId());
      stmt.setLong(EntityConstants.INDEX_6, contact.getId());

      stmt.executeUpdate();
    }

    return contact;
  }

  public final Optional<Contact> findById(final Long id) throws SQLException {
    String sql =
        "SELECT id, first_name, last_name, phone, email, account_id FROM contacts WHERE id = ?";

    try (Connection conn = dataSource.getConnection();
        PreparedStatement stmt = conn.prepareStatement(sql)) {

      stmt.setLong(1, id);

      try (ResultSet rs = stmt.executeQuery()) {
        if (rs.next()) {
          return Optional.of(mapResultSetToContact(rs));
        }
      }
    }

    return Optional.empty();
  }

  public final List<Contact> findAll() throws SQLException {
    String sql = "SELECT id, first_name, last_name, phone, email, account_id FROM contacts "
        + "ORDER BY last_name, first_name";
    List<Contact> contacts = new ArrayList<>();

    try (Connection conn = dataSource.getConnection();
        PreparedStatement stmt = conn.prepareStatement(sql);
        ResultSet rs = stmt.executeQuery()) {

      while (rs.next()) {
        contacts.add(mapResultSetToContact(rs));
      }
    }

    return contacts;
  }

  public final List<Contact> findByAccountId(final Long accountId) throws SQLException {
    String sql = "SELECT id, first_name, last_name, phone, email, account_id FROM contacts "
        + "WHERE account_id = ? ORDER BY last_name, first_name";
    List<Contact> contacts = new ArrayList<>();

    try (Connection conn = dataSource.getConnection();
        PreparedStatement stmt = conn.prepareStatement(sql)) {

      stmt.setLong(1, accountId);

      try (ResultSet rs = stmt.executeQuery()) {
        while (rs.next()) {
          contacts.add(mapResultSetToContact(rs));
        }
      }
    }

    return contacts;
  }

  public final void delete(final Long id) throws SQLException {
    String sql = "DELETE FROM contacts WHERE id = ?";

    try (Connection conn = dataSource.getConnection();
        PreparedStatement stmt = conn.prepareStatement(sql)) {

      stmt.setLong(1, id);
      stmt.executeUpdate();
    }
  }

  public final long count() throws SQLException {
    String sql = "SELECT COUNT(*) FROM contacts";

    try (Connection conn = dataSource.getConnection();
        PreparedStatement stmt = conn.prepareStatement(sql);
        ResultSet rs = stmt.executeQuery()) {

      if (rs.next()) {
        return rs.getLong(1);
      }
    }

    return 0;
  }

  private Contact mapResultSetToContact(final ResultSet rs) throws SQLException {
    Contact contact = new Contact();
    contact.setId(rs.getLong("id"));
    contact.setFirstName(rs.getString("first_name"));
    contact.setLastName(rs.getString("last_name"));
    contact.setPhone(rs.getString("phone"));
    contact.setEmail(rs.getString("email"));
    // Note: Account object not fully hydrated to avoid circular dependencies
    // You would need to fetch separately if needed
    return contact;
  }
}
