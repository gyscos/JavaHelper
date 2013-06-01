package com.helper.network;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.PrintWriter;

/**
 * Slave class used by a network agent to do the actual work.
 * 
 * @author gyscos
 * 
 */
public abstract class NetworkHandler {

    protected NetworkAgent agent;

    public NetworkHandler() {
    }

    public NetworkHandler(NetworkAgent agent) {
        setAgent(agent);
    }

    /**
     * Allows to say "Hi" on connect.
     */
    public void onConnect(String ip) {
    }

    /**
     * Called when the connection was lost.
     */
    public void onDisconnect() {
    }

    /**
     * Read and process an incoming network message.
     * 
     * @param in
     *            Reader used to read the message.
     * @param out
     *            Writer used to answer to the message.
     * @return TRUE if no error occurred.
     */
    public abstract boolean readMessage(BufferedReader in, PrintWriter out)
            throws IOException;

    public void setAgent(NetworkAgent agent) {
        this.agent = agent;
    }
}
