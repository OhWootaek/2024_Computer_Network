package main;
/**
 * HttpRequest - HTTP request container and parser
 *
 * $Id: HttpRequest.java,v 1.2 2003/11/26 18:11:53 kangasha Exp $
 *
 */

import java.io.*;
import java.net.*;
import java.util.*;

public class HttpRequest {
    /** Help variables */
    final static String CRLF = "\r\n";
    final static int HTTP_PORT = 80;
    /** Store the request parameters */
    String method;
    String URI;
    String version;
    String headers = "";
    /** Server and port */
    private String host;
    private int port;

    /** Create HttpRequest by reading it from the client socket */
    public HttpRequest(BufferedReader from) {
	String firstLine = "";
	try {
	    firstLine = from.readLine();
	    System.out.println(firstLine);
	} catch (IOException e) {
	    System.out.println("Error reading request line: " + e);
	}

	String[] tmp = firstLine.split(" ");
	method = tmp[0];
	URI = tmp[1].substring(0);
	//System.out.println("URI is: " + URI);
	version = tmp[2];
	URI = URI.substring(7);
	System.out.println("URI is: " + URI)
	
	if (URI.contains(":")) {
        // Extract host and port if present
        int colonIndex = URI.indexOf(":");
        int slashIndex = URI.indexOf("/", colonIndex);

        if (slashIndex == -1) {
            slashIndex = URI.length();
        }

        host = URI.substring(0, colonIndex); // Extract host
        port = Integer.parseInt(URI.substring(colonIndex + 1, slashIndex)); // Extract port
        URI = URI.substring(slashIndex); // Extract path
    } else {
        // Default to HTTP port if no port is specified
        int slashIndex = URI.indexOf("/");
        if (slashIndex == -1) {
            slashIndex = URI.length();
        }

        host = URI.substring(0, slashIndex); // Extract host
        port = HTTP_PORT; // Default port
        URI = URI.substring(slashIndex); // Extract path
    }
	//host = URI;
	//port = HTTP_PORT;
	
	//System.out.println("URI is: " + URI);

	if (!method.equals("GET")) {
	    System.out.println("Error: Method not GET");
	}
	try {
	    String line = from.readLine();
	    while (line.length() != 0) {
	    	System.out.println(line);
	    	/*if (line.startsWith("Host:")) {
	    		headers += "Host: " + host + CRLF;
	    	}
	    	else {
	    		headers += line + CRLF;
	    	}*/
		/* We need to find host header to know which server to
		 * contact in case the request URI is not complete. */
		/*if (line.startsWith("Host:")) {
		    tmp = line.split(" ");
		    if (tmp[1].indexOf(':') > 0) {
			String[] tmp2 = tmp[1].split(":");
			host = tmp2[0];
			port = Integer.parseInt(tmp2[1]);
		    } else {
			host = tmp[1];
			port = HTTP_PORT;
		    }
		}*/
		line = from.readLine();
	    }
	    System.out.println();
	} catch (IOException e) {
	    System.out.println("Error reading from socket: " + e);
	    return;
	}
	System.out.println("Host to contact is: " + host + " at port " + port);
	System.out.println();
    }

    /** Return host for which this request is intended */
    public String getHost() {
	return host;
    }

    /** Return port for server */
    public int getPort() {
	return port;
    }

    /**
     * Convert request into a string for easy re-sending.
     */
    public String toString() {
	String req = "";

	req = method + " " + URI + " " + version + CRLF;
	//req = method + " " + URI + " " + version + CRLF;
	req += headers;
	/* This proxy does not support persistent connections */
	req += "Connection: close" + CRLF;
	req += CRLF;
	
	return req;
    }
}
