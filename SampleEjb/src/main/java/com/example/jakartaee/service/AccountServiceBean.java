package com.example.jakartaee.service;

import com.example.jakartaee.entity.Account;
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
//@DeclareRoles({"admin", "user"})
@TransactionAttribute(TransactionAttributeType.REQUIRED)
public class AccountServiceBean implements AccountService {

    private static final Logger logger = Logger.getLogger(AccountServiceBean.class.getName());

    @PersistenceContext(unitName = "SamplePU")
    private EntityManager entityManager;

    @Override
    //@RolesAllowed({"admin", "user"})
    public Account createAccount(Account account) {
        logger.info("Creating new account: " + account.getName());
        entityManager.persist(account);
        entityManager.flush();
        return account;
    }

    @Override
    //@RolesAllowed({"admin", "user"})
    public Account updateAccount(Account account) {
        logger.info("Updating account with ID: " + account.getId());
        Account merged = entityManager.merge(account);
        entityManager.flush();
        return merged;
    }

    @Override
    //@RolesAllowed("admin")
    public void deleteAccount(Long id) {
        logger.info("Deleting account with ID: " + id);
        Account account = entityManager.find(Account.class, id);
        if (account != null) {
            entityManager.remove(account);
        }
    }

    @Override
    //@PermitAll
    @TransactionAttribute(TransactionAttributeType.SUPPORTS)
    public Account findAccountById(Long id) {
        logger.info("Finding account by ID: " + id);
        return entityManager.find(Account.class, id);
    }

    @Override
    //@PermitAll
    @TransactionAttribute(TransactionAttributeType.SUPPORTS)
    public Account findAccountByEmail(String email) {
        logger.info("Finding account by email: " + email);
        TypedQuery<Account> query = entityManager.createNamedQuery("Account.findByEmail", Account.class);
        query.setParameter("email", email);
        List<Account> results = query.getResultList();
        return results.isEmpty() ? null : results.get(0);
    }

    @Override
    //@PermitAll
    @TransactionAttribute(TransactionAttributeType.SUPPORTS)
    public List<Account> findAllAccounts() {
        logger.info("Finding all accounts");
        TypedQuery<Account> query = entityManager.createNamedQuery("Account.findAll", Account.class);
        return query.getResultList();
    }

    @Override
    //@PermitAll
    @TransactionAttribute(TransactionAttributeType.SUPPORTS)
    public long countAccounts() {
        logger.info("Counting all accounts");
        TypedQuery<Long> query = entityManager.createQuery("SELECT COUNT(a) FROM Account a", Long.class);
        return query.getSingleResult();
    }
}
