package tests;

import static org.junit.jupiter.api.Assertions.*;
import model.Password;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

// Represents testing of various helper methods from the Password class
class PasswordTest {
    private Password password;
    private Password p2;
    private Password p3;

    @BeforeEach
    public void passwordSetUp() {
        password = new Password("instagram", "IloveCPSC210!", "cheeselove453");
        p2 = new Password("reddit", "trees", "CPSC210_isdabest");
        p3 = new Password("facebook", "beepbeepbeep", "katkatkatkak");
    }

    @Test
    public void testConstructor() {
        assertEquals("IloveCPSC210!", password.getPassword());
        assertEquals("instagram", password.getWebsite());
        assertEquals("cheeselove453", password.getUsername());
    }

    @Test
    public void testPasswordStrength() {
        assertEquals("strong", password.passwordStrength());
    }

    @Test
    public void testPasswordStrengthWeak() {
        assertEquals("weak", p2.passwordStrength());
    }

    @Test
    public void testPasswordStrengthMedium() {
        assertEquals("medium", p3.passwordStrength());
    }

    @Test
    public void changePassword() {
        password.setPassword("testTest1234!");
        assertEquals("testTest1234!", password.getPassword());
    }

    @Test
    public void passwordToString() {
        assertEquals("website:instagram\tpassword:IloveCPSC210!\t username:cheeselove453",
                password.toString());
    }

    @Test
    public void testSetWebsite() {
        password.setWebsite("twitter");
        assertEquals("twitter", password.getWebsite());
    }

    @Test
    public void testSetUser() {
        password.setUser("CPSC210ISTHEBESTEVER");
        assertEquals("CPSC210ISTHEBESTEVER", password.getUsername());
    }



}

