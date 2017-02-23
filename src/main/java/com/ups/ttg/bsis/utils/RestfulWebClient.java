package com.ups.ttg.bsis.utils;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.Map;

import javax.net.ssl.HttpsURLConnection;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class RestfulWebClient {
	
	private static final Logger LOGGER = LogManager.getLogger(RestfulWebClient.class);


	/**
	 * Generic function which allows any RESTful GET request to be performed
	 * @param completeUrl
	 * @param parameters
	 * @return
	 */
	public static String sendGetRequest(String completeUrl, Map<String, String> parameters){
		try {
			URL url = new URL(completeUrl);
			LOGGER.trace("Created url...");
			
			HttpURLConnection conn = (HttpURLConnection) url.openConnection();
			/*if(this.HttpAuthEnabled){				
				 conn.setRequestProperty("Authorization", "Basic "+ getBasicAuthenticationEncoding());				
			}*/
			LOGGER.trace("Created connection");
			
			conn.setRequestMethod("GET");
			conn.setRequestProperty("Accept", "application/xml");
			conn.setDoOutput(true);
			
			LOGGER.debug("Sending get request to: " + completeUrl);
			
			BufferedReader br = new BufferedReader(new InputStreamReader((conn.getInputStream())));
			
			StringBuilder serverOutput = new StringBuilder();
			String tmp;
			while ((tmp = br.readLine()) != null) {
				serverOutput.append(tmp);
			}
			
			LOGGER.info("Sent request request to: " + completeUrl + "...  Server response: " + serverOutput.toString());
            
			conn.disconnect();
			
			return serverOutput.toString();
		} 
		catch (Exception e){
			e.printStackTrace();
		    LOGGER.warn("Get request failed to: " + completeUrl + ", Message: " + e.toString());   
            return "";
		}
	}
	
	/**
	 * Generic function which allows any RESTful POST request to be performed
	 * @param completeUrl
	 * @param body
	 * @return
	 */
	public static String sendPostRequest(String completeUrl, String body){
		try {
            URL url = new URL(completeUrl);
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            BufferedReader br = null;
            /*if(this.HttpAuthEnabled){               
                 conn.setRequestProperty("Authorization", "Basic "+ getBasicAuthenticationEncoding());              
            }*/
            LOGGER.trace("Created connection");
            
            
            conn.setRequestMethod("POST");
            //conn.setRequestProperty("Accept", acceptType);
            //conn.setRequestProperty("Content-Type", contentType);
            conn.setDoInput(true);
            conn.setDoOutput(true);

            //Send XML data to server
            LOGGER.debug("Sending get request to: " + completeUrl + ", Message body: " + body);
            
            DataOutputStream out = new DataOutputStream(conn.getOutputStream());
            out.writeBytes(body);
            out.flush();
            out.close();
            
            if (conn.getResponseCode() != 200) {
                br = new BufferedReader(new InputStreamReader((conn.getErrorStream())));
            }
            else{
                br = new BufferedReader(new InputStreamReader((conn.getInputStream())));
            }
            
            LOGGER.info(conn.getResponseCode() + " : " + conn.getResponseMessage());
          
            StringBuilder serverOutput = new StringBuilder();
            String tmp;
            while ((tmp = br.readLine()) != null) {
                serverOutput.append(tmp);
            }
            
            LOGGER.info("Sent request request to: " + completeUrl + "...  Server response: " + serverOutput.toString());
            
            conn.disconnect();
            
            return serverOutput.toString();
        } 
        catch (Exception e){
        	e.printStackTrace();
		    LOGGER.warn("Get request failed to: " + completeUrl + ", Message: " + e.toString());   
            return "";
        }
	}
}
