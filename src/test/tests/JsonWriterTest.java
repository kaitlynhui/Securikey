package tests;

import model.Password;
import model.PwordManager;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import persistence.JsonReader;
import persistence.JsonWriter;

import java.io.IOException;
import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.fail;

class JsonWriterTest extends JsonTest {

    private Password pass;
    private Password p2;
    private Password p3;


    @BeforeEach
    public void passwordSetUp() {
        pass = new Password("instagram", "IloveCPSC210!", "cheeselover453");
        p2 = new Password("reddit", "trees", "CPSC210_isdabest");
        p3 = new Password("facebook", "beepbeepbeep", "katkatkatkak");
    }

    @Test
    void testIllegalFileName() {
        try {
            PwordManager manager = new PwordManager("My password manager");
            JsonWriter writer = new JsonWriter("./data/my\0;illegal:fileName.json");
            writer.openWriter();
            fail("IOException expected");
        } catch (IOException e) {
            // pass
        }
    }

    @Test
    void testWriteEmptyPwordManager() {
        try {
            PwordManager manager = new PwordManager("Your password manager");
            JsonWriter writer = new JsonWriter("./data/testEmptyPwordManager.json");
            writer.openWriter();
            writer.write(manager);
            writer.closeWriter();

            JsonReader readFile = new JsonReader("./data/testEmptyPwordManager.json");
            manager = readFile.read();
            assertEquals("Your password manager", manager.getName());
            assertEquals(0, manager.getNumberOfPasswords());
        } catch (IOException e) {
            fail("Exception should not have been thrown");
        }
    }

    @Test
    void testWriteNonEmptyPwordManager() {
        try {
            PwordManager manager = new PwordManager("Your password manager");
            manager.addPassword(pass);
            manager.addPassword(p2);
            JsonWriter writer = new JsonWriter("./data/testNonEmptyPwordManager.json");
            writer.openWriter();
            writer.write(manager);
            writer.closeWriter();

            JsonReader readFile = new JsonReader("./data/testNonEmptyPwordManager.json");
            manager = readFile.read();
            assertEquals("Your password manager", manager.getName());
            ArrayList<Password> passwords = manager.getPasswords();
            assertEquals(2, manager.getNumberOfPasswords());
            checkPassword("instagram", "IloveCPSC210!", "cheeselover453", passwords.get(0));
            checkPassword("reddit", "trees", "CPSC210_isdabest", passwords.get(1));
        } catch (IOException e) {
            fail("Exception should not have been thrown");
        }
    }
}