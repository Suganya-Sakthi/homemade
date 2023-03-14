package org.perscholas.homemade.services;

import org.perscholas.homemade.models.EmailDetails;

public interface EmailService {
    // Method
    // To send a simple email
    String sendSimpleMail(EmailDetails details);
}
