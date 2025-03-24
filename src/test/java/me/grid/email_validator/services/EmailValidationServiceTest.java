package me.grid.email_validator.services;

import me.grid.email_validator.dataTransferObjects.EmailRequest;
import me.grid.email_validator.dataTransferObjects.EmailResponse;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class EmailValidationServiceTest {

    public EmailValidationService emailValidationService;

    @BeforeEach
    public void setUp(){
        emailValidationService = new EmailValidationService();
    }

    @Test
    public void withInvalidRequestBody(){
        RuntimeException thrown = assertThrows(
                RuntimeException.class,
                () -> emailValidationService.groupEmails(new EmailRequest(null)),
                "Was expected to throw exception but didn't"
        );

        assertTrue(thrown.getMessage().contains("Not a valid request body"));
    }

    @Test
    public void withSpecialCharacters(){
        ArrayList<String> arrayList = new ArrayList<>(List.of(
                "validemail+_+_+_+_+.......@gmail.com",
                "invalidEmail####>>>>@gmail.com"
        ));
        EmailResponse emailResponse = emailValidationService.groupEmails(new EmailRequest(arrayList));

        System.out.println(emailResponse.getValidEmails().toString() + " " + emailResponse.getInvalidEmails().toString());
        assertTrue(emailResponse.getValidEmails().contains(arrayList.getFirst()));
        assertTrue(emailResponse.getInvalidEmails().contains(arrayList.get(1)));
    }

    @Test
    public void withShortDomains(){
        ArrayList<String> arrayList = new ArrayList<>(List.of(
                "a@gmail.ua",
                "validMainPart@gmail.c"
        ));
        EmailResponse emailResponse = emailValidationService.groupEmails(new EmailRequest(arrayList));

        System.out.println(emailResponse.getValidEmails().toString() + " " + emailResponse.getInvalidEmails().toString());
        assertTrue(emailResponse.getValidEmails().contains(arrayList.getFirst()));
        assertTrue(emailResponse.getInvalidEmails().contains(arrayList.get(1)));
    }

    @Test
    public void withEmptyString(){
        ArrayList<String> arrayList = new ArrayList<>(List.of(
                ""
        ));
        EmailResponse emailResponse = emailValidationService.groupEmails(new EmailRequest(arrayList));

        System.out.println(emailResponse.getValidEmails() + " " + emailResponse.getInvalidEmails());
        assertTrue(emailResponse.getInvalidEmails().contains(arrayList.getFirst()));
    }

}