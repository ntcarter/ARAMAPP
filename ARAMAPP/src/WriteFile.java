import org.json.JSONArray;
import org.json.JSONObject;
import java.io.FileWriter;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;

public class WriteFile {
    public void WriteJSONFile(JSONObject obj) {
        try {
            JSONObject jsonObject;
            jsonObject = obj;

            FileWriter newFile = new FileWriter("ARAMRequest.json");
            newFile.write(jsonObject.toString());
            newFile.flush();
            newFile.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}

