package tests;

import model.Password;
import model.PwordManager;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

// Represents the testing of various helper methods from the PwordManager class
public class PwordManagerTest {
    private PwordManager manager;
    private Password password;
    private Password password2;
    private Password password3;

    @BeforeEach
    public void setPasswordBank() {
        manager = new PwordManager("Your Password Manager");
        password = new Password("twitter", "beepBeepLettuce321$", "210ismyfavourite");
        password2 = new Password("instagram", "CPSC210BestClassEver!", "testtesttest");
        password3 = new Password("reddit", "whyIsThisNotWorking", "katkatkat");
    }

    @Test
    public void testConstructor() {
        manager = new PwordManager("Your Password Manager");
        assertEquals(0, manager.getNumberOfPasswords());
    }

    @Test
    public void testAddPassword() {
        manager.addPassword(password);
        assertEquals(1, manager.getNumberOfPasswords());
    }

    @Test
    public void testDeletePassword() {
        manager.addPassword(password);
        assertEquals(1, manager.getNumberOfPasswords());
        manager.deletePassword(password);
        assertEquals(0, manager.getNumberOfPasswords());
    }


    @Test
    public void testFindPassword () {
        manager.addPassword(password);
        manager.addPassword(password2);
        assertEquals("beepBeepLettuce321$", manager.findPassword("twitter"));
    }

    @Test
    public void testDontFindPassword () {
        manager.addPassword(password);
        manager.addPassword(password2);
        assertEquals("error: no passwords for this website", manager.findPassword("reddit"));
    }

    @Test
    public void extraFindPasswordTest() {
        manager.addPassword(password);
        manager.addPassword(password2);
        manager.addPassword(password3);
        assertEquals("CPSC210BestClassEver!", manager.findPassword("instagram"));
    }

    @Test
    public void testReturnPassword() {
        manager.addPassword(password2);
        manager.addPassword(password3);
        assertEquals(password2, manager.returnThePassword("instagram"));
    }

    @Test
    public void testDontReturnPassword() {
        manager.addPassword(password2);
        manager.addPassword(password3);
        assertEquals(null, manager.returnThePassword("facebook"));
    }

    @Test
    public void testGetPasswords() {
        manager.addPassword(password2);
        manager.addPassword(password3);
        assertEquals("[website:instagram\tpassword:CPSC210BestClassEver!\t username:testtesttest, " +
                "website:reddit\tpassword:whyIsThisNotWorking\t username:katkatkat]", manager.getPasswords().toString());
    }

    @Test
    public void testContainsPassword() {
        manager.addPassword(password2);
        manager.addPassword(password3);
        assertTrue(manager.containsPassword("instagram"));

    }

    @Test
    public void testDoesntContainsPassword() {
        manager.addPassword(password2);
        manager.addPassword(password3);
        assertFalse(manager.containsPassword("spotify"));

    }

}
