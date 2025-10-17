package com.example.jakartaee.repository;

import com.example.jakartaee.entity.Contact;
import jakarta.annotation.Resource;
import jakarta.enterprise.context.ApplicationScoped;
import javax.sql.DataSource;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

/**
 * JDBC-based repository for Contact entity (alternative to JPA)
 */
@ApplicationScoped
public class ContactRepository {

    @Resource(lookup = "java:comp/env/jdbc/SampleDS")
    private DataSource dataSource;

    public Contact save(Contact contact) throws SQLException {
        if (contact.getId() == null) {
            return insert(contact);
        } else {
            return update(contact);
        }
    }

    private Contact insert(Contact contact) throws SQLException {
        String sql = "INSERT INTO contacts (first_name, last_name, phone, email, account_id) " +
                     "VALUES (?, ?, ?, ?, ?) RETURNING id";

        try (Connection conn = dataSource.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setString(1, contact.getFirstName());
            stmt.setString(2, contact.getLastName());
            stmt.setString(3, contact.getPhone());
            stmt.setString(4, contact.getEmail());
            stmt.setLong(5, contact.getAccount().getId());

            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    contact.setId(rs.getLong("id"));
                }
            }
        }

        return contact;
    }

    private Contact update(Contact contact) throws SQLException {
        String sql = "UPDATE contacts SET first_name = ?, last_name = ?, phone = ?, email = ?, account_id = ? " +
                     "WHERE id = ?";

        try (Connection conn = dataSource.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setString(1, contact.getFirstName());
            stmt.setString(2, contact.getLastName());
            stmt.setString(3, contact.getPhone());
            stmt.setString(4, contact.getEmail());
            stmt.setLong(5, contact.getAccount().getId());
            stmt.setLong(6, contact.getId());

            stmt.executeUpdate();
        }

        return contact;
    }

    public Optional<Contact> findById(Long id) throws SQLException {
        String sql = "SELECT id, first_name, last_name, phone, email, account_id FROM contacts WHERE id = ?";

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

    public List<Contact> findAll() throws SQLException {
        String sql = "SELECT id, first_name, last_name, phone, email, account_id FROM contacts " +
                     "ORDER BY last_name, first_name";
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

    public List<Contact> findByAccountId(Long accountId) throws SQLException {
        String sql = "SELECT id, first_name, last_name, phone, email, account_id FROM contacts " +
                     "WHERE account_id = ? ORDER BY last_name, first_name";
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

    public void delete(Long id) throws SQLException {
        String sql = "DELETE FROM contacts WHERE id = ?";

        try (Connection conn = dataSource.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setLong(1, id);
            stmt.executeUpdate();
        }
    }

    public long count() throws SQLException {
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

    private Contact mapResultSetToContact(ResultSet rs) throws SQLException {
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
