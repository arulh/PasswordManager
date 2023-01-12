package persistence;

import org.json.JSONArray;
import org.json.JSONObject;

// Represents a JsonFile with necessary constants
public abstract class JsonFile {

    public static final String ROOT_DIRECTORY = "./data/users/";
    protected static final int TAB = 4;
    protected JSONObject jsonObject;
    protected JSONArray passwords;
}
