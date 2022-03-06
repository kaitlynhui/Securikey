package tests;

import model.Password;
import model.PwordManager;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class JsonTest {
    protected void checkPassword(String website, String password, String username, Password p) {
        assertEquals(website, p.getWebsite());
        assertEquals(password, p.getPassword());
        assertEquals(username, p.getUsername());
    }
}
