/**
 * Copyright 2018 Sid Narayan
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package io.milk.jim;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.net.*;
import java.io.*;

/**
 * Represents a jIM peer listening for connections this is the server-like component of a peer
 */
public class JimPeer
{
    private static final Logger LOG = LoggerFactory.getLogger(JimPeer.class);

    private ServerSocket serverSocket;

    /**
     * Start listening for incoming connections on the specified port.
     * @param port The port to listen for connections on
     */
    public void start(int port)
    {
        try {
            serverSocket = new ServerSocket(port);
            while(true)
                new JimSessionHandler(serverSocket.accept()).start();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            stop();
        }
    }

    /**
     * Stop listening for connections and close all resources in use.
     */
    public void stop()
    {
        try {
            serverSocket.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * Handles a session spawned by a peer's request to connect to this peer. Since you can chat
     * with multiple people, this class extends Thread by necessity.
     */
    private static class JimSessionHandler extends Thread
    {
        private Socket peerSocket;
        private PrintWriter out;
        private BufferedReader in;

        /**
         * Constructor for a JimSessionHandler
         * @param socket The socket for communication with the desired peer.
         */
        public JimSessionHandler(Socket socket)
        {
            peerSocket = socket;
        }

        /**
         * Allow messages to be sent and received until the connection is closed.
         */
        public void run()
        {
            try {
                out = new PrintWriter(peerSocket.getOutputStream(), true);
                in = new BufferedReader(new InputStreamReader(peerSocket.getInputStream()));
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
