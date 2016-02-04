package objects;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.List;

import org.apache.http.NameValuePair;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.message.BasicNameValuePair;

public class httpclient {
	
	private CloseableHttpClient httpClient;// = HttpClients.createDefault();
	public httpclient() {
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
	
	public static void main(String[] args) {

		System.out.println(new httpclient().getStatus("http://localhost:8080/main_server/chatService"));
		List<NameValuePair> urlParameters = new ArrayList<NameValuePair>();
		urlParameters.add(new BasicNameValuePair("sn", "C02G8416DRJM"));
		urlParameters.add(new BasicNameValuePair("cn", ""));
		urlParameters.add(new BasicNameValuePair("locale", ""));
		urlParameters.add(new BasicNameValuePair("caller", ""));
		urlParameters.add(new BasicNameValuePair("num", "12345"));
		System.out.println(new httpclient().post("http://localhost:8080/main_server/chatService", urlParameters));
	}
}
