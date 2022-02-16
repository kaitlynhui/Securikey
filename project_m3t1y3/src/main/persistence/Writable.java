package persistence;

import org.json.JSONObject;

// represents an interface with a method that converts some object to a JSON object
public interface Writable {
    // EFFECTS: returns this as JSON object
    JSONObject toJson();
}
