package me.grid.email_validator.controllers;

import me.grid.email_validator.dataTransferObjects.EmailRequest;
import me.grid.email_validator.dataTransferObjects.EmailResponse;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

@SpringBootTest
public class EmailValidationControllerTests {

    @Autowired
    public EmailValidationController emailValidationController;

    @Test
    public void withInvalidRequestBody(){
        RuntimeException thrown = assertThrows(
                RuntimeException.class,
                () -> emailValidationController.getGroupedEmails(new EmailRequest(null)),
                "Was expected to throw exception but didn't"
        );

        assertTrue(thrown.getMessage().contains("Not a valid request body"));
    }

    @Test
    public void withValidRequestBody(){
        List<String> list = List.of(
                "validemail+_+_+_+_+.......@gmail.com",
                "invalidEmail####>>>>@gmail.com"
        );
        EmailResponse emailResponse = emailValidationController.getGroupedEmails(new EmailRequest(list));

        System.out.println(emailResponse.getValidEmails().toString() + " " + emailResponse.getInvalidEmails().toString());
        assertTrue(emailResponse.getValidEmails().contains(list.get(0)));
        assertTrue(emailResponse.getInvalidEmails().contains(list.get(1)));
    }

}
