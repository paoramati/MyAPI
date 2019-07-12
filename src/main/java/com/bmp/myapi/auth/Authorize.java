/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.bmp.myapi.auth;

import com.bmp.myapi.model.Data;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import java.nio.charset.StandardCharsets;
import java.util.Base64;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.Set;
import java.util.function.Predicate;
import javax.servlet.http.HttpServletRequest;
import javax.ws.rs.core.HttpHeaders;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.Status;
//import org.apache.commons.codec.binary.Base64;

/**
 *
 * @author shareuser
 */
public class Authorize
{



	private AuthParams authParams = null;

	private Response response = null;
	private HttpServletRequest req;

	public Authorize(AuthParams authParams)
	{
		this.authParams = authParams;
//        checkAuthorization();
	}

	public Authorize(HttpServletRequest req)
	{
		this.req = req;
	}

	public Response getResponse()
	{
		return response;
	}

	/**
	 * Return true if this request is authorized by Basic authentication
	 *
	 * @param req
	 * @return
	 */
	public static boolean getIsBasicAuthorized(HttpServletRequest req)
	{
		Authorize authorize = new Authorize(req);
		final Optional<String[]> authValue = authorize.getAuthCredentials();
		if (authValue.isPresent())
		{
			String[] creds = authValue.get();
			boolean result = authorize.checkUsernamePassword(creds[0], creds[1]);
			return result;
		}
		return false;
	}
	
	public String generateJWT(HttpServletRequest req)
	{
		JwTokenHelper helper = JwTokenHelper.getInstance();
		Optional<String[]> authHeader = getAuthCredentials(req);
		if (authHeader.isPresent())
		{
			String token = helper.generatePrivateKey(authHeader.get()[0], authHeader.get()[1]);
			return token;
		}
		return null;
	}

	private Optional<String[]> getAuthCredentials()
	{
		return getAuthCredentials(req);
//		List<String> authHeaders = Collections.list(req.getHeaders("Authorization"));
//		if (!authHeaders.isEmpty())
//		{
//			// find Basic auth header
//			for (String authHeader : authHeaders)
//			{
//				if ("Basic".equals(authHeader.substring(0, 5)))
//				{
//					byte[] bytes = Base64.getDecoder().decode(authHeader.substring("Basic".length()).trim());
//					String credentials = new String(bytes, StandardCharsets.UTF_8);
//					return Optional.of(credentials.split(":"));
//				}
//			}
//		}
//		return Optional.empty();
	}

	private Optional<String[]> getAuthCredentials(HttpServletRequest req)
	{
		List<String> authHeaders = Collections.list(req.getHeaders("Authorization"));
		if (!authHeaders.isEmpty())
		{
			// find Basic auth header
			for (String authHeader : authHeaders)
			{
				if ("Basic".equals(authHeader.substring(0, 5)))
				{
					byte[] bytes = Base64.getDecoder().decode(authHeader.substring("Basic".length()).trim());
					String credentials = new String(bytes, StandardCharsets.UTF_8);
					return Optional.of(credentials.split(":"));
				}
			}
		}
		return Optional.empty();
	}

	private boolean checkUsernamePassword(final String username, final String password)
	{
		Predicate<Credentials> credentialsMatch = c
			-> c.getUsername().equals(username) && c.getPassword().equals(password);
		boolean result = users.stream().anyMatch(credentialsMatch);
		return result;
	}

	final static Set<Credentials> users = new HashSet();

	static
	{
		users.add(new Credentials("brandon", "password"));
	}

	public String getAccessToken(String token)
	{
		Data.Access access = new Data.Access();
		access.access_token = token;
		return gson().toJson(access, Data.Access.class);
	}

	public static final class Credentials
	{

		private String username = null;
		private String password = null;

		public Credentials(final String username, final String password)
		{
			this.username = username;
			this.password = password;
		}

		public String getUsername()
		{
			return username;
		}

		public String getPassword()
		{
			return password;
		}
	}

	/**
	 * Parameters
	 */
	public static final class AuthParams
	{

		public HttpServletRequest req = null;
		public HttpHeaders hh = null;
	}
	
	public static Gson gson()
	{
		return new GsonBuilder().create();
	}

}
