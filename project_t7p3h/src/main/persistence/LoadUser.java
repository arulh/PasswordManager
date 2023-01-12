package persistence;

import model.Password;
import org.json.JSONObject;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.*;

// Represents account details read from json file
public class LoadUser extends JsonFile {

    private String username;
    private String accountKey;
    private String email;
    private int id;
    Map<String, String> entry = new HashMap<>();
    private List<Password> passwordList;

    // EFFECTS: loads user's username, accountKey, and email from json file
    public LoadUser(String directory) {
        try {
            String user = new String((Files.readAllBytes(Paths.get(directory))));
            jsonObject = new JSONObject(user);
            username = (String) jsonObject.get("Username");
            accountKey = (String) jsonObject.get("Account Key");
            email = (String) jsonObject.get("Email");
            id = (int) jsonObject.get("ID Number");
        } catch (IOException e) {
            System.out.println("Error: File directory does not exist");
        }
    }

    // EFFECTS: returns true if entered login info matches account (username and accountKey)
    public boolean checkLoginDetails(String name, String key) {
        return this.username.equals(name) && this.accountKey.equals(key);
    }

    // MODIFIES: this
    // EFFECTS: loads user's passwords from json file and adds them to passwordList
    public void loadPasswords() {
        passwords = jsonObject.getJSONArray("Passwords");
        List<Object> l = passwords.toList();
        passwordList = new ArrayList<>();
        for (int i = 0; i < l.size(); i++) {
            entry = (Map<String, String>) l.get(i);
            Password p = new Password(entry.get("accountName"), entry.get("accountPassword"));
            passwordList.add(p);
        }
    }

    public String getUsername() {
        return username;
    }

    public String getAccountKey() {
        return accountKey;
    }

    public String getEmail() {
        return email;
    }

    public int getId() {
        return id;
    }

    public List<Password> getPasswordList() {
        return passwordList;
    }
}
