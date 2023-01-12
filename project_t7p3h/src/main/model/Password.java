package model;

// Represents a password containing an account name and the password associated with the account
public class Password {

    private String accountName;
    private String accountPassword;

    // EFFECTS: creates new password object with given account name and key
    public Password(String name, String key) {
        this.accountName = name;
        this.accountPassword = key;
    }

    public String getAccountName() {
        return this.accountName;
    }

    public String getAccountPassword() {
        return this.accountPassword;
    }
}
