package me.grid.email_validator.dataTransferObjects;

import java.util.ArrayList;
import java.util.List;

public class EmailResponse {

    private List<String> validEmails = new ArrayList<>();
    private List<String> invalidEmails = new ArrayList<>();

    public List<String> getInvalidEmails() {
        return invalidEmails;
    }

    public List<String> getValidEmails() {
        return validEmails;
    }

    public EmailResponse(){}

    public EmailResponse(List<String> validEmails, List<String> invalidEmails){
        this.validEmails = validEmails;
        this.invalidEmails = invalidEmails;
    }
}
