package objects;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.http.NameValuePair;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.message.BasicNameValuePair;

import com.google.gson.Gson;

public class HTTP {
	private CloseableHttpClient httpClient;// = HttpClients.createDefault();
	public HTTP() {
		httpClient = HttpClients.createDefault();
	}
	
	public String post(String url, List<NameValuePair> params){
		HttpPost httppost = new HttpPost(url);
	    httppost.addHeader("User-Agent", "Mozilla/5.0");
	    
	    if(params!=null) {
		    try {
				httppost.setEntity(new UrlEncodedFormEntity(params));
			} catch (UnsupportedEncodingException e1) {
				e1.printStackTrace();
			}
	    }
	    String httpresponse = "";
	    try {
			CloseableHttpResponse httpResponse = httpClient.execute(httppost);
			
	        BufferedReader reader = new BufferedReader(new InputStreamReader(
	                httpResponse.getEntity().getContent()));
	 
	        String inputLine;
	        StringBuffer response = new StringBuffer();
	 
	        while ((inputLine = reader.readLine()) != null) {
	            response.append(inputLine);
	        }
	        reader.close();
	 
	        httpresponse = response.toString();
	        httpClient.close();
		} catch (ClientProtocolException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	    return httpresponse;
	}
	
	public int getStatus(String url) {
	    HttpPost httppost = new HttpPost(url);
	    httppost.addHeader("User-Agent", "Mozilla/5.0");

		CloseableHttpResponse httpResponse = null;
		try {
			httpResponse = httpClient.execute(httppost);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return httpResponse.getStatusLine().getStatusCode();
		 
	}
	
	  public static String Post(String u, String json) {
	        String result = "";
	        try {
	            URL url = new URL(u);
	            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
	            conn.setReadTimeout(10000);
	            conn.setConnectTimeout(15000);
	            conn.setRequestMethod("POST");
	            conn.setDoInput(true);
	            conn.setDoOutput(true);

	            //        List<NameValuePair> params = new ArrayList<NameValuePair>();
	            //        params.add(new BasicNameValuePair("firstParam", paramValue1));
	            //        params.add(new BasicNameValuePair("secondParam", paramValue2));
	            //        params.add(new BasicNameValuePair("thirdParam", paramValue3));
//	            Map<String, String> params = new HashMap<String, String>();
//	            params.put("username", "test");
//	            params.put("password", "pass123");

	            OutputStream os = conn.getOutputStream();
	            BufferedWriter writer = new BufferedWriter(
	                    new OutputStreamWriter(os, "UTF-8"));


	            //String json = new Gson().toJson(json);
	            System.out.println(json);
	            writer.write(json);
	            writer.flush();
	            writer.close();
	            os.close();

	            StringBuffer response = new StringBuffer ();
	            conn.connect();
	            if (conn.getResponseCode()==200) {
		            BufferedReader in = new BufferedReader(
		                    new InputStreamReader(conn.getInputStream()));
		            String inputLine;
		            response = new StringBuffer();
	
		            while ((inputLine = in.readLine()) != null) {
		                response.append(inputLine);
		            }
		            in.close();
		            result = response.toString();

	            } else {
	            	result = Integer.toString(conn.getResponseCode());
	            }

	            //print result
	            //System.out.println(response.toString());
	        } catch (Exception e) {
	            e.printStackTrace();
	        }
	        return result;
	    }
	
	public static void main(String[] args) {

		/*---------------------------------*/
		//System.out.println(new httpclient().getStatus("http://localhost:8080/main_server/chatService"));
		Map<String, String> params = new HashMap<String, String>();

//		params.put("user1", "ayoko001");
//		params.put("username", "ayoko001");
//		params.put("password", "akiyo456");
		
//		params.put("user1",  "ayoko001");
//		params.put("user2", " bs");
//		params.put("status", "requested");
		
        //final String json = new Gson().toJson(params);
        
        /*--------------------- user authentication -----------------------*/

//      params.put("username", "ayoko001");
//      params.put("password", "ppp");
//      //params.put("gcmtoken", "dtFmxeUspyY:APA91bGDgxSQtkeVAII0Dt9XI4iszN5WVCVR-gX3NP-RAAYM4S2m9OX-5hFu60xnVyTF9SyweGTINiZzwOQxpNHoFI3XYgYMXCF1xWHFucQlmBKQC-nValR6Vt-_s3yQG63Lb5sRMWgp");
//      params.put("gcmtoken", "fuckyou!!");
//      final String json = new Gson().toJson(params);
//        //System.out.println(new HTTP().Post("http://localhost:8080/main_server/userAuthentication", json));
//		System.out.println(new HTTP().Post("http://ec2-54-201-118-78.us-west-2.compute.amazonaws.com:8080/main_server/userAuthentication", json));

//        
//        /*----------------- user sign up ------------------- */
//        params.put("username", "ayoko006");
//        params.put("password", "akiyo123");
//        params.put("repassword", "akiyo123");
//        params.put("fullname", "Akiyo Yokota");
//        params.put("sex", "m");
//        final String json = new Gson().toJson(params);
//		//System.out.println(new HTTP().Post("http://localhost:8080/main_server/userSignUp", json));
//		System.out.println(new HTTP().Post("http://ec2-54-201-118-78.us-west-2.compute.amazonaws.com:8080/main_server/userSignUp", json));

        
        /*--------------------- prof update -----------------------*/
//		params.put("username", "ayoko001");
//		params.put("oldpassword", "ppp");
//		params.put("newpassword", "aaa");
////		params.put("newfullname", "Travis Phan");
//        final String json = new Gson().toJson(params);
//        //System.out.println(new HTTP().Post("http://localhost:8080/main_server/profUpdate", json));
//        System.out.println(new HTTP().Post("http://ec2-54-201-118-78.us-west-2.compute.amazonaws.com:8080/main_server/profUpdate", json));
        
        /*--------------------- contacts -----------------------*/
//		params.put("user1", "ayoko001");
//		params.put("user2", "sample");
//		params.put("status", "declined");
//		//params.put("check", "ayoko001");
//
//		
//        final String json = new Gson().toJson(params);
//        System.out.println(new HTTP().Post("http://localhost:8080/main_server/contacts", json));
//        //System.out.println(new HTTP().Post("http://ec2-54-201-118-78.us-west-2.compute.amazonaws.com:8080/main_server/contacts", json));
		
		
        /*--------------------- chat service -----------------------*/
//		params.put("gcmtoken", "test");
//		
//        final String json = new Gson().toJson(params);
//        //System.out.println(new HTTP().Post("http://localhost:8080/main_server/chatService", json));
//        System.out.println(new HTTP().Post("http://ec2-54-201-118-78.us-west-2.compute.amazonaws.com:8080/main_server/chatService", json));

		
		//System.out.println(new HTTP().Post("http://pubsub.pubnub.com/v1/push/sub-key/sub-c-caec8254-d91f-11e5-8758-02ee2ddab7fe/devices/cXYv_II4ZEE:APA91bFC66fs57HX-ngUVIs7QL5A-E-8y9MaXLqCTjDYCNZDaBVf50a3SWOO2Ih9dg_59BLdYiXYVpxQ4TlTFGOW3L4lN0HCsBQ-V8T4Qyr3Lm8Wlaxom8jnURvyRWKgsD1SX0KvFIy_?add=ayoko001&type=gcm", ""));
		//System.out.println(new HTTP().Post("http://pubsub.pubnub.com/v1/push/sub-key/sub-c-caec8254-d91f-11e5-8758-02ee2ddab7fe/devices/cXYv_II4ZEE:APA91bFC66fs57HX-ngUVIs7QL5A-E-8y9MaXLqCTjDYCNZDaBVf50a3SWOO2Ih9dg_59BLdYiXYVpxQ4TlTFGOW3L4lN0HCsBQ-V8T4Qyr3Lm8Wlaxom8jnURvyRWKgsD1SX0KvFIy_?type=gcm",""));
	
        /*--------------------- events -----------------------*/
//		params.put("id", "ayoko001pppp");
//		params.put("attendee", "worldpeace");
//		params.put("host", "ayoko001");
//		params.put("topic", "demo3");
//		params.put("keyword", "cs179 presentation");
//		params.put("time", "2016-02-28 10:05:10");
//		params.put("location", "ucr lab 133");
//		
//		params.put("delete", "ayoko001pp");
//		
//		
//		params.put("search", "");
        final String json = new Gson().toJson(params);
//        System.out.println(new HTTP().Post("http://localhost:8080/main_server/events", json));
        System.out.println(new HTTP().Post("http://ec2-54-201-118-78.us-west-2.compute.amazonaws.com:8080/main_server/events", json));

		
        /*--------------------- tracking -----------------------*/
//		params.put("id", "ayoko001pppp");
//		params.put("attendee", "dog");
////		//params.put("location", "5");
////
////		
//		
//        final String json = new Gson().toJson(params);
////        //System.out.println(new HTTP().Post("http://localhost:8080/main_server/tracking", json));
//        System.out.println(new HTTP().Post("http://ec2-54-201-118-78.us-west-2.compute.amazonaws.com:8080/main_server/tracking", json));
////		
		//System.out.println(new HTTP().Post("http://pubsub.pubnub.com/v1/push/sub-key/sub-c-caec8254-d91f-11e5-8758-02ee2ddab7fe/devices/cXYv_II4ZEE:APA91bFC66fs57HX-ngUVIs7QL5A-E-8y9MaXLqCTjDYCNZDaBVf50a3SWOO2Ih9dg_59BLdYiXYVpxQ4TlTFGOW3L4lN0HCsBQ-V8T4Qyr3Lm8Wlaxom8jnURvyRWKgsD1SX0KvFIy_?add=ayoko001&type=gcm", ""));
		//System.out.println(new HTTP().Post("http://pubsub.pubnub.com/v1/push/sub-key/sub-c-caec8254-d91f-11e5-8758-02ee2ddab7fe/devices/cXYv_II4ZEE:APA91bFC66fs57HX-ngUVIs7QL5A-E-8y9MaXLqCTjDYCNZDaBVf50a3SWOO2Ih9dg_59BLdYiXYVpxQ4TlTFGOW3L4lN0HCsBQ-V8T4Qyr3Lm8Wlaxom8jnURvyRWKgsD1SX0KvFIy_?type=gcm",""));
	
    	
        /*--------------------- attendee -----------------------*/
//		params.put("id", "ayoko001pppp");
//		//params.put("attendee", "sample2");
//
//        final String json = new Gson().toJson(params);
//        //System.out.println(new HTTP().Post("http://localhost:8080/main_server/attendee", json));
//        System.out.println(new HTTP().Post("http://ec2-54-201-118-78.us-west-2.compute.amazonaws.com:8080/main_server/attendee", json));

	}
}
