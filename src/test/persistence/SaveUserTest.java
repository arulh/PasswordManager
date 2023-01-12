package persistence;

import model.User;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.fail;

public class SaveUserTest {

    private final String DIRECTORY = "./data/test.json";
    SaveUser session;
    User savedUser;
    LoadUser loadedUser;

    @BeforeEach
    void runBefore() {
        savedUser = new User("test", "test@xxx.com", "12345", User.generateID());
        savedUser.addPassword("google", "rocketShip123");
        savedUser.addPassword("gmail", "HelloWorld222");
        session = new SaveUser(savedUser);
        session.setDirectory(DIRECTORY);
        session.write();
    }

    @Test
    void testConstructor() {
        loadedUser = new LoadUser(DIRECTORY);
        loadedUser.loadPasswords();
        assertEquals("test", loadedUser.getUsername());
        assertEquals("test@xxx.com", loadedUser.getEmail());
        assertEquals("12345", loadedUser.getAccountKey());

        assertEquals(2, loadedUser.getPasswordList().size());
        assertEquals("google", loadedUser.getPasswordList().get(0).getAccountName());
        assertEquals("gmail", loadedUser.getPasswordList().get(1).getAccountName());
    }

    @Test
    void testConstructorNonExistentDirectory() {
        try {
            SaveUser json = new SaveUser(savedUser);
            json.setDirectory("./data/bad_file\0.json");
            json.write();
            // expected
        } catch (Exception e) {
            fail("Exception was not expected");
        }
    }
}
