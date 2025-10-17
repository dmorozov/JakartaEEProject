package com.example.jakartaee.repository;

import com.example.jakartaee.entity.Account;
import jakarta.annotation.Resource;
import jakarta.enterprise.context.ApplicationScoped;
import javax.sql.DataSource;
import java.sql.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

/**
 * JDBC-based repository for Account entity (alternative to JPA)
 * This implementation uses direct JDBC similar to Spring's JdbcTemplate
 */
@ApplicationScoped
public class AccountRepository {

    @Resource(lookup = "java:comp/env/jdbc/SampleDS")
    private DataSource dataSource;

    public Account save(Account account) throws SQLException {
        if (account.getId() == null) {
            return insert(account);
        } else {
            return update(account);
        }
    }

    private Account insert(Account account) throws SQLException {
        String sql = "INSERT INTO accounts (name, email, created_date) VALUES (?, ?, ?) RETURNING id";

        try (Connection conn = dataSource.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setString(1, account.getName());
            stmt.setString(2, account.getEmail());
            stmt.setTimestamp(3, Timestamp.valueOf(LocalDateTime.now()));

            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    account.setId(rs.getLong("id"));
                }
            }
        }

        return account;
    }

    private Account update(Account account) throws SQLException {
        String sql = "UPDATE accounts SET name = ?, email = ? WHERE id = ?";

        try (Connection conn = dataSource.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setString(1, account.getName());
            stmt.setString(2, account.getEmail());
            stmt.setLong(3, account.getId());

            stmt.executeUpdate();
        }

        return account;
    }

    public Optional<Account> findById(Long id) throws SQLException {
        String sql = "SELECT id, name, email, created_date FROM accounts WHERE id = ?";

        try (Connection conn = dataSource.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setLong(1, id);

            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    return Optional.of(mapResultSetToAccount(rs));
                }
            }
        }

        return Optional.empty();
    }

    public List<Account> findAll() throws SQLException {
        String sql = "SELECT id, name, email, created_date FROM accounts ORDER BY name";
        List<Account> accounts = new ArrayList<>();

        try (Connection conn = dataSource.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql);
             ResultSet rs = stmt.executeQuery()) {

            while (rs.next()) {
                accounts.add(mapResultSetToAccount(rs));
            }
        }

        return accounts;
    }

    public Optional<Account> findByEmail(String email) throws SQLException {
        String sql = "SELECT id, name, email, created_date FROM accounts WHERE email = ?";

        try (Connection conn = dataSource.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setString(1, email);

            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    return Optional.of(mapResultSetToAccount(rs));
                }
            }
        }

        return Optional.empty();
    }

    public void delete(Long id) throws SQLException {
        String sql = "DELETE FROM accounts WHERE id = ?";

        try (Connection conn = dataSource.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setLong(1, id);
            stmt.executeUpdate();
        }
    }

    public long count() throws SQLException {
        String sql = "SELECT COUNT(*) FROM accounts";

        try (Connection conn = dataSource.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql);
             ResultSet rs = stmt.executeQuery()) {

            if (rs.next()) {
                return rs.getLong(1);
            }
        }

        return 0;
    }

    private Account mapResultSetToAccount(ResultSet rs) throws SQLException {
        Account account = new Account();
        account.setId(rs.getLong("id"));
        account.setName(rs.getString("name"));
        account.setEmail(rs.getString("email"));

        Timestamp createdTimestamp = rs.getTimestamp("created_date");
        if (createdTimestamp != null) {
            account.setCreatedDate(createdTimestamp.toLocalDateTime());
        }

        return account;
    }
}
