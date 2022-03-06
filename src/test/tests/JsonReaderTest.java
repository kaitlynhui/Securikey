package tests;

import static org.junit.jupiter.api.Assertions.*;
import model.Password;
import model.PwordManager;
import org.junit.jupiter.api.Test;
import persistence.JsonReader;

import java.io.IOException;
import java.util.ArrayList;

class JsonReaderTest extends JsonTest {

    @Test
    public void testManagerDoesNotExist() {
        JsonReader readFile = new JsonReader("\"./data/nonExistentManager.json");
        try {
            PwordManager manager = readFile.read();
            fail("IOException expected");
        } catch (IOException e) {
            //handle failure
        }
    }

    @Test
    public void testEmptyPwordManager() {
        JsonReader readFile = new JsonReader("./data/testEmptyPwordManager.json");
        try {
            PwordManager manager = readFile.read();
            assertEquals("Your password manager", manager.getName());
            assertEquals(0, manager.getNumberOfPasswords());
        } catch (IOException e) {
            fail("Couldn't read from file");
        }
    }

    @Test
    public void testReadNonEmptyPwordManager() {
        JsonReader readFile = new JsonReader("./data/testNonEmptyPwordManager.json");
        try {
            PwordManager manager = readFile.read();
            assertEquals("Your password manager", manager.getName());
            ArrayList<Password> passwords = manager.getPasswords();
            assertEquals(2, manager.getNumberOfPasswords());
            checkPassword("instagram", "IloveCPSC210!", "cheeselover453", passwords.get(0));
            checkPassword("reddit", "trees", "CPSC210_isdabest", passwords.get(1));
        } catch (IOException e) {
            fail("Couldn't read from file");
        }


    }
}
