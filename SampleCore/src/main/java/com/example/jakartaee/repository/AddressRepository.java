package com.example.jakartaee.repository;

import com.example.jakartaee.EntityConstants;
import com.example.jakartaee.entity.Address;
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
 * JDBC-based repository for Address entity (alternative to JPA).
 */
@ApplicationScoped
public class AddressRepository {

    @Resource(lookup = "java:comp/env/jdbc/SampleDS")
    private DataSource dataSource;

    public final Address save(final Address address) throws SQLException {
      if (address.getId() == null) {
        return insert(address);
      } else {
        return update(address);
      }
    }

    private Address insert(final Address address) throws SQLException {
      String sql = "INSERT INTO addresses (street, city, state, zip_code, country, contact_id) "
          + "VALUES (?, ?, ?, ?, ?, ?) RETURNING id";

      try (Connection conn = dataSource.getConnection();
          PreparedStatement stmt = conn.prepareStatement(sql)) {

        stmt.setString(EntityConstants.INDEX_1, address.getStreet());
        stmt.setString(EntityConstants.INDEX_2, address.getCity());
        stmt.setString(EntityConstants.INDEX_3, address.getState());
        stmt.setString(EntityConstants.INDEX_4, address.getZipCode());
        stmt.setString(EntityConstants.INDEX_5, address.getCountry());
        stmt.setLong(EntityConstants.INDEX_6, address.getContact().getId());

        try (ResultSet rs = stmt.executeQuery()) {
          if (rs.next()) {
            address.setId(rs.getLong("id"));
          }
        }
      }

      return address;
    }

    private Address update(final Address address) throws SQLException {
      String sql = "UPDATE addresses SET street = ?, city = ?, state = ?, zip_code = ?, "
          + "country = ?, contact_id = ? WHERE id = ?";

      try (Connection conn = dataSource.getConnection();
          PreparedStatement stmt = conn.prepareStatement(sql)) {

        stmt.setString(EntityConstants.INDEX_1, address.getStreet());
        stmt.setString(EntityConstants.INDEX_2, address.getCity());
        stmt.setString(EntityConstants.INDEX_3, address.getState());
        stmt.setString(EntityConstants.INDEX_4, address.getZipCode());
        stmt.setString(EntityConstants.INDEX_5, address.getCountry());
        stmt.setLong(EntityConstants.INDEX_6, address.getContact().getId());
        stmt.setLong(EntityConstants.INDEX_7, address.getId());

        stmt.executeUpdate();
      }

      return address;
    }

    public final Optional<Address> findById(final Long id) throws SQLException {
      String sql =
          "SELECT id, street, city, state, zip_code, country, contact_id FROM addresses WHERE id = ?";

      try (Connection conn = dataSource.getConnection();
          PreparedStatement stmt = conn.prepareStatement(sql)) {

        stmt.setLong(1, id);

        try (ResultSet rs = stmt.executeQuery()) {
          if (rs.next()) {
            return Optional.of(mapResultSetToAddress(rs));
          }
        }
      }

      return Optional.empty();
    }

    public final List<Address> findAll() throws SQLException {
      String sql = "SELECT id, street, city, state, zip_code, country, contact_id FROM addresses "
          + "ORDER BY city, state";
      List<Address> addresses = new ArrayList<>();

      try (Connection conn = dataSource.getConnection();
          PreparedStatement stmt = conn.prepareStatement(sql);
          ResultSet rs = stmt.executeQuery()) {

        while (rs.next()) {
          addresses.add(mapResultSetToAddress(rs));
        }
      }

      return addresses;
    }

    public final List<Address> findByContactId(final Long contactId) throws SQLException {
      String sql = "SELECT id, street, city, state, zip_code, country, contact_id FROM addresses "
          + "WHERE contact_id = ?";
      List<Address> addresses = new ArrayList<>();

      try (Connection conn = dataSource.getConnection();
          PreparedStatement stmt = conn.prepareStatement(sql)) {

        stmt.setLong(1, contactId);

        try (ResultSet rs = stmt.executeQuery()) {
          while (rs.next()) {
            addresses.add(mapResultSetToAddress(rs));
          }
        }
      }

      return addresses;
    }

    public final List<Address> findByCity(final String city) throws SQLException {
      String sql = "SELECT id, street, city, state, zip_code, country, contact_id FROM addresses "
          + "WHERE city = ?";
      List<Address> addresses = new ArrayList<>();

      try (Connection conn = dataSource.getConnection();
          PreparedStatement stmt = conn.prepareStatement(sql)) {

        stmt.setString(1, city);

        try (ResultSet rs = stmt.executeQuery()) {
          while (rs.next()) {
            addresses.add(mapResultSetToAddress(rs));
          }
        }
      }

      return addresses;
    }

    public final void delete(final Long id) throws SQLException {
      String sql = "DELETE FROM addresses WHERE id = ?";

      try (Connection conn = dataSource.getConnection();
          PreparedStatement stmt = conn.prepareStatement(sql)) {

        stmt.setLong(1, id);
        stmt.executeUpdate();
      }
    }

    public final long count() throws SQLException {
      String sql = "SELECT COUNT(*) FROM addresses";

      try (Connection conn = dataSource.getConnection();
          PreparedStatement stmt = conn.prepareStatement(sql);
          ResultSet rs = stmt.executeQuery()) {

        if (rs.next()) {
          return rs.getLong(1);
        }
      }

      return 0;
    }

    private Address mapResultSetToAddress(final ResultSet rs) throws SQLException {
        Address address = new Address();
        address.setId(rs.getLong("id"));
        address.setStreet(rs.getString("street"));
        address.setCity(rs.getString("city"));
        address.setState(rs.getString("state"));
        address.setZipCode(rs.getString("zip_code"));
        address.setCountry(rs.getString("country"));
        // Note: Contact object not fully hydrated to avoid circular dependencies
        return address;
    }
}
