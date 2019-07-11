/** ***************************************************************************
 * This file contains proprietary information of Access-It Software Ltd.
 * Copying or reproduction without prior written approval is prohibited.
 * Copyright (c) 2004-2013 Access-It Software Ltd.
 *
 **************************************************************************** */
package com.bmp.myapi;

import com.bmp.myapi.v1.TestEndpoint;
import java.util.HashSet;
import java.util.Set;
import javax.ws.rs.ApplicationPath;
import javax.ws.rs.core.Application;

/**
 *
 * @author Brandon
 */
@ApplicationPath("api")
public class MyAPIApplication extends Application
{
	@Override
	public Set<Class<?>> getClasses()
	{
		final Set<Class<?>> resources = new HashSet<>();
		resources.add(TestEndpoint.class);
		return resources;
	}
}
