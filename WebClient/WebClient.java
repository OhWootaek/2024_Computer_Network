import java.io.*;
import java.net.*;

/**
* WebClient class implements a simple web client.

* Its primary responsibilities include:
* 1. Initializing the tcp connection to web server
* 2. send HTTP request and receive HTTP response
*/
public class WebClient {
    public static void main(String[] args) {
        // Set the host, port and resource to send HTTP Request
    	String host = "localhost";
        int port = 8080;
        String resource = "localhost:8888/index.html";
          // Mission 1: Establish a socket connection to Server
        try (
        		//Fill #1, Set TCP socket to HTTP Web Server
        		Socket socket = new Socket(host, port);
        		//Fill #2, create PrintWirter instance with socket’s OutputStream
        		PrintWriter out = new PrintWriter(socket.getOutputStream());
        		//Fill #3, Get input stream from server, and insert it to BufferedReader instance
        		BufferedReader in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
        	){

/**
* Improve your HTTP Client to provide other request Methods(POST, DELETE, …)
* and also improve to handle headers(Content-Type, User-Agent, …)
*/
            // Mission 2: Send HTTP GET Request and Read and display the response
        	// Fill#4, Send HTTP GET request
            out.println("GET " + resource + " HTTP/1.1");
            out.println("Host: " + host);
            out.println("User-Agent: WebClient/1.0");
            out.println();
            out.flush();

            // Mission 3: Read and display the response
            //Fill#5,  Read and display the response
            String responseLine;
            responseLine = in.readLine();
            while (responseLine != null) {
                System.out.println(responseLine);
                responseLine = in.readLine();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
