package com.example.jakartaee.repository;

import com.example.jakartaee.entity.Address;
import jakarta.annotation.Resource;
import jakarta.enterprise.context.ApplicationScoped;
import javax.sql.DataSource;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

/**
 * JDBC-based repository for Address entity (alternative to JPA)
 */
@ApplicationScoped
public class AddressRepository {

    @Resource(lookup = "java:comp/env/jdbc/SampleDS")
    private DataSource dataSource;

    public Address save(Address address) throws SQLException {
        if (address.getId() == null) {
            return insert(address);
        } else {
            return update(address);
        }
    }

    private Address insert(Address address) throws SQLException {
        String sql = "INSERT INTO addresses (street, city, state, zip_code, country, contact_id) " +
                     "VALUES (?, ?, ?, ?, ?, ?) RETURNING id";

        try (Connection conn = dataSource.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setString(1, address.getStreet());
            stmt.setString(2, address.getCity());
            stmt.setString(3, address.getState());
            stmt.setString(4, address.getZipCode());
            stmt.setString(5, address.getCountry());
            stmt.setLong(6, address.getContact().getId());

            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    address.setId(rs.getLong("id"));
                }
            }
        }

        return address;
    }

    private Address update(Address address) throws SQLException {
        String sql = "UPDATE addresses SET street = ?, city = ?, state = ?, zip_code = ?, " +
                     "country = ?, contact_id = ? WHERE id = ?";

        try (Connection conn = dataSource.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setString(1, address.getStreet());
            stmt.setString(2, address.getCity());
            stmt.setString(3, address.getState());
            stmt.setString(4, address.getZipCode());
            stmt.setString(5, address.getCountry());
            stmt.setLong(6, address.getContact().getId());
            stmt.setLong(7, address.getId());

            stmt.executeUpdate();
        }

        return address;
    }

    public Optional<Address> findById(Long id) throws SQLException {
        String sql = "SELECT id, street, city, state, zip_code, country, contact_id FROM addresses WHERE id = ?";

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

    public List<Address> findAll() throws SQLException {
        String sql = "SELECT id, street, city, state, zip_code, country, contact_id FROM addresses " +
                     "ORDER BY city, state";
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

    public List<Address> findByContactId(Long contactId) throws SQLException {
        String sql = "SELECT id, street, city, state, zip_code, country, contact_id FROM addresses " +
                     "WHERE contact_id = ?";
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

    public List<Address> findByCity(String city) throws SQLException {
        String sql = "SELECT id, street, city, state, zip_code, country, contact_id FROM addresses " +
                     "WHERE city = ?";
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

    public void delete(Long id) throws SQLException {
        String sql = "DELETE FROM addresses WHERE id = ?";

        try (Connection conn = dataSource.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setLong(1, id);
            stmt.executeUpdate();
        }
    }

    public long count() throws SQLException {
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

    private Address mapResultSetToAddress(ResultSet rs) throws SQLException {
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
