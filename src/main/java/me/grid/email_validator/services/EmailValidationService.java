package me.grid.email_validator.services;

import me.grid.email_validator.dataTransferObjects.EmailRequest;
import me.grid.email_validator.dataTransferObjects.EmailResponse;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;
import java.util.function.Predicate;
import java.util.stream.Collectors;

@Service
public class EmailValidationService {

    public EmailResponse groupEmails(EmailRequest emailRequest){
        verifyIfInputIsValid(emailRequest);

        Predicate<String> matchingRegex =
                a -> a.matches("^[\\p{L}\\p{N}._%+-]+@[\\p{L}\\p{N}.-]+\\.\\p{L}{2,}$");

        Map<Boolean, List<String>> partitionedEmails = emailRequest.getEmails().stream()
                .collect(Collectors.partitioningBy(matchingRegex));

        return new EmailResponse(partitionedEmails.get(true), partitionedEmails.get(false));
    }

    public void verifyIfInputIsValid(EmailRequest emailRequest){
        if (emailRequest.getEmails() == null) {
            throw new IllegalArgumentException("Not a valid request body. Try {\"emails\":[...]}");
        }
        if (emailRequest.getEmails().isEmpty()) {
            throw new IllegalArgumentException("Empty list in the request body. Try {\"emails\":[\"email1\"]}");
        }
        emailRequest.getEmails()
                .forEach(this::checkLengthAndWhitespaces);
    }

    public void checkLengthAndWhitespaces(String email) {
        if (email.length() > 100){
            throw new IllegalArgumentException("Email: " + email + " \nis too long. Maximum is 100 characters, but this one is: " + email.length());
        }
        if (email.contains(" ")) {
            throw new IllegalArgumentException("Email: " + email + " \nhas whitespaces. Please remove them.");
        }
        if (email.chars().anyMatch(Character::isISOControl)) {
            throw new IllegalArgumentException("Email: " + email + " \nhas control characters. Please remove them.");
        }
    }

}
