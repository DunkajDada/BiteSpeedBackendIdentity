package org.pankajsinghcoding.bitespeedbackendidentity.Repository;

import org.pankajsinghcoding.bitespeedbackendidentity.Entity.Contact;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface ContactRepository extends JpaRepository<Contact, Long> {

    List<Contact> findByPhoneNumberOrEmail(String phoneNumber, String email);

    Optional<Contact> findFirstByPhoneNumberOrEmailOrderByCreatedAtAsc(String phoneNumber, String email);
}
