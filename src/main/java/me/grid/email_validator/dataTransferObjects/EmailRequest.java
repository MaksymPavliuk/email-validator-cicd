package me.grid.email_validator.dataTransferObjects;

import java.util.ArrayList;

public class EmailRequest {

    private ArrayList<String> emails;

    public EmailRequest(ArrayList<String> emails) {
        this.emails = emails;
    }

    public ArrayList<String> getEmails() {
        return emails;
    }
}
