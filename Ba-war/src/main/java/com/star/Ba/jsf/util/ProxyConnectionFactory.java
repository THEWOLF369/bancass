package com.star.Ba.jsf.util;

import java.io.IOException;
import java.net.Authenticator;
import java.net.HttpURLConnection;
import java.net.InetSocketAddress;
import java.net.PasswordAuthentication;
import java.net.Proxy;
import java.net.URL;

import com.sun.jersey.client.urlconnection.HttpURLConnectionFactory;

public  class ProxyConnectionFactory implements HttpURLConnectionFactory {

    Proxy proxy;

    private void initializeProxy() {
    	
		Authenticator.setDefault(
				  new Authenticator() {
				    public PasswordAuthentication getPasswordAuthentication() {
				      return new PasswordAuthentication("houssema", "Azeazeaze123123123***".toCharArray());
				    }
				  }
				);
        proxy = new Proxy(Proxy.Type.HTTP, new InetSocketAddress("172.31.70.254", 3128));
    }



	@Override
	public HttpURLConnection getHttpURLConnection(URL url) throws IOException {
		 initializeProxy();
	        return (HttpURLConnection) url.openConnection(proxy);
	}
}

