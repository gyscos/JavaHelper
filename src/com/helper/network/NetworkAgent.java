package com.helper.network;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.net.SocketException;

/**
 * An entity on the network. Can send and receive messages.
 * 
 * @author gyscos
 * 
 */
public class NetworkAgent {
    /**
     * Network backend
     */
    protected Socket         socket;

    // Helper class to ease I/O
    protected PrintWriter    out;
    protected BufferedReader in;

    /**
     * Handler to do the actual work in case of events.
     */
    protected NetworkHandler handler;

    protected boolean        running = false;

    public NetworkAgent() {
    }

    public NetworkAgent(NetworkHandler handler) {
        setHandler(handler);
    }

    public synchronized void close() {
        try {
            running = false;

            out.close();
            in.close();
            socket.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public String getIp() {
        if (socket != null)
            return socket.getInetAddress().getHostAddress();
        else
            return "127.0.0.1";
    }

    /**
     * React to a network connection
     */
    protected void onConnect() {
        if (handler != null)
            handler.onConnect(getIp());
    }

    /**
     * React to a network disconnection
     */
    protected void onDisconnect() {
        if (handler != null)
            handler.onDisconnect();
    }

    /**
     * Assume setup() has been called. Starts listening to events.
     * This will block until the connection is lost. Run this in separate
     * thread.
     */
    public void run() {
        try {
            onConnect();

            while (running) {
                running &= handler.readMessage(in);
            }
            System.out.println("Clean disconnection.");
            close();
        } catch (SocketException e) {
            System.out.println("Socket Exception !");
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            onDisconnect();
        }
    }

    /**
     * Send a message
     * 
     * @param content
     */
    public void sendMessage(String content) {
        out.print(content);
    }

    public void setHandler(NetworkHandler handler) {
        this.handler = handler;
        handler.setAgent(this);
    }

    /**
     * Setup the network connection used by this agent.
     * 
     * @param socket
     *            Already open network socket used by the agent.
     */
    public synchronized void setup(Socket socket) {
        try {
            this.socket = socket;

            // Create our nice utils
            out = new PrintWriter(socket.getOutputStream(), true);
            in = new BufferedReader(new InputStreamReader(
                    socket.getInputStream()));
        } catch (IOException e) {
            e.printStackTrace();
        }
        // And turn on the green light.
        running = true;
    }

}
