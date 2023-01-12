package model;

import exceptions.NotInListException;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

// Represents a user that contains account information
// (username, email, account key, and the list of stored passwords)
public class User {

    private String username;
    private String email;
    private String accountKey;
    private List<Password> passwordList;
    private int id;

    private EventLog events;

    // EFFECTS: creates new user object with account details (username, email, key)
    //          passwordList is initialized to be empty
    public User(String username, String email, String key, int id) {
        events = EventLog.getInstance();
        this.username = username;
        this.email = email;
        this.accountKey = key;
        passwordList = new ArrayList<>();
        this.id = id;
    }

    // EFFECTS: returns random number from [1, 999]
    public static int generateID() {
        Random r = new Random();
        return r.nextInt(999) + 1;
    }

    // MODIFIES: this
    // EFFECTS: creates new password Object with given account password and name and adds it to passwordList
    public void addPassword(String name, String key) {
        Password p = new Password(name, key);
        passwordList.add(p);
        events.logEvent(new Event("PASSWORD WAS ADDED TO ACCOUNT"));
    }

    // MODIFIES: this
    // EFFECTS: removes password with given account name from list
    //          if account name not in list, then throw NotInListException
    public void removePassword(String accountName) throws NotInListException {
        for (Password p : passwordList) {
            if (p.getAccountName().equals(accountName)) {
                passwordList.remove(p);
                events.logEvent(new Event("PASSWORD WAS REMOVED FROM ACCOUNT"));
                return;
            }
        }
        throw new NotInListException();
    }

    public void setPasswordList(List<Password> pl) {
        this.passwordList = pl;
    }

    public String getEmail() {
        return this.email;
    }

    public String getUsername() {
        return this.username;
    }

    public String getAccountKey() {
        return this.accountKey;
    }

    public List<Password> getPasswordList() {
        return passwordList;
    }

    public int getId() {
        return id;
    }
}
