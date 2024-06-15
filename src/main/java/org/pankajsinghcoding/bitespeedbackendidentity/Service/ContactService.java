package org.pankajsinghcoding.bitespeedbackendidentity.Service;


import org.pankajsinghcoding.bitespeedbackendidentity.Entity.Contact;
import org.pankajsinghcoding.bitespeedbackendidentity.Enum.LinkPrecedence;
import org.pankajsinghcoding.bitespeedbackendidentity.Repository.ContactRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class ContactService {

    @Autowired
    private ContactRepository contactRepository;

    public Contact identifyContact(String email, String phoneNumber) {
        Optional<Contact> existingContactOpt = contactRepository.findFirstByPhoneNumberOrEmailOrderByCreatedAtAsc(phoneNumber, email);

        Contact newContact;
        if (existingContactOpt.isPresent()) {
            Contact existingContact = existingContactOpt.get();
            newContact = new Contact();
            newContact.setPhoneNumber(existingContact.getPhoneNumber());
            newContact.setEmail(existingContact.getEmail());
            newContact.setLinkPrecedence(LinkPrecedence.SECONDARY);
            newContact.setLinkedId(existingContact.getId());
        } else {
            newContact = new Contact();
            newContact.setPhoneNumber(phoneNumber);
            newContact.setEmail(email);
            newContact.setLinkPrecedence(LinkPrecedence.PRIMARY);
        }

        return contactRepository.save(newContact);
    }

    public List<Contact> findSecondaryContacts(String phoneNumber, String email) {
        return contactRepository.findByPhoneNumberOrEmail(phoneNumber, email).stream()
                .filter(contact -> contact.getLinkPrecedence() == LinkPrecedence.SECONDARY)
                .collect(Collectors.toList());
    }
}

