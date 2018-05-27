package io.milk.jim;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.*;
import java.net.*;

public class JimSession {
    private static final Logger LOG = LoggerFactory.getLogger(JimSession.class);

    private Socket jimSocket;
    private PrintWriter out;
    private BufferedReader in;

    public void startConnection(String ip, int port)
    {
        try {
            jimSocket = new Socket(ip, port);
            out = new PrintWriter(jimSocket.getOutputStream());
            in = new BufferedReader(new InputStreamReader(jimSocket.getInputStream()));
        } catch(IOException e) {
            LOG.debug("Error when initializing client connection", e);
        }
    }

    public String sendMessage(String msg) {
        try {
            out.println(msg);
            return in.readLine();
        } catch(Exception e) {
            return null;
        }
    }

    public void stopConnection() {
        try {
            in.close();
            out.close();
            jimSocket.close();
        } catch(IOException e) {
            LOG.debug("Error when stopping client connection", e);
        }
    }
}
