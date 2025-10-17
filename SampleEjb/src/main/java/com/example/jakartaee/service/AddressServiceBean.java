package com.example.jakartaee.service;

import com.example.jakartaee.entity.Address;
import jakarta.annotation.security.DeclareRoles;
import jakarta.annotation.security.PermitAll;
import jakarta.annotation.security.RolesAllowed;
import jakarta.ejb.Stateless;
import jakarta.ejb.TransactionAttribute;
import jakarta.ejb.TransactionAttributeType;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.TypedQuery;
import java.util.List;
import java.util.logging.Logger;

@Stateless
@DeclareRoles({"admin", "user"})
@TransactionAttribute(TransactionAttributeType.REQUIRED)
public class AddressServiceBean implements AddressService {

    private static final Logger logger = Logger.getLogger(AddressServiceBean.class.getName());

    @PersistenceContext(unitName = "SamplePU")
    private EntityManager entityManager;

    @Override
    @RolesAllowed({"admin", "user"})
    public Address createAddress(Address address) {
        logger.info("Creating new address in city: " + address.getCity());
        entityManager.persist(address);
        entityManager.flush();
        return address;
    }

    @Override
    @RolesAllowed({"admin", "user"})
    public Address updateAddress(Address address) {
        logger.info("Updating address with ID: " + address.getId());
        Address merged = entityManager.merge(address);
        entityManager.flush();
        return merged;
    }

    @Override
    @RolesAllowed("admin")
    public void deleteAddress(Long id) {
        logger.info("Deleting address with ID: " + id);
        Address address = entityManager.find(Address.class, id);
        if (address != null) {
            entityManager.remove(address);
        }
    }

    @Override
    @PermitAll
    @TransactionAttribute(TransactionAttributeType.SUPPORTS)
    public Address findAddressById(Long id) {
        logger.info("Finding address by ID: " + id);
        return entityManager.find(Address.class, id);
    }

    @Override
    @PermitAll
    @TransactionAttribute(TransactionAttributeType.SUPPORTS)
    public List<Address> findAllAddresses() {
        logger.info("Finding all addresses");
        TypedQuery<Address> query = entityManager.createNamedQuery("Address.findAll", Address.class);
        return query.getResultList();
    }

    @Override
    @PermitAll
    @TransactionAttribute(TransactionAttributeType.SUPPORTS)
    public List<Address> findAddressesByContactId(Long contactId) {
        logger.info("Finding addresses for contact ID: " + contactId);
        TypedQuery<Address> query = entityManager.createNamedQuery("Address.findByContact", Address.class);
        query.setParameter("contactId", contactId);
        return query.getResultList();
    }

    @Override
    @PermitAll
    @TransactionAttribute(TransactionAttributeType.SUPPORTS)
    public long countAddresses() {
        logger.info("Counting all addresses");
        TypedQuery<Long> query = entityManager.createQuery("SELECT COUNT(a) FROM Address a", Long.class);
        return query.getSingleResult();
    }
}
