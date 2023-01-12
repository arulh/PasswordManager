package persistence;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class LoadUserTest {

    private final String DIRECTORY = "./data/test.json";
    LoadUser loadedUser;

    @BeforeEach
    void runBefore() {
        loadedUser = new LoadUser(DIRECTORY);
    }

    @Test
    void testConstructor() {
        assertEquals("test", loadedUser.getUsername());
        assertEquals("test@xxx.com", loadedUser.getEmail());
        assertEquals("12345", loadedUser.getAccountKey());
        assertTrue(loadedUser.getId() >= 1 && loadedUser.getId() <= 999);

        // checks that passwords are not loaded
        try {
            loadedUser.getPasswordList().size();
            fail("NullPointerException not caught");
        } catch (NullPointerException e) {
            // expected
        }
    }

    @Test
    void testConstructorNonExistentDirectory() {
        try {
            LoadUser user = new LoadUser("./data/bad_file.json");
            // expected
        } catch (Exception e) {
            fail("Exception was not expected");
        }
    }

    @Test
    void testCheckLoginDetails() {
        assertTrue(loadedUser.checkLoginDetails("test", "12345"));
        assertFalse(loadedUser.checkLoginDetails("test", "hello"));
        assertFalse(loadedUser.checkLoginDetails("111", "12345"));
    }

    @Test
    void testLoadPasswords() {
        loadedUser.loadPasswords();
        assertEquals(2, loadedUser.getPasswordList().size());
        assertEquals("google", loadedUser.getPasswordList().get(0).getAccountName());
        assertEquals("gmail", loadedUser.getPasswordList().get(1).getAccountName());
    }
}
