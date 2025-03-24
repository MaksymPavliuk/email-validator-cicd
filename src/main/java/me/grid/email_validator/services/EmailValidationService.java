package me.grid.email_validator.services;

import me.grid.email_validator.dataTransferObjects.EmailRequest;
import me.grid.email_validator.dataTransferObjects.EmailResponse;
import org.springframework.stereotype.Service;

import java.util.function.Predicate;

@Service
public class EmailValidationService {

    public EmailResponse groupEmails(EmailRequest emailRequest){
        verifyIfInputIsValid(emailRequest);

        EmailResponse emailResponse = new EmailResponse();
        Predicate<String> matchingRegex = a -> a.matches("^[a-zA-Z0-9._%+-]+@[a-zA-Z0-9.-]+\\.[a-zA-Z]{2,}$");

        emailRequest.getEmails().stream()
                .filter(matchingRegex)
                .forEach(emailResponse::addValid);

        emailRequest.getEmails().stream()
                .filter(matchingRegex.negate())
                .forEach(emailResponse::addInvalid);

        return emailResponse;
    }

    public void verifyIfInputIsValid(EmailRequest emailRequest){
        if (emailRequest.getEmails() == null) {
            throw new IllegalArgumentException("Not a valid request body. Try {\"emails\":[...]}");
        }
    }

}
