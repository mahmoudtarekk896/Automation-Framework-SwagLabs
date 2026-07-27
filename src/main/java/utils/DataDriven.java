package utils;

import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;

import java.io.FileReader;

public class DataDriven {
    private static final String FILE_PATH = "src/test/resources/testData.json";

    public static JSONObject jsonReader() {

        JSONParser parser = new JSONParser();

        try (FileReader reader = new FileReader(FILE_PATH)) {
            Object object = parser.parse(reader);
            return (JSONObject) object;
        } catch (Exception e) {

            e.printStackTrace();
            return new JSONObject();
        }

    }
}