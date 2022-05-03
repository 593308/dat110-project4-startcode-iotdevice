package no.hvl.dat110.aciotdevice.client;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

import com.google.gson.Gson;

public class RestClient {
	
	private static String domain = "http://localhost:8080";

	public RestClient() {
		// TODO Auto-generated constructor stub
	}

	private static String logpath = "/accessdevice/log/";

	public void doPostAccessEntry(String message) {

		// TODO: implement a HTTP POST on the service to post the message
		AccessMessage am = new AccessMessage(message);
		Gson gson = new Gson();
		String json = gson.toJson(am);
		
		try {
			URL urlobj = new URL(domain + logpath);
			HttpURLConnection con = (HttpURLConnection) urlobj.openConnection();
			con.setRequestMethod("POST");
			con.setRequestProperty("Content-type", "application/json");
			
			con.setDoOutput(true);
			PrintWriter out = new PrintWriter(con.getOutputStream());
			out.print(json);
			out.flush();
			out.close();
			
			int responseCode = con.getResponseCode();
			//Skip reading response
			
			con.disconnect();
		} catch (MalformedURLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

	private static String codepath = "/accessdevice/code";

	public AccessCode doGetAccessCode() {

		AccessCode code = null;

		// TODO: implement a HTTP GET on the service to get current access code
		String json = "";
		try {
			URL urlobj = new URL(domain + codepath);
			HttpURLConnection con = (HttpURLConnection) urlobj.openConnection();
			con.setRequestMethod("GET");

			int responsCode = con.getResponseCode();

			if (responsCode == HttpURLConnection.HTTP_OK) {
				BufferedReader in = new BufferedReader(new InputStreamReader(con.getInputStream()));
				String inputLine;

				while ((inputLine = in.readLine()) != null)
					json += inputLine;

				in.close();
			}

			con.disconnect();
		} catch (MalformedURLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		Gson gson = new Gson();
		code = gson.fromJson(json, AccessCode.class);

		return code;
	}
}
