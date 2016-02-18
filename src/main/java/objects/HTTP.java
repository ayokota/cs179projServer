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

	            conn.connect();
	            BufferedReader in = new BufferedReader(
	                    new InputStreamReader(conn.getInputStream()));
	            String inputLine;
	            StringBuffer response = new StringBuffer();

	            while ((inputLine = in.readLine()) != null) {
	                response.append(inputLine);
	            }
	            in.close();

	            //print result
	            //System.out.println(response.toString());
	            result = response.toString();
	        } catch (Exception e) {
	            e.printStackTrace();
	        }
	        return result;
	    }
	
	public static void main(String[] args) {

		/*---------------------------------*/
		//System.out.println(new httpclient().getStatus("http://localhost:8080/main_server/chatService"));
		List<NameValuePair> urlParameters = new ArrayList<NameValuePair>();
		urlParameters.add(new BasicNameValuePair("username", "test"));
		urlParameters.add(new BasicNameValuePair("password", "pass123"));

		Map<String, String> params = new HashMap<String, String>();
        params.put("username", "ayoko001");
        params.put("password", "akiyo123");
        params.put("repassword", "akiyo123");
        params.put("fullname", "Akiyo Yokota");
        params.put("sex", "m");
        final String json = new Gson().toJson(params);


		//System.out.println(new httpclient().post("http://ec2-54-201-118-78.us-west-2.compute.amazonaws.com:8080/main_server/userAuthentication", urlParameters));
		//System.out.println(new httpclient().post("http://ec2-54-201-118-78.us-west-2.compute.amazonaws.com:8080/main_server/chatService", urlParameters));
		System.out.println(new HTTP().Post("http://localhost:8080/main_server/userSignUp", json));

	}
}
