/** ***************************************************************************
 * This file contains proprietary information of Access-It Software Ltd.
 * Copying or reproduction without prior written approval is prohibited.
 * Copyright (c) 2004-2013 Access-It Software Ltd.
 *
 **************************************************************************** */
package com.bmp.myapi;

import com.bmp.myapi.auth.AuthEndpoint;
import java.util.HashSet;
import java.util.Set;
import javax.ws.rs.ApplicationPath;
import javax.ws.rs.core.Application;

/**
 *
 * @author Brandon
 */
@ApplicationPath("oauth")
public class MyAuthApplication extends Application
{

	@Override
	public Set<Class<?>> getClasses()
	{
		final Set<Class<?>> resources = new HashSet<>();
		resources.add(AuthEndpoint.class);
		return resources;
	}
	
}
