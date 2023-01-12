package model;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

class PasswordTest {

    Password password;

    @BeforeEach
    void runBefore() {
        password = new Password("google", "hello");
    }

    @Test
    void testConstructor() {
        assertEquals("google", password.getAccountName());
        assertEquals("hello", password.getAccountPassword());
    }
}