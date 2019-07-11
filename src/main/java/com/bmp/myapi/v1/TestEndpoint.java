package com.bmp.myapi.v1;

import com.bmp.myapi.auth.Authorize;
import com.bmp.myapi.model.Data.Resource;
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
import javax.ws.rs.core.Response.Status;

/**
 * Root resource (exposed at "test" path)
 */
@Path("v1")
public class TestEndpoint {

    /**
     * Method handling HTTP GET requests. The returned object will be sent to the
     * client as "text/plain" media type.
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
        MultivaluedMap<String, String> headers = hh.getRequestHeaders();
        if (!headers.isEmpty()) {
            Gson gson = new Gson();
            String json = gson.toJson(headers);
            return Response.ok(json).build();
        }
        return Response.serverError().build();
    }

    @GET
    @Path("protected")
    @Produces(MediaType.APPLICATION_JSON)
    public Response getProtectedResource(@Context HttpServletRequest req, @Context HttpHeaders hh) {

        // try (final LeakSafeSqlManager mgr = new LeakSafeSqlManager(ucb);
        // final Authorize auth = Authorize.getIsBasicAuthorized(req)) {
        // final Authorize auth = Authorize.getIsBasicAuthorized(req);

        // if (!Authorize.getIsBasicAuthorized(req))
        if (!isAuthorized(req)) {
            return Response.status(Status.UNAUTHORIZED).entity("Not authorized to access this resource").build();
        }

        // return a protected resource
        Resource resource = new Resource();
        resource.message = "You have access to this protected resource";
        resource.title = "The Wizard of Oz";

        return Response.status(Status.OK).entity(getJson(resource)).build();

        // }
        // MultivaluedMap<String,String> headers = hh.getRequestHeaders();
        // if (!headers.isEmpty()) {
        // Gson gson = new Gson();
        // String json = gson.toJson(headers);
        // return Response.ok(json).build();
        // }
        // return Response.serverError().build();
    }



    private boolean isAuthorized(HttpServletRequest req) {
        return Authorize.getIsBasicAuthorized(req);
    }

    private String getJson(Resource res)
    {
        Gson gson = new Gson();
        String json = gson.toJson(res);
        return gson.toJson(json, res.getClass());
    }

}
