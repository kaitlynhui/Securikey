package model;

import org.json.JSONArray;
import org.json.JSONObject;
import persistence.Writable;

import java.util.ArrayList;

// Represents the actual storage of passwords and has helper methods to perform password related list operations
public class PwordManager implements Writable {
    private String name;
    private ArrayList<Password> passwords;


    // EFFECTS: constructs an empty Password manager that stores passwords
    public PwordManager(String name) {
        this.name = name;
        passwords = new ArrayList<>();
    }

    //getters
    public ArrayList<Password> getPasswords() {
        return passwords;
    }

    public int getNumberOfPasswords() {
        return passwords.size();
    }

    public String getName() {
        return name;
    }

    // setters
    // MODIFIES: this
    // EFFECTS: adds a password to the password bank
    public void addPassword(Password p) {
        passwords.add(p);
        EventLog.getInstance().logEvent(new Event(
                "a password for " + p.getWebsite() + " was added to manager."));
    }

    // REQUIRES: given password to already be in the password bank
    // MODIFIES: this
    // EFFECTS: removes password from this password bank
    public void deletePassword(Password p) {
        passwords.remove(p);
        EventLog.getInstance().logEvent(new Event(
                "the password for " + p.getWebsite() + " was deleted from the manager."));
    }

    //REQUIRES: non-empty Password manager
    //EFFECTS: returns password associated with the inputted website, if it's not found, return an error
    public String findPassword(String w) {
        for (Password p : passwords) {
            String website = p.getWebsite();
            if (website.equals(w)) {
                return p.getPassword();
            }
        }
        return "error: no passwords for this website";
    }

    //REQUIRES: non-empty Password manager
    //EFFECTS: returns password associated with the inputted website, if it's not found, return an error
    public Boolean containsPassword(String w) {
        for (Password p : passwords) {
            String website = p.getWebsite();
            if (website.equals(w)) {
                return true;
            }
        }
        return false;
    }

    //REQUIRES: non-empty Password manager
    //EFFECTS: returns password associated with the inputted website, if it's not found, return an error
    public Password returnThePassword(String w) {
        for (Password p : passwords) {
            String website = p.getWebsite();
            if (website.equals(w)) {
                return p;
            }
        }
        return null;
    }

    // EFFECTS: converts a password to a JSOn object
    @Override
    public JSONObject toJson() {
        JSONObject json = new JSONObject();
        json.put("name", name);
        json.put("passwords", passwordToJArray());
        return json;
    }

    // EFFECTS: returns passwords in password manager as a JSON array
    private JSONArray passwordToJArray() {
        JSONArray jsonArray = new JSONArray();

        for (Password p : passwords) {
            jsonArray.put(p.toJson());
        }

        return jsonArray;
    }
}
