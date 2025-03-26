package me.grid.email_validator.services;

import me.grid.email_validator.dataTransferObjects.EmailRequest;
import me.grid.email_validator.dataTransferObjects.EmailResponse;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvFileSource;
import org.junit.jupiter.params.provider.CsvSource;
import org.junit.jupiter.params.provider.MethodSource;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class EmailValidationServiceTest {

    public EmailValidationService emailValidationService;

    @BeforeEach
    public void setUp(){
        emailValidationService = new EmailValidationService();
    }

    // no need to parametrize (only one input applicable)
    @Test
    public void withInvalidRequestBody(){
        RuntimeException thrown = assertThrows(
                RuntimeException.class,
                () -> emailValidationService.groupEmails(new EmailRequest(null)),
                "Was expected to throw exception but didn't"
        );

        assertTrue(thrown.getMessage().contains("Not a valid request body"));
    }

    @ParameterizedTest
    @MethodSource("me.grid.email_validator.dataProviders.EmailTestData#emailListProvider")
    public void verifyingDistributionAndSize(List<String> validArray, List<String> invalidArray){
        List<String> arrayList = new ArrayList<>();
        arrayList.addAll(validArray);
        arrayList.addAll(invalidArray);

        EmailResponse emailResponse = emailValidationService.groupEmails(new EmailRequest(arrayList));

        assertEquals(emailResponse.getValidEmails(), validArray);
        assertEquals(emailResponse.getInvalidEmails(), invalidArray);

        assertEquals(emailResponse.getValidEmails().size(), validArray.size());
        assertEquals(emailResponse.getInvalidEmails().size(), invalidArray.size());
    }

    @ParameterizedTest
    @CsvFileSource(resources = "/specialCharacters.csv")
    public void withSpecialCharacters(String validEmail, String invalidEmail){
        List<String> arrayList = List.of(
                validEmail,
                invalidEmail
        );
        EmailResponse emailResponse = emailValidationService.groupEmails(new EmailRequest(arrayList));

        assertTrue(emailResponse.getValidEmails().contains(arrayList.get(0)));
        assertTrue(emailResponse.getInvalidEmails().contains(arrayList.get(1)));
    }

    @ParameterizedTest
    @CsvFileSource(resources = "/missingParts.csv")
    public void withMissingParts(String invalidEmail){
        List<String> arrayList = List.of(
                invalidEmail
        );
        EmailResponse emailResponse = emailValidationService.groupEmails(new EmailRequest(arrayList));

        assertTrue(emailResponse.getInvalidEmails().contains(arrayList.get(0)));
    }

    @ParameterizedTest
    @CsvFileSource(resources = "/subdomains.csv")
    public void withSubdomains(String validEmail){
        List<String> arrayList = List.of(
                validEmail
        );
        EmailResponse emailResponse = emailValidationService.groupEmails(new EmailRequest(arrayList));

        assertTrue(emailResponse.getValidEmails().contains(arrayList.get(0)));
    }

    @ParameterizedTest
    @CsvFileSource(resources = "/unicode.csv")
    public void withUnicode(String validEmail){
        List<String> arrayList = List.of(
                validEmail
        );
        EmailResponse emailResponse = emailValidationService.groupEmails(new EmailRequest(arrayList));

        assertTrue(emailResponse.getValidEmails().contains(arrayList.get(0)));
    }

    // no need to parametrize (the range is too small)
    @Test
    public void withShortDomains(){
        ArrayList<String> arrayList = new ArrayList<>(List.of(
                "a@gmail.ua",
                "validMainPart@gmail.c"
        ));
        EmailResponse emailResponse = emailValidationService.groupEmails(new EmailRequest(arrayList));

        assertTrue(emailResponse.getValidEmails().contains(arrayList.get(0)));
        assertTrue(emailResponse.getInvalidEmails().contains(arrayList.get(1)));
    }

    @ParameterizedTest
    @CsvFileSource(resources = "/withWhiteSpaces.csv")
    public void withWhiteSpaces(String invalidEmail){
        List<String> arrayList = List.of(
                invalidEmail
        );

        RuntimeException thrown = assertThrows(
                RuntimeException.class,
                () -> emailValidationService.groupEmails(new EmailRequest(arrayList)),
                "Was expected to throw exception but didn't"
        );

        assertTrue(thrown.getMessage().contains("has whitespaces"));
    }

    @ParameterizedTest
    @CsvFileSource(resources = "/withControlCharacters.csv")
    public void withControlCharacters(String invalidEmail){
        List<String> arrayList = List.of(
                invalidEmail
        );

        invalidEmail.chars().forEach(ch -> System.out.println("ASCII: " + ch + " -> " + (char) ch));

        RuntimeException thrown = assertThrows(
                RuntimeException.class,
                () -> emailValidationService.groupEmails(new EmailRequest(arrayList)),
                "Was expected to throw exception but didn't"
        );

        assertTrue(thrown.getMessage().contains("has control characters"));
    }

    // no need to parametrize (only one input applicable)
    @Test
    public void withLongEmails(){
        List<String> arrayList = List.of(
                "aaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaa" +
                        "aaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaa"
        );

        RuntimeException thrown = assertThrows(
                RuntimeException.class,
                () -> emailValidationService.groupEmails(new EmailRequest(arrayList)),
                "Was expected to throw exception but didn't"
        );

        assertTrue(thrown.getMessage().contains("is too long"));
    }

}