/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.bmp.myapi.auth;

import java.util.List;
import java.util.Map;
import java.util.Optional;
import javax.servlet.http.HttpServletRequest;
import javax.ws.rs.core.HttpHeaders;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.Status;

/**
 *
 * @author shareuser
 */
public class Authorize {
    
    private AuthParams authParams = null;
    
    private Response response = null;
    
    public Authorize(AuthParams authParams)
    {
        this.authParams = authParams;
//        checkAuthorization();
    }
    
    
    
    public Response getResponse()
    {
        return response;
    }
    
    private static final String AUTH_USERNAME = "brandon";
    private static final String AUTH_PASSWORD = "password";

    /**
     * 
     * @return response
     */
    public Response checkAuthorization() 
    {
        // get auth header
        final Optional<String> authHeader = getAuthHeader();
        if (authHeader.isPresent())
        {
            
        }
        return Response.status(Status.UNAUTHORIZED).build(); // was not authorized
    }
    
    private Optional<String> getAuthHeader()
    {
        List<String> authHeaders = authParams.hh.getRequestHeaders().get("Authorization");
        if (!authHeaders.isEmpty())
        {
            // find Basic auth header
        }
        return Optional.empty();
    }
    
    

    /**
     *  Parameters
     */
    public static final class AuthParams
    {
        public HttpServletRequest req = null;
        public HttpHeaders hh = null;
    }
            
}
