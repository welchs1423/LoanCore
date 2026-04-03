package com.finance.service;

import java.io.InputStream;
import java.io.OutputStream;
import java.net.Socket;
import org.springframework.stereotype.Service;

@Service
public class TcpSocketClient {

    public String sendAndReceive(String ip, int port, String message) {
        try (Socket socket = new Socket(ip, port);
             OutputStream os = socket.getOutputStream();
             InputStream is = socket.getInputStream()) {
             
            os.write(message.getBytes("UTF-8"));
            
            byte[] buffer = new byte[1024];
            int bytesRead = is.read(buffer);
            
            if (bytesRead > 0) {
                return new String(buffer, 0, bytesRead, "UTF-8");
            }
            return "";
            
        } catch (Exception e) {
            return "ERROR";
        }
    }
}