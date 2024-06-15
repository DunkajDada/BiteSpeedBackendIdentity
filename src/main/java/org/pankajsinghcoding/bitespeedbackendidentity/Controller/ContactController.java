package com.example.bitespeed.Controller;

import com.example.bitespeed.Entity.Contact;
import com.example.bitespeed.Service.ContactService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/identify")
public class ContactController {

    @Autowired
    private ContactService contactService;

    @PostMapping
    public ResponseEntity<Map<String, Object>> identify(@RequestBody Map<String, Object> payload) {
        String email = (String) payload.get("email");
        String phoneNumber = (String) payload.get("phoneNumber");

        Contact savedContact = contactService.identifyContact(email, phoneNumber);

        List<String> emails = new ArrayList<>();
        List<String> phoneNumbers = new ArrayList<>();
        List<Long> secondaryContactIds = new ArrayList<>();

        emails.add(savedContact.getEmail());
        phoneNumbers.add(savedContact.getPhoneNumber());

        List<Contact> secondaryContacts = contactService.findSecondaryContacts(phoneNumber, email);
        secondaryContacts.stream()
                .filter(contact -> contact.getId() != savedContact.getId())
                .forEach(contact -> {
                    emails.add(contact.getEmail());
                    phoneNumbers.add(contact.getPhoneNumber());
                    secondaryContactIds.add(contact.getId());
                });

        Map<String, Object> response = new HashMap<>();
        Map<String, Object> contactMap = new HashMap<>();
        contactMap.put("primaryContactId", savedContact.getId());
        contactMap.put("emails", emails);
        contactMap.put("phoneNumbers", phoneNumbers);
        contactMap.put("secondaryContactIds", secondaryContactIds);
        response.put("contact", contactMap);

        return ResponseEntity.ok(response);
    }
}

