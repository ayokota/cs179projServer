package objects;


import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.Map;

import org.apache.commons.io.IOUtils;
import org.json.JSONObject;

import com.google.gson.Gson;

public class gcmm {
	  //public static final String API_KEY = "AIzaSyCe4nkor8To0vlKRKVHrWyY-IEM992Zt94";
	  public static final String API_KEY = "AIzaSyCVia8kBQzG1uERcFELJINkUAKBqAQ-cdI"; //hangout


	    public static void main(String[] args) {
	        try {
	            // Prepare JSON containing the GCM message content. What to send and where to send.
	            JSONObject jGcmData = new JSONObject();
	            JSONObject jData = new JSONObject();
	            jData.put("message", "qqqq");

	            jGcmData.put("to", "/topics/global");
	            jGcmData.put("to", "ePXMHtvnWIw:APA91bEvCl1QZ9DYN7WKfTTp-jCw63OpABLtPn1xmiMkCLIy29zFp9DMTz6VF0zfNJXvS8GxtoehE0aaGyNk-5f18o9PK4Emd_1QaX67fJEhBM-EasxzbtuV4yBcyxIpNpyPlTcmv5zl");

	            jGcmData.put("data", jData);
	            
	            System.out.println(new Gson().toJson(jGcmData));

	            // Create connection to send GCM Message request.
	            URL url = new URL("https://android.googleapis.com/gcm/send");
	            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
	            conn.setRequestProperty("Authorization", "key=" + API_KEY);
	            conn.setRequestProperty("Content-Type", "application/json");
	            conn.setRequestMethod("POST");
	            conn.setDoOutput(true);

	            // Send GCM message content.
	            OutputStream outputStream = conn.getOutputStream();
	            outputStream.write(jGcmData.toString().getBytes());

	            // Read GCM response.
	            InputStream inputStream = conn.getInputStream();
	            String resp = IOUtils.toString(inputStream);
	            System.out.println(resp);
	            System.out.println("Check your device/emulator for notification or logcat for " +
	                    "confirmation of the receipt of the GCM message.");
	        } catch (IOException e) {
	            System.out.println("Unable to send GCM message.");
	            System.out.println("Please ensure that API_KEY has been replaced by the server " +
	                    "API key, and that the device's registration token is correct (if specified).");
	            e.printStackTrace();
	        }
	    }
}
