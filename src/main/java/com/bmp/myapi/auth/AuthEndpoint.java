/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.bmp.myapi.auth;

import com.bmp.myapi.auth.Authorize.AuthParams;
import com.bmp.myapi.model.Data.Access;
import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.HttpHeaders;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

/**
 *
 * @author shareuser
 */
//@Path("auth")
@Consumes(MediaType.APPLICATION_JSON)
public class AuthEndpoint
{

	@GET
	@Path("test")
	public String test()
	{
		return "Auth API and endpoint is active";
//        return Response.status(Response.Status.ACCEPTED).build();
	}

	@POST
	@Path("token")
	public Response getToken(@Context HttpServletRequest req, @Context ServletContext context)
	{
		try
		{
			Authorize auth = new Authorize(req);
			if (auth.getIsBasicAuthorized(req))
			{
				// generate token
				final String token = auth.generateJWT(req);
//				final String json = auth.getAccessToken(token);
				return Response.status(Response.Status.OK).entity(auth.getAccessToken(token)).build();
			}
			else
			{
				return Response.status(Response.Status.UNAUTHORIZED).entity("Basic auth details are not correct").build();
			}
		}
		catch (Exception e)
		{
			
		}
		return Response.serverError().build();
	}


//    @POST
//    @Consumes(MediaType.APPLICATION_JSON)
//    public Response authorizeUser(
//            @Context HttpServletRequest req,
//            @Context HttpHeaders hh)
//    {
//        boolean isAuthorized = Authorize.getIsBasicAuthorized(req);
//        if (isAuthorized) {
////            Message msg = new Message();
////            msg.me
//            String msg = "Was authorized using Basic authentication";
//            return Response.status(Response.Status.OK).entity(msg).build();
//        }
//        return Response.status(Response.Status.UNAUTHORIZED).build();
//    }
//    @POST
//    @Path("form")
//    @Consumes(MediaType.MULTIPART_FORM_DATA)
//    public Response authorizeUserFromForm(
//            @Context HttpServletRequest req,
//            @Context HttpHeaders hh)
//    {
//        AuthParams authParams = new AuthParams();
//        authParams.req = req;
//        authParams.hh = hh;
//        Authorize authorize = new Authorize(authParams);
////        Response result = authorize.get();
//        return result;
//    }
}
