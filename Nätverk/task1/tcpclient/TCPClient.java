package tcpclient;
import java.net.*;
import java.io.*;

public class TCPClient {

    boolean shutdown;
    Integer timeout;
    Integer limit;
    
    public TCPClient(boolean shutdown, Integer timeout, Integer limit){
        this.shutdown = shutdown;
        this.timeout = timeout;
        this.limit = limit;
    }


    public byte[] askServer(String hostname, int port, byte [] toServerBytes) throws IOException {

        Socket socket = new Socket(hostname, port);
        InputStream bytesIn = socket.getInputStream();
        OutputStream bytesOut = socket.getOutputStream();

        bytesOut.write(toServerBytes);

        ByteArrayOutputStream response = new ByteArrayOutputStream();
        byte[] buffer = new byte[1024];
        int bytes;
        while((bytes = bytesIn.read(buffer)) != -1){
            response.write(buffer, 0, bytes);
        }

        if(shutdown == true)
            socket.shutdownOutput();

        if(timeout != null)
            socket.setSoTimeout(timeout);
        else
            socket.setSoTimeout(0);
        
        int totalBytes;

        while(true){
            if(socket.getInputStream().read() != -1)
                break;
            totalBytes += getInoutStream().read();

            if(limit != null && totalBytes == limit)
                break;
        }
       
        
    

        

        socket.close();
        return response.toByteArray();
    }

    
}
