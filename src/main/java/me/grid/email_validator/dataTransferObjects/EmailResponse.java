package me.grid.email_validator.dataTransferObjects;

import java.util.ArrayList;

public class EmailResponse {

    private ArrayList<String> validEmails = new ArrayList<>();
    private ArrayList<String> invalidEmails = new ArrayList<>();

    public void addValid(String value){
        validEmails.add(value);
    }

    public void addInvalid(String value){
        invalidEmails.add(value);
    }

    public ArrayList<String> getInvalidEmails() {
        return invalidEmails;
    }

    public ArrayList<String> getValidEmails() {
        return validEmails;
    }
}
