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

import java.io.*;
import java.net.*;

/**
 * Represents an IM session started by one peer attempting to communicate with another.
 */
public class JimSession {
    private static final Logger LOG = LoggerFactory.getLogger(JimSession.class);

    private Socket jimSocket;
    private PrintWriter out;
    private BufferedReader in;

    /**
     * Attempts to start a connection with the peer specified by the passed IP address and port.
     * @param ip The IP address of the target peer.
     * @param port the port number that the target peer is listening on.
     */
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

    /**
     * Attempts to send a message to a connected peer.
     * @param msg The message to send to the connected peer.
     * @return The connected peer's response.
     */
    public String sendMessage(String msg) {
        try {
            out.println(msg);
            return in.readLine();
        } catch(Exception e) {
            return null;
        }
    }

    /**
     * Attempts to close all resources in use by this peer and end the connection.
     */
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
