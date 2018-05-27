package io.milk.jim;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.net.*;
import java.io.*;

public class JimPeer
{
    private static final Logger LOG = LoggerFactory.getLogger(JimPeer.class);

    private ServerSocket peerSocket;

    public void start(int port)
    {
        try {
            peerSocket = new ServerSocket(port);
            while(true)
                new JimSessionHandler(peerSocket.accept()).start();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            stop();
        }
    }

    public void stop()
    {
        try {
            peerSocket.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private static class JimSessionHandler extends Thread
    {
        private Socket clientSocket;
        private PrintWriter out;
        private BufferedReader in;

        public JimSessionHandler(Socket socket)
        {
            clientSocket = socket;
        }

        public void run()
        {
            try {
                out = new PrintWriter(clientSocket.getOutputStream(), true);
                in = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
                String inputLine;
                while((inputLine = in.readLine()) != null) {
                    out.println("ack\n");
                    System.out.println(inputLine);
                }
            } catch(IOException e) {
                LOG.debug(e.getMessage());
            }

        }
    }
}
