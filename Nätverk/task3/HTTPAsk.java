

import java.net.*;
import java.io.*;

public class HTTPAsk {

    public static void main(String[] args) {
        ServerSocket socket = null;
        int portNumber = Integer.parseInt(args[0]);

        // HTTP responses
        String OK = "HTTP/1.1 200 OK\r\n\r\n";
        String BadRequest = "HTTP/1.1 400 Bad Request\r\n\r\n";
        String NotFound = "HTTP/1.1 404 Not Found\r\n\r\n";

        try {
            socket = new ServerSocket(portNumber);
            System.out.println("Server is running on port: " + portNumber);

            while (true) {
                Socket clientSocket = socket.accept();
                InputStream in = clientSocket.getInputStream();
                OutputStream out = clientSocket.getOutputStream();
                byte[] buffer = new byte[512];

                try {
                    System.out.println("Client connected");

                    // Read input and construct the request string
                    StringBuilder buildRequest = new StringBuilder();
                    int bytesRead;
                    while ((bytesRead = in.read(buffer)) != -1) {
                        buildRequest.append(new String(buffer, 0, bytesRead));
                        if (buildRequest.toString().endsWith("\r\n")) {
                            break;
                        }
                    }


                    //Split request
                    String request = buildRequest.toString();
                    String[] parameters = request.split("[?\\&\\=\\ ]");
                    System.out.println(request);
                    
                    // Variables
                    Boolean shutdown = false;
                    Integer limit = null;
                    Integer timeout = 0;
                    String hostname = "";
                    Integer port = null;
                    String output = "";

                    for (int i = 0; i < parameters.length; i++) {
                        if (parameters[i].equals("hostname"))
                            hostname = parameters[++i];
                        else if (parameters[i].equals("shutdown"))
                            shutdown = Boolean.parseBoolean(parameters[++i]);
                        else if (parameters[i].equals("limit"))
                            limit = Integer.parseInt(parameters[++i]);
                        else if (parameters[i].equals("timeout"))
                            timeout = Integer.parseInt(parameters[++i]);
                        else if (parameters[i].equals("port"))
                            port = Integer.parseInt(parameters[++i]);
                        else if (parameters[i].equals("string"))
                            output = parameters[++i];

                    }

                 
                    byte[] dataClient = output.getBytes();
                    System.out.println(request);

                    // response
                    if (!parameters[0].equals("GET") || !request.contains("HTTP/1.1") || port == null || hostname.equals("")) {
                        out.write(BadRequest.getBytes());
                        out.flush();
                        System.out.println(BadRequest);
                    } 
                    else if (!parameters[1].contains("/ask")) {
                        out.write(NotFound.getBytes());
                        out.flush();
                        System.out.println(NotFound);
                    } else {

                        try {
                            TCPClient tcpclient = new TCPClient(shutdown, timeout, limit);
                            byte[] response = tcpclient.askServer(hostname, port, dataClient);

                            System.out.println(OK);
                            out.write(OK.getBytes());
                            out.write(response);
                            out.flush(); 
                        } catch (Exception e) {
                            out.write(NotFound.getBytes());
                            System.out.println(NotFound);
                            out.flush();
                        } finally {
                            clientSocket.close();
                        }
                    }
                    System.out.println("DONE");
                } catch (IOException e) {
                    out.write(BadRequest.getBytes());
                    System.out.println(BadRequest + e);
                    out.flush();
                    clientSocket.close();
                }
            }
        } catch (IOException e) {
            System.exit(0);
        }
    }
}