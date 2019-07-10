package com.bmp.myapi;

import com.google.gson.Gson;
import javax.servlet.http.HttpServletRequest;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.HttpHeaders;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.MultivaluedMap;
import javax.ws.rs.core.Response;

/**
 * Root resource (exposed at "test" path)
 */
@Path("test")
public class TestEndpoint {

    /**
     * Method handling HTTP GET requests. The returned object will be sent
     * to the client as "text/plain" media type.
     *
     * @return String that will be returned as a text/plain response.
     */
    @GET
    @Produces(MediaType.TEXT_PLAIN)
    public String getIt() {
        return "Test endpoint is working!";
    }
    
    @GET
    @Path("headers")
    @Produces(MediaType.APPLICATION_JSON)
    public Response getHeaders(@Context HttpServletRequest req, @Context HttpHeaders hh) {
        MultivaluedMap<String,String> headers = hh.getRequestHeaders();
        if (!headers.isEmpty()) {
            Gson gson = new Gson();
            String json = gson.toJson(headers);
            return Response.ok(json).build();
        }
        return Response.serverError().build();
    }
    
}
