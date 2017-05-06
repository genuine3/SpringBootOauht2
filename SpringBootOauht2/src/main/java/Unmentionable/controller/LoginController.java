package Unmentionable.controller;

import java.util.Arrays;
import java.util.LinkedHashMap;
import java.util.Map;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.apache.commons.codec.binary.Base64;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import Unmentionable.model.Account;

import static Unmentionable.settings.security.OAuth2ServerConfiguration.CLIENT_ID;
import static Unmentionable.settings.security.OAuth2ServerConfiguration.CLIENT_SECRET;
import static Unmentionable.settings.security.OAuth2ServerConfiguration.ACCESS_TOKEN_EXPIRY;
import static Unmentionable.settings.security.OAuth2ServerConfiguration.REFRESH_TOKEN_EXPIRY;


@RestController
public class LoginController {

	// private static final String REST_API_URI = "http://localhost:8080";
	private static final String AUTH_SERVER_URI = "http://localhost:8080/oauth/token";
	private static final String PASSWORD_GRANT = "?grant_type=password&username=";
	private static final String REFRESH_TOKEN = "?grant_type=refresh_token&refresh_token=";

	@RequestMapping(value = "/login", method = RequestMethod.POST, consumes = "application/json")
	public ResponseEntity<Void> login(@RequestBody Account account, HttpServletResponse httpResponse) {
		
		// prepare request:
		HttpHeaders headers = new HttpHeaders();
		headers.setAccept(Arrays.asList(MediaType.APPLICATION_JSON));
		String userCredentials = new String(Base64.encodeBase64((CLIENT_ID + ":" + CLIENT_SECRET).getBytes()));
		headers.add("Authorization", "Bearer " + userCredentials);
		HttpEntity<String> request = new HttpEntity<String>(headers);
		
		// get response:
		RestTemplate restTemplate = new RestTemplate();
		ResponseEntity<Object> response = restTemplate.exchange(
				AUTH_SERVER_URI + PASSWORD_GRANT + account.getId() + "&password=" + account.getPassword(), HttpMethod.POST,
				request, Object.class);
		
		// set cookies:
		@SuppressWarnings("unchecked")
		Map<String, Object> map = (LinkedHashMap<String, Object>) response.getBody();
		if (map != null) {
			
			// set access token cookie:
			Cookie cookie = new Cookie("X-ACCESS-TOKEN", map.get("access_token").toString());
			// cookie.setSecure(true);
//			 cookie.setDomain(REST_API_URI);
			// cookie.setPath("/?acess_token=");
			cookie.setMaxAge(ACCESS_TOKEN_EXPIRY);  // an hour
			httpResponse.addCookie(cookie);
			
			// set refresh token cookie:
			cookie = new Cookie("X-REFRESH-TOKEN", map.get("refresh_token").toString());
			cookie.setHttpOnly(true);
			// cookie.setSecure(true);
			// cookie.setDomain(REST_API_URI);
			// cookie.setPath(AUTH_SERVER_URI);
			cookie.setMaxAge(REFRESH_TOKEN_EXPIRY);  // an year
			httpResponse.addCookie(cookie);
			
		} else {
			System.out.println("User not found");
			return new ResponseEntity<Void>(HttpStatus.NOT_FOUND);
		}
		return new ResponseEntity<Void>(HttpStatus.OK);
	}

}
