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
        byte[] buffer = new byte[1];
        ByteArrayOutputStream response = new ByteArrayOutputStream();

        try{
            bytesOut.write(toServerBytes);

            if(shutdown == true)
                socket.shutdownOutput();

            if(timeout != null)
                socket.setSoTimeout(timeout);
            else
                socket.setSoTimeout(0);

            int totalBytes = 0;
            int bytes;

            while((bytes = bytesIn.read(buffer)) != -1){

                response.write(buffer, 0, bytes);
                totalBytes += bytes;

                if(limit != null && totalBytes >= limit)
                    break;

            }
        }

        catch(SocketTimeoutException e){
            System.out.println("Timeout" + e);
            return response.toByteArray();
        }
        finally{
            socket.close();
        }

        socket.close();
        return response.toByteArray();
    }

    
}

