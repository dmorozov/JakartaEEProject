package com.example.jakartaee.service;

import com.example.jakartaee.entity.Contact;
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
public class ContactServiceBean implements ContactService {

    private static final Logger logger = Logger.getLogger(ContactServiceBean.class.getName());

    @PersistenceContext(unitName = "SamplePU")
    private EntityManager entityManager;

    @Override
    @RolesAllowed({"admin", "user"})
    public Contact createContact(Contact contact) {
        logger.info("Creating new contact: " + contact.getFullName());
        entityManager.persist(contact);
        entityManager.flush();
        return contact;
    }

    @Override
    @RolesAllowed({"admin", "user"})
    public Contact updateContact(Contact contact) {
        logger.info("Updating contact with ID: " + contact.getId());
        Contact merged = entityManager.merge(contact);
        entityManager.flush();
        return merged;
    }

    @Override
    @RolesAllowed("admin")
    public void deleteContact(Long id) {
        logger.info("Deleting contact with ID: " + id);
        Contact contact = entityManager.find(Contact.class, id);
        if (contact != null) {
            entityManager.remove(contact);
        }
    }

    @Override
    @PermitAll
    @TransactionAttribute(TransactionAttributeType.SUPPORTS)
    public Contact findContactById(Long id) {
        logger.info("Finding contact by ID: " + id);
        return entityManager.find(Contact.class, id);
    }

    @Override
    @PermitAll
    @TransactionAttribute(TransactionAttributeType.SUPPORTS)
    public List<Contact> findAllContacts() {
        logger.info("Finding all contacts");
        TypedQuery<Contact> query = entityManager.createNamedQuery("Contact.findAll", Contact.class);
        return query.getResultList();
    }

    @Override
    @PermitAll
    @TransactionAttribute(TransactionAttributeType.SUPPORTS)
    public List<Contact> findContactsByAccountId(Long accountId) {
        logger.info("Finding contacts for account ID: " + accountId);
        TypedQuery<Contact> query = entityManager.createNamedQuery("Contact.findByAccount", Contact.class);
        query.setParameter("accountId", accountId);
        return query.getResultList();
    }

    @Override
    @PermitAll
    @TransactionAttribute(TransactionAttributeType.SUPPORTS)
    public long countContacts() {
        logger.info("Counting all contacts");
        TypedQuery<Long> query = entityManager.createQuery("SELECT COUNT(c) FROM Contact c", Long.class);
        return query.getSingleResult();
    }
}
