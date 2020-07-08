package util;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;

import java.io.FileReader;
import java.util.ArrayList;
import java.util.List;

public class JSONHandler {

    public static String pathJSONTestParameters = System.getProperty("user.dir")+"\\src\\main\\resources\\json\\dataOfTests.json";

    public static JSONObject fetchDataSetJSON(String testName)throws Exception{

        JSONObject result;
        List<JSONObject> testDataList = new ArrayList<JSONObject>();
        JSONArray testData = (JSONArray) extractObject_JSON(pathJSONTestParameters).get(testName);

        for ( int i = 0; i < testData.size(); i++ ) {
            testDataList.add((JSONObject) testData.get(i));
        }

        // create object for dataprovider to return
        result = testDataList.get(0);

        return result;
    }

    public static JSONObject extractObject_JSON(String file_path) throws Exception {
        FileReader reader = new FileReader(file_path);
        JSONParser jsonParser = new JSONParser();

        return (JSONObject) jsonParser.parse(reader);
    }

}
