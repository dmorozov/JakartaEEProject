package com.example.jakartaee.action;

import com.example.jakartaee.entity.Address;
import com.example.jakartaee.entity.Contact;
import com.example.jakartaee.service.AddressService;
import com.example.jakartaee.service.ContactService;
import org.apache.struts2.ActionSupport;
import org.apache.struts2.ModelDriven;
import org.apache.struts2.Preparable;
import jakarta.ejb.EJB;
import jakarta.inject.Named;

import java.util.List;

@Named
public class AddressAction extends ActionSupport implements ModelDriven<Address>, Preparable {

    private static final long serialVersionUID = 1L;

    @EJB
    private AddressService addressService;

    @EJB
    private ContactService contactService;

    private Address address = new Address();
    private List<Address> addresses;
    private List<Contact> contacts;
    private Long id;
    private Long contactId;

    @Override
    public final void prepare() {
      // Load contacts for dropdown in form
      try {
        contacts = contactService.findAllContacts();
      } catch (Exception e) {
        addActionError("Error loading contacts: " + e.getMessage());
      }
    }

    public final String list() {
      try {
        if (contactId != null) {
          addresses = addressService.findAddressesByContactId(contactId);
        } else {
          addresses = addressService.findAllAddresses();
        }
        return SUCCESS;
      } catch (Exception e) {
        addActionError("Error retrieving addresses: " + e.getMessage());
        return ERROR;
      }
    }

    public final String view() {
      if (id == null) {
        addActionError("Address ID is required");
        return INPUT;
      }

      try {
        address = addressService.findAddressById(id);
        if (address == null) {
          addActionError("Address not found with ID: " + id);
          return INPUT;
        }
        return SUCCESS;
      } catch (Exception e) {
        addActionError("Error retrieving address: " + e.getMessage());
        return ERROR;
      }
    }

    public final String create() {
      address = new Address();
      return SUCCESS;
    }

    public final String edit() {
      if (id == null) {
        addActionError("Address ID is required");
        return INPUT;
      }

      try {
        address = addressService.findAddressById(id);
        if (address == null) {
          addActionError("Address not found with ID: " + id);
          return INPUT;
        }
        if (address.getContact() != null) {
          contactId = address.getContact().getId();
        }
        return SUCCESS;
      } catch (Exception e) {
        addActionError("Error retrieving address: " + e.getMessage());
        return ERROR;
      }
    }

    public final String save() {
      try {
        // Set contact if contactId is provided
        if (contactId != null) {
          Contact contact = contactService.findContactById(contactId);
          if (contact != null) {
            address.setContact(contact);
          } else {
            addActionError("Invalid contact selected");
            return INPUT;
          }
        }

        if (address.getId() == null) {
          addressService.createAddress(address);
          addActionMessage("Address created successfully!");
        } else {
          addressService.updateAddress(address);
          addActionMessage("Address updated successfully!");
        }
        return SUCCESS;
      } catch (Exception e) {
        addActionError("Error saving address: " + e.getMessage());
        return INPUT;
      }
    }

    public final String delete() {
      if (id == null) {
        addActionError("Address ID is required");
        return ERROR;
      }

      try {
        addressService.deleteAddress(id);
        addActionMessage("Address deleted successfully!");
        return SUCCESS;
      } catch (Exception e) {
        addActionError("Error deleting address: " + e.getMessage());
        return ERROR;
      }
    }

    @Override
    public final Address getModel() {
      return address;
    }

    // Getters and Setters
    public final Address getAddress() {
      return address;
    }

    public final void setAddress(final Address address) {
      this.address = address;
    }

    public final List<Address> getAddresses() {
      return addresses;
    }

    public final void setAddresses(final List<Address> addresses) {
      this.addresses = addresses;
    }

    public final List<Contact> getContacts() {
      return contacts;
    }

    public final void setContacts(final List<Contact> contacts) {
      this.contacts = contacts;
    }

    public final Long getId() {
      return id;
    }

    public final void setId(final Long id) {
      this.id = id;
    }

    public final Long getContactId() {
      return contactId;
    }

    public final void setContactId(final Long contactId) {
        this.contactId = contactId;
    }
}
