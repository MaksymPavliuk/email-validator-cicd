package me.grid.email_validator.controllers;

import me.grid.email_validator.dataTransferObjects.EmailRequest;
import me.grid.email_validator.dataTransferObjects.EmailResponse;
import me.grid.email_validator.services.EmailValidationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RequestMapping("/validate/")
@RestController
public class EmailValidationController {

    private final EmailValidationService emailValidationService;

    @Autowired
    public EmailValidationController(EmailValidationService emailValidationService){
        this.emailValidationService = emailValidationService;
    }

    @PostMapping
    public EmailResponse getGroupedEmails(@RequestBody EmailRequest emailRequest){
        return emailValidationService.groupEmails(emailRequest);
    }

}
