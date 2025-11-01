/*************************************
 *  Filename:  HTTPInteraction.java
 **+***+******************************/

import java.net.*;
import java.io.*;
import java.util.*;

/**
 * Class for downloading one object from a Http server.
 *
 */
public class HTTPInteraction {
	private String host;
	private String path;
	private String requestMessage;
	
		
	private static final int HTTP_PORT = 80;
	private static final String CRLF = "\r\n";
	private static final int BUF_SIZE = 4096; 
	private static final int MAX_OBJECT_SIZE = 102400;

 	/** Create a HTTPInteraction object. ***/
	public HTTPInteraction(String url) {
		
		/* Split "URL" into "host name" and "path name", and
		 * set host and path class variables. 
		 * if the URL is only a host name, use "/" as path 
		 */		
		int index = url.indexOf("/");
		if (index != -1) {
			host = url.substring(0, index);
			path = url.substring(index);
		}
		else {
			host = url;
			path = "/";
		}
		System.out.println(path);

	
		/* Construct requestMessage, add a header line so that
		 * server closes connection after one response. */		
		requestMessage = "GET " + path + " HTTP/1.1\r\n";
		requestMessage += "Host: " + host + "\r\n";
		requestMessage += "Connection: close\r\n";
		requestMessage += "\r\n";
		
		return;
	}	
	
	
	/* Send Http request, parse response and return requested object 
	 * as a String (if no errors), 
	 * otherwise return meaningful error message. 
	 * Don't catch Exceptions. EmailClient will handle them. */		
	public String send() throws IOException {
		
		/* buffer to read object in 4kB chunks */
		char[] buf = new char[BUF_SIZE];

		/* Maximum size of object is 100kB, which should be enough for most objects. 
		 * Change constant if you need more. */		
		char[] body = new char[MAX_OBJECT_SIZE];
		
		String statusLine="";	// status line
		int status;		// status code
		String headers="";	// headers
		int bodyLength=-1;	// lenghth of body
				
		String[] tmp;
		
		/* The socket to the server */
		Socket connection;
		
		/* Streams for reading from and writing to socket */
		BufferedReader fromServer;
		DataOutputStream toServer;
		
		System.out.println("Connecting server: " + host+CRLF);
		
		/* Connect to http server on port 80.
		 * Assign input and output streams to connection. */		
		connection = new Socket(host, HTTP_PORT);
		fromServer = new BufferedReader(new InputStreamReader(connection.getInputStream()));
		toServer = new DataOutputStream(connection.getOutputStream());
		
		System.out.println("Send request:\n" + requestMessage);


		/* Send requestMessage to http server */
		toServer.writeBytes(requestMessage);
		
		/* Read the status line from response message */
		statusLine = fromServer.readLine();
		System.out.println("Status Line:\n"+statusLine+CRLF);
		/* Extract status code from status line. If status code is not 200,
		 * close connection and return an error message. 
		 * Do NOT throw an exception */		
		status = Integer.valueOf(statusLine.substring(9, 12));
		if (status != 200) {
			connection.close();
			return "error";
		}

		/* Read header lines from response message, convert to a string, 
 		 * and assign to "headers" variable. 
		 * Recall that an empty line indicates end of headers.
		 * Extract length  from "Content-Length:" (or "Content-length:") 
		 * header line, if present, and assign to "bodyLength" variable. 
		requires 10 lines of code*/
		String line = fromServer.readLine();
		while ((line != null) && (line != "")) {
			if(line.startsWith("Content-Length:") || line.startsWith("Content-length:")) {
				String length = line.substring(16);
				bodyLength = Integer.valueOf(length); 
			}
			headers += line + "\n";
			line = fromServer.readLine();
		}
        
		System.out.println("Headers:\n"+headers+CRLF);
	

		/* If object is larger than MAX_OBJECT_SIZE, close the connection and 
		 * return meaningful message. */
		if (MAX_OBJECT_SIZE > bodyLength) {
			connection.close();
			return("object too large: " + bodyLength);
		}
					    
		/* Read the body in chunks of BUF_SIZE using buf[] and copy the chunk
		 * into body[]. Stop when either we have
		 * read Content-Length bytes or when the connection is
		 * closed (when there is no Content-Length in the response). 
		 * Use one of the read() methods of BufferedReader here, NOT readLine().
		 * Make sure not to read more than MAX_OBJECT_SIZE characters.
		 */				
		int bytesRead = 0;
		int i = 0;
		int k = 0;
		while ((bytesRead <= MAX_OBJECT_SIZE) && (!connection.isClosed())) {
			buf[i] = (char) fromServer.read();
			bytesRead += 1;
			i += 1;
			if ((i == BUF_SIZE) || (bytesRead == MAX_OBJECT_SIZE)) {
				for (int j = 0; k*BUF_SIZE + j < bytesRead; j++) {
					body[k*BUF_SIZE + j] = buf[j];
				}
				buf = new char[BUF_SIZE];
				k+=1;
			}
		}
		/* Fill in */   // Requires 10-20 lines of code

		/* At this points body[] should hold to body of the downloaded object and 
		 * bytesRead should hold the number of bytes read from the BufferedReader
		 */
		
		/* Close connection and return object as String. */
		System.out.println("Done reading file. Closing connection.");
		connection.close();
		return(new String(body, 0, bytesRead));
	}
}
