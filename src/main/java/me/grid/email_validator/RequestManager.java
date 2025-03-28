package me.grid.email_validator;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.microsoft.playwright.APIRequest;
import com.microsoft.playwright.APIRequestContext;
import com.microsoft.playwright.APIResponse;
import com.microsoft.playwright.Playwright;
import com.microsoft.playwright.options.RequestOptions;
import me.grid.email_validator.dataTransferObjects.EmailResponse;
import org.junit.jupiter.api.BeforeEach;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class RequestManager {

    APIRequestContext requestContext;
    ObjectMapper objectMapper;
    static String baseURI = "http://localhost:8080/validate/";

    public RequestManager(APIRequestContext requestContext){
        this.requestContext = requestContext;
        objectMapper = new ObjectMapper();
    }

    public EmailResponse getPOJOFromResponse(APIResponse response) throws IOException {
        return objectMapper.readValue(response.body(), EmailResponse.class);
    }

    public APIResponse sendPostRequestWithBody(Map<String, List<String>> body){
        return requestContext.post(baseURI, RequestOptions.create().setData(body));
    }

    public APIResponse sendPostRequestWithoutBody(){
        return requestContext.post(baseURI);
    }

    public Map<String, List<String>> createBodyFromList(List<String> list){
        Map<String, List<String>> map = new HashMap<>();
        map.put("emails", list);
        return map;
    }

}
