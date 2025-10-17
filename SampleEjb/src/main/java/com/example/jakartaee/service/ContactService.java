package com.example.jakartaee.service;

import com.example.jakartaee.entity.Contact;
import jakarta.ejb.Local;
import java.util.List;

@Local
public interface ContactService {

    Contact createContact(Contact contact);

    Contact updateContact(Contact contact);

    void deleteContact(Long id);

    Contact findContactById(Long id);

    List<Contact> findAllContacts();

    List<Contact> findContactsByAccountId(Long accountId);

    long countContacts();
}
