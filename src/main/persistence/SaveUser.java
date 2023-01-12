package persistence;

import model.User;
import org.json.JSONArray;
import org.json.JSONObject;

import java.io.FileWriter;
import java.io.IOException;

// saves user account details to file
public class SaveUser extends JsonFile {

    private String directory;

    // EFFECTS: writes user account details (username, email, accountKey) to json file at given directory
    public SaveUser(User user) {
        directory = ROOT_DIRECTORY + "USR-" + user.getUsername().toUpperCase() + "-"
                + String.format("%03d", user.getId()) + ".json";
        jsonObject = new JSONObject();

        jsonObject.put("Username", user.getUsername());
        jsonObject.put("Email", user.getEmail());
        jsonObject.put("Account Key", user.getAccountKey());
        jsonObject.put("ID Number", user.getId());

        passwords = new JSONArray(user.getPasswordList());

        jsonObject.put("Passwords", passwords);
    }

    // EFFECTS: writes JSONObject to json file at given directory
    public void write() {
        try  (FileWriter file = new FileWriter(directory)) {
            file.write(jsonObject.toString(TAB));
        } catch (IOException e) {
            System.out.println("File could not be found");
        }
    }

    public void setDirectory(String directory) {
        this.directory = directory;
    }
}
