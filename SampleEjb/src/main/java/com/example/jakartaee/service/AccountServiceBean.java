package com.example.jakartaee.service;

import com.example.jakartaee.entity.Account;
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

  private static final Logger LOG = Logger.getLogger(AccountServiceBean.class.getName());

    @PersistenceContext(unitName = "SamplePU")
    private EntityManager entityManager;


    // @RolesAllowed({"admin", "user"})
    @Override
    public final Account createAccount(final Account account) {
      LOG.info("Creating new account: " + account.getName());
      entityManager.persist(account);
      entityManager.flush();
      return account;
    }


    // @RolesAllowed({"admin", "user"})
    @Override
    public final Account updateAccount(final Account account) {
      LOG.info("Updating account with ID: " + account.getId());
      Account merged = entityManager.merge(account);
      entityManager.flush();
      return merged;
    }


    // @RolesAllowed("admin")
    @Override
    public final void deleteAccount(final Long id) {
      LOG.info("Deleting account with ID: " + id);
      Account account = entityManager.find(Account.class, id);
      if (account != null) {
        entityManager.remove(account);
      }
    }

    @Override
    // @PermitAll
    @TransactionAttribute(TransactionAttributeType.SUPPORTS)
    public final Account findAccountById(final Long id) {
      LOG.info("Finding account by ID: " + id);
      return entityManager.find(Account.class, id);
    }

    @Override
    // @PermitAll
    @TransactionAttribute(TransactionAttributeType.SUPPORTS)
    public final Account findAccountByEmail(final String email) {
      LOG.info("Finding account by email: " + email);
      TypedQuery<Account> query =
          entityManager.createNamedQuery("Account.findByEmail", Account.class);
      query.setParameter("email", email);
      List<Account> results = query.getResultList();
      return results.isEmpty() ? null : results.get(0);
    }

    @Override
    // @PermitAll
    @TransactionAttribute(TransactionAttributeType.SUPPORTS)
    public final List<Account> findAllAccounts() {
      LOG.info("Finding all accounts");
      TypedQuery<Account> query = entityManager.createNamedQuery("Account.findAll", Account.class);
      return query.getResultList();
    }

    @Override
    // @PermitAll
    @TransactionAttribute(TransactionAttributeType.SUPPORTS)
    public final long countAccounts() {
      LOG.info("Counting all accounts");
        TypedQuery<Long> query = entityManager.createQuery("SELECT COUNT(a) FROM Account a", Long.class);
        return query.getSingleResult();
    }
}
