package model;

import exceptions.NotInListException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

public class UserTest {

    User user;

    @BeforeEach
    void runBefore() {
        user = new User("user1", "user1@gmail.com", "password123", User.generateID());
    }

    @Test
    void testConstructor() {
        assertEquals("user1", user.getUsername());
        assertEquals("user1@gmail.com", user.getEmail());
        assertEquals("password123", user.getAccountKey());
        assertTrue(user.getPasswordList().isEmpty());
    }

    @Test
    void testAddPassword() {
        user.addPassword("google", "myPassword91");
        assertEquals(1, user.getPasswordList().size());
        assertEquals("google", user.getPasswordList().get(0).getAccountName());
        assertEquals("myPassword91", user.getPasswordList().get(0).getAccountPassword());

        user.addPassword("gmail", "12345");
        assertEquals(2, user.getPasswordList().size());
        assertEquals("gmail", user.getPasswordList().get(1).getAccountName());
        assertEquals("12345", user.getPasswordList().get(1).getAccountPassword());
    }

    @Test
    void testRemovePassword() {
        user.addPassword("google", "myPassword91");
        try {
            user.removePassword("google");
            // expected
        } catch (NotInListException e) {
            fail("NotInListException should not be thrown");
        }
        assertTrue(user.getPasswordList().isEmpty());
        try {
            user.removePassword("google");
            fail("NotInListException should be thrown");
        } catch (NotInListException e) {
            // expected
        }
        assertTrue(user.getPasswordList().isEmpty());

        user.addPassword("youtube", "0");
        user.addPassword("gmail", "hello");

        try {
            user.removePassword("gmail");
            // expected
        } catch (NotInListException e) {
            fail("NotInListException should not be thrown");
        }
        assertEquals(1, user.getPasswordList().size());

        assertEquals("youtube", user.getPasswordList().get(0).getAccountName());
        assertEquals("0", user.getPasswordList().get(0).getAccountPassword());
    }

    @Test
    void testSetPasswordList() {
        Password p1 = new Password("google", "airplane");
        Password p2 = new Password("gmail", "Hello");
        Password p3 = new Password("youtube", "password12");
        List<Password> l = new ArrayList<>();
        l.add(p1);
        l.add(p2);
        user.setPasswordList(l);

        assertEquals(2, user.getPasswordList().size());
        assertEquals("google", user.getPasswordList().get(0).getAccountName());
        assertEquals("gmail", user.getPasswordList().get(1).getAccountName());

        l.add(p3);
        user.setPasswordList(l);

        assertEquals(3, user.getPasswordList().size());
        assertEquals("youtube", user.getPasswordList().get(2).getAccountName());
    }
}
