package me.grid.email_validator.api;

import com.microsoft.playwright.*;
import com.microsoft.playwright.options.RequestOptions;
import me.grid.email_validator.RequestManager;
import me.grid.email_validator.dataTransferObjects.EmailResponse;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvFileSource;
import org.junit.jupiter.params.provider.MethodSource;
import org.springframework.boot.test.context.SpringBootTest;

import java.io.IOException;
import java.util.*;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.DEFINED_PORT)
public class ApiTests {

    static String baseURI = "http://localhost:8080/validate/";

    Playwright playwright;
    APIRequestContext requestContext;
    RequestManager requestManager;

    @BeforeEach
    public void setUp(){
        playwright = Playwright.create();

        APIRequest request = playwright.request();
        requestContext = request.newContext();

        requestManager = new RequestManager(requestContext);
    }

    @AfterEach
    public void tearDown() {
        playwright.close();
    }

    public void assertBadRequestWithMessage(APIResponse apiResponse, String message){
        assertEquals(400, apiResponse.status());
        assertTrue(apiResponse.text().contains(message));
    }

    @ParameterizedTest
    @CsvFileSource(resources = "/forAPI/invalidEmails.csv")
    public void testWithValidBody_InvalidEmails(String invalidEmail) throws IOException {
        var body = requestManager.createBodyFromList(List.of(invalidEmail));
        var apiResponse = requestManager.sendPostRequestWithBody(body);
        EmailResponse emailResponse = requestManager.getPOJOFromResponse(
                apiResponse
        );

        assertEquals(200, apiResponse.status());
        assertEquals(body.get("emails"), emailResponse.getInvalidEmails());
    }


    @ParameterizedTest
    @CsvFileSource(resources = "/forAPI/validEmails.csv")
    public void testWithValidBody_ValidEmails(String validEmail) throws IOException {
        var body = requestManager.createBodyFromList(List.of(validEmail));
        var apiResponse = requestManager.sendPostRequestWithBody(body);
        EmailResponse emailResponse = requestManager.getPOJOFromResponse(
                apiResponse
        );

        assertEquals(200, apiResponse.status());
        assertEquals(body.get("emails"), emailResponse.getValidEmails());
    }

    @Test
    public void testWithEmptyList() {
        var body = requestManager.createBodyFromList(List.of());
        var apiResponse = requestManager.sendPostRequestWithBody(body);

        assertBadRequestWithMessage(apiResponse, "Empty list in the request body.");
    }

    @Test
    public void testWithInvalidBody() {
        Map<String, List<String>> body = new HashMap<>();
        body.put("e", List.of("aaaa"));
        var apiResponse = requestContext.post(baseURI, RequestOptions.create().setData(body));

        assertBadRequestWithMessage(apiResponse, "Not a valid request body.");
    }

    @Test
    public void testWithoutABody(){
        var apiResponse = requestContext.post(baseURI);

        assertBadRequestWithMessage(apiResponse, "Request body is missing.");
    }

    @ParameterizedTest
    @MethodSource("me.grid.email_validator.dataProviders.EmailTestData#emailListProvider")
    public void verifyingDistributionAndSize(List<String> validArray, List<String> invalidArray) throws IOException {
        List<String> arrayList = new ArrayList<>();
        arrayList.addAll(validArray);
        arrayList.addAll(invalidArray);

        var body = requestManager.createBodyFromList(arrayList);
        var apiResponse = requestManager.sendPostRequestWithBody(body);
        EmailResponse emailResponse = requestManager.getPOJOFromResponse(
                apiResponse
        );

        assertEquals(emailResponse.getValidEmails(), validArray);
        assertEquals(emailResponse.getInvalidEmails(), invalidArray);

        assertEquals(emailResponse.getValidEmails().size(), validArray.size());
        assertEquals(emailResponse.getInvalidEmails().size(), invalidArray.size());

        assertEquals(200, apiResponse.status());
    }

}
