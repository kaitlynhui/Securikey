package persistence;

import model.Password;
import model.PwordManager;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.stream.Stream;

import org.json.*;

// REFERENCE: This particular class is based heavily on the JSONSerializationDemo project from CPSC 210.

// Represents a reader that reads password manager from JSON data stored in file
public class JsonReader {
    private String source;

    // EFFECTS: constructs reader to read from source file
    public JsonReader(String source) {
        this.source = source;
    }

    // EFFECTS: reads password manager from file and if no error occurs, returns the password manager;
    // if an error occurs, it throws an IOException
    public PwordManager read() throws IOException {
        String jsonData = readFile(source);
        JSONObject jsonObject = new JSONObject(jsonData);
        return parsePwordManager(jsonObject);
    }

    // EFFECTS: reads source file as string and returns it
    private String readFile(String source) throws IOException {
        StringBuilder contentBuilder = new StringBuilder();

        try (Stream<String> stream = Files.lines(Paths.get(source), StandardCharsets.UTF_8)) {
            stream.forEach(s -> contentBuilder.append(s));
        }

        return contentBuilder.toString();
    }

    // EFFECTS: parses password manager from JSON object and returns it as a PwordManager object
    private PwordManager parsePwordManager(JSONObject jsonObject) {
        String name = jsonObject.getString("name");
        PwordManager manager = new PwordManager(name);
        addPasswords(manager, jsonObject);
        return manager;
    }

    // MODIFIES: password manager
    // EFFECTS: parses passwords stored from JSON object and adds them to the password manager
    private void addPasswords(PwordManager manager, JSONObject jsonObject) {
        JSONArray jsonArray = jsonObject.getJSONArray("passwords");
        for (Object json : jsonArray) {
            JSONObject nextPassword = (JSONObject) json;
            addPassword(manager, nextPassword);
        }
    }

    // MODIFIES: password manager
    // EFFECTS: parses paswords from JSON object and adds it to manager
    private void addPassword(PwordManager manager, JSONObject jsonObject) {
        String name = jsonObject.getString("website");
        String pass = jsonObject.getString("password");
        String user = jsonObject.getString("username");
        Password password = new Password(name, pass, user);
        manager.addPassword(password);
    }
}