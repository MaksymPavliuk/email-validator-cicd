package me.grid.email_validator.dataTransferObjects;

import java.util.List;

public class EmailRequest {

    private List<String> emails;

    public EmailRequest(List<String> emails) {
        this.emails = emails;
    }

    public List<String> getEmails() {
        return emails;
    }
}
