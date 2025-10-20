package com.example.jakartaee.repository;

import com.example.jakartaee.EntityConstants;
import com.example.jakartaee.entity.Account;
import jakarta.annotation.Resource;
import jakarta.enterprise.context.ApplicationScoped;
import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

/**
 * JDBC-based repository for Account entity (alternative to JPA) This implementation uses direct
 * JDBC similar to Spring's JdbcTemplate.
 */
@ApplicationScoped
public class AccountRepository {

    @Resource(lookup = "java:comp/env/jdbc/SampleDS")
    private DataSource dataSource;

    public final Account save(final Account account) throws SQLException {
      if (account.getId() == null) {
        return insert(account);
      } else {
        return update(account);
      }
    }

    private Account insert(final Account account) throws SQLException {
      String sql = "INSERT INTO accounts (name, email, created_date) VALUES (?, ?, ?) RETURNING id";

      try (Connection conn = dataSource.getConnection();
          PreparedStatement stmt = conn.prepareStatement(sql)) {

        stmt.setString(EntityConstants.INDEX_1, account.getName());
        stmt.setString(EntityConstants.INDEX_2, account.getEmail());
        stmt.setTimestamp(EntityConstants.INDEX_3, Timestamp.valueOf(LocalDateTime.now()));

        try (ResultSet rs = stmt.executeQuery()) {
          if (rs.next()) {
            account.setId(rs.getLong("id"));
          }
        }
      }

      return account;
    }

    private Account update(final Account account) throws SQLException {
      String sql = "UPDATE accounts SET name = ?, email = ? WHERE id = ?";

      try (Connection conn = dataSource.getConnection();
          PreparedStatement stmt = conn.prepareStatement(sql)) {

        stmt.setString(EntityConstants.INDEX_1, account.getName());
        stmt.setString(EntityConstants.INDEX_2, account.getEmail());
        stmt.setLong(EntityConstants.INDEX_3, account.getId());

        stmt.executeUpdate();
      }

      return account;
    }

    public final Optional<Account> findById(final Long id) throws SQLException {
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

    public final List<Account> findAll() throws SQLException {
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

    public final Optional<Account> findByEmail(final String email) throws SQLException {
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

    public final void delete(final Long id) throws SQLException {
      String sql = "DELETE FROM accounts WHERE id = ?";

      try (Connection conn = dataSource.getConnection();
          PreparedStatement stmt = conn.prepareStatement(sql)) {

        stmt.setLong(1, id);
        stmt.executeUpdate();
      }
    }

    public final long count() throws SQLException {
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

    private Account mapResultSetToAccount(final ResultSet rs) throws SQLException {
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
