package com.bmp.myapi.model;

public class Data
{
		// this a primary class
	public final static class Access
	{
		public String access_token = null;
		public String token_type = null;
		public Integer expires_in = null;
		public String scope = null;

		@Override
		public String toString()
		{
			return "Access:\r\n" + access_token + "\r\n" + token_type + "\r\n" + (expires_in == null ? "null" : expires_in.toString()) + "\r\n" + scope;
		}
	}
	
    public static class Message
    {
        public String message = "";
    }

    public static class Resource extends Message
    {
        public String title = "";
    }

    public static class User extends Message
    {

    }


}