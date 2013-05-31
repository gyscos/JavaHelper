package com.helper.network;

import java.io.IOException;
import java.net.Socket;

/**
 * A network client is a stand-alone network agent that can connect to a network
 * server.
 * 
 * @author gyscos
 * 
 */
public class NetworkClient extends NetworkAgent {

    Thread thread;

    public NetworkClient() {
        super();
    }

    public NetworkClient(NetworkHandler handler) {
        super(handler);
    }

    /**
     * Connect to a remote server, and run the conversation in a separate
     * thread. This call is non-blocking.
     * 
     * @param server
     * @param port
     */
    public void connect(final String server, final int port) {
        running = true;

        thread = new Thread() {
            @Override
            public void run() {
                NetworkClient.this.setup(server, port);
                NetworkClient.this.run();
            }
        };
        thread.start();
    }

    /**
     * Create the socket. Internal use.
     * 
     * @param server
     * @param port
     */
    void setup(String server, int port) {
        try {
            System.out.println("Connecting to " + server + " on port " + port);
            socket = new Socket(server, port);
            // socket.getOutputStream().write("Test, test !\n".getBytes());

            super.setup(socket);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
