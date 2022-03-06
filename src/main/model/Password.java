package model;


import org.json.JSONObject;
import persistence.Writable;


// Represents a password that has a website, the password for the actual site, and the username associated with it
public class Password implements Writable {

    private String website;         // the website associated with password
    private String password;        // the password to be stored
    private String username;        // the username associated with password


    public Password(String site, String pass, String user) {
        this.website = site;
        this.password = pass;
        this.username = user;
    }

    // getters
    public String getPassword() {
        return password;
    }

    public String getWebsite() {
        return website;
    }

    public String getUsername() {
        return username;
    }


    //    setters
    public void setPassword(String password) {
        this.password = password;
    }

    public void setWebsite(String website) {
        this.website = website;
    }

    public void setUser(String username) {
        this.username = username;
    }

    // EFFECTS: returns a string representation of a password
    public String toString() {
        return "website:" + website + "\t" + "password:" + password + "\t " + "username:" + username;
    }

    // EFFECTS: returns the strength of your password based on the number of characters
    public String passwordStrength() {
        password = this.getPassword();
        if ((password.length()) < 6) {
            return "weak";
        } else if ((password.length()) < 13) {
            return "medium";
        } else {
            return "strong";
        }
    }

    @Override
    public JSONObject toJson() {
        JSONObject json = new JSONObject();
        json.put("website", website);
        json.put("password", password);
        json.put("username", username);
        return json;
    }
}
