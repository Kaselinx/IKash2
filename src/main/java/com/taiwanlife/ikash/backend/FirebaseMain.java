package com.taiwanlife.ikash.backend;

import java.io.ByteArrayInputStream;
import java.io.FileNotFoundException;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Base64;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.auth.oauth2.GoogleCredentials;
import com.google.firebase.FirebaseOptions;

public class FirebaseMain {

	final static Base64.Decoder decoder = Base64.getDecoder();
	final static Base64.Encoder encoder = Base64.getEncoder();
	@SuppressWarnings("unused")
	private static FirebaseOptions options;

	public static void main(String[] args) throws FileNotFoundException {
	
		String randomsWords = "sgtfstrewrw舒酸定qrew12121";
		try {
			//  file path...
			String path = "C:\\Key\\notification_besta.json";
			GoogleCredentials credentials = CreateCredentials(path);
			String iin = encoder.encodeToString(randomsWords.getBytes());
			// assign to builder...build success.
			options = FirebaseOptions.builder().setCredentials(credentials)
					// .setDatabaseUrl("https://<DATABASE_NAME>.firebaseio.com/")
					.build();
			
			
			System.out.println("encoder: " + iin);
			System.out.println("decoder: " + new String(decoder.decode(iin), "UTF-8"));
		} catch (Exception ex) {
			ex.printStackTrace();

		}

	}

	public static GoogleCredentials CreateCredentials(String fp) throws Exception {

		ArrayList<String> scopes = new ArrayList<String>();
		try {
			// Get string from file...
			String tokenString = readFileAsString(fp);

			// trying to decode base64 private key
			ObjectMapper mapper = new ObjectMapper();
			JasonToken token = mapper.readValue(tokenString, JasonToken.class);
			token.private_key = new String(decoder.decode(token.private_key), "UTF-8");

			// after convert back to json... there seems to be a extra \\ for each \ ,
			// replacing them.
			String jsonString = mapper.writeValueAsString(token).replace("\\\\", "\\");

			// convert string back to input steam.
			ByteArrayInputStream refreshToken = new ByteArrayInputStream(jsonString.getBytes(Charset.forName("UTF-8")));

			// create credentials ...
			GoogleCredentials credentials = GoogleCredentials.fromStream(refreshToken).createScoped(scopes);

			// double check primary key
			System.out.println(token.private_key);
			System.out.println(jsonString);

			return credentials;
		} catch (Exception ex) {
			ex.printStackTrace();
			throw ex;
		}
	}

	/*
	 * read from as string...
	 * 
	 */
	public static String readFileAsString(String file) throws Exception {
		return new String(Files.readAllBytes(Paths.get(file)));
	}

	/*
	 * model class..
	 */
	public static class JasonToken {
		private String type;
		private String project_id;
		private String private_key_id;
		private String private_key;
		private String client_email;
		private String client_id;
		private String auth_uri;
		private String token_uri;
		private String auth_provider_x509_cert_url;
		private String client_x509_cert_url;

		public String getType() {
			return type;
		}

		public void setType(String type) {
			this.type = type;
		}

		public String getProject_id() {
			return project_id;
		}

		public void setProject_id(String project_id) {
			this.project_id = project_id;
		}

		public String getPrivate_key_id() {
			return private_key_id;
		}

		public void setPrivate_key_id(String private_key_id) {
			this.private_key_id = private_key_id;
		}

		public String getPrivate_key() {
			return private_key;
		}

		public void setPrivate_key(String private_key) {
			this.private_key = private_key;
		}

		public String getClient_email() {
			return client_email;
		}

		public void setClient_email(String client_email) {
			this.client_email = client_email;
		}

		public String getClient_id() {
			return client_id;
		}

		public void setClient_id(String client_id) {
			this.client_id = client_id;
		}

		public String getAuth_uri() {
			return auth_uri;
		}

		public void setAuth_uri(String auth_uri) {
			this.auth_uri = auth_uri;
		}

		public String getToken_uri() {
			return token_uri;
		}

		public void setToken_uri(String token_uri) {
			this.token_uri = token_uri;
		}

		public String getAuth_provider_x509_cert_url() {
			return auth_provider_x509_cert_url;
		}

		public void setAuth_provider_x509_cert_url(String auth_provider_x509_cert_url) {
			this.auth_provider_x509_cert_url = auth_provider_x509_cert_url;
		}

		public String getClient_x509_cert_url() {
			return client_x509_cert_url;
		}

		public void setClient_x509_cert_url(String client_x509_cert_url) {
			this.client_x509_cert_url = client_x509_cert_url;
		}

	}

}
