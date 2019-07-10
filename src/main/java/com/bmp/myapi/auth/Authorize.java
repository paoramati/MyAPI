/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.bmp.myapi.auth;

import com.google.gson.Gson;
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
public class Authorize {

    private AuthParams authParams = null;

    private Response response = null;
    private HttpServletRequest req;

    public Authorize(AuthParams authParams) {
        this.authParams = authParams;
//        checkAuthorization();
    }

    public Authorize(HttpServletRequest req) {
        this.req = req;
    }

    public Response getResponse() {
        return response;
    }

    private static final String AUTH_USERNAME = "brandon";
    private static final String AUTH_PASSWORD = "password";

    /**
     *
     * @param req
     * @return response
     */
//    public Response checkAuthorization() {
//        // get auth header
//        final Optional<String> authValue = getAuthCredentials();
//        if (authValue.isPresent()) {
//            String value = authValue.get();
//            byte[] bytes = Base64.getDecoder().decode(value);
//            String auth = new String(bytes);
////            String username = auth.split(":")[0];
////            String password = auth.split(":")[1];
////            Gson gson = new Gson();
////            String json = gson.toJson(auth);
////            return Response.ok(json, MediaType.APPLICATION_JSON).build();
//        }
//        return Response.status(Status.UNAUTHORIZED).build(); // was not authorized
//    }
    public static boolean getIsAuthorized(HttpServletRequest req) {
        Authorize authorize = new Authorize(req);
        // get auth header
        final Optional<String[]> authValue = authorize.getAuthCredentials();
        if (authValue.isPresent()) {
            boolean result = authorize.checkUsernamePassword(AUTH_USERNAME, AUTH_PASSWORD);
            return result;
        }
//        AuthParams params = new AuthParams();
//        params.req = req;
//        Authorize authorize = new Authorize(params);

        return false;
    }

    private Optional<String[]> getAuthCredentials() {
        List<String> authHeaders = Collections.list(req.getHeaders("Authorization"));
        if (!authHeaders.isEmpty()) {
            // find Basic auth header
            for (String authHeader : authHeaders) {
                if ("Basic".equals(authHeader.substring(0, 5))) {
                    byte[] bytes = Base64.getDecoder().decode(authHeader.substring("Basic".length()).trim());
                    String credentials = new String(bytes, StandardCharsets.UTF_8);
                    return Optional.of(credentials.split(":"));
                }
            }
        }
        return Optional.empty();
    }

    private boolean checkUsernamePassword(final String username, final String password) {
        Predicate<Credentials> credentialsMatch = c -> 
            c.getUsername().equals(username) && c.getPassword().equals(password);
        boolean result = users.stream().anyMatch(credentialsMatch);
        return result;
    }
    
    final static Set<Credentials> users = new HashSet();
    static {
        users.add(new Credentials("brandon", "password"));
    }

//    private Optional<String> getAuthHeaderValue2() {
//        List<String> authHeaders = authParams.hh.getRequestHeaders().get("Authorization");
//        if (!authHeaders.isEmpty()) {
//            // find Basic auth header
//            for (String authHeader : authHeaders) {
//                String key = authHeader.substring(0, 5);
//                if ("Basic".equals(key)) {
//                    return Optional.of(authHeader.substring(6)); // trim space
//                }
//            }
//            return Optional.of(authHeaders.get(0));
//        }
//        return Optional.empty();
//    }


    public static final class Credentials {

        private String username = null;
        private String password = null;

        public Credentials(final String username, final String password) {
            this.username = username;
            this.password = password;
        }

        public String getUsername() {
            return username;
        }

        public String getPassword() {
            return password;
        }        
    }

    /**
     * Parameters
     */
    public static final class AuthParams {

        public HttpServletRequest req = null;
        public HttpHeaders hh = null;
    }

}
