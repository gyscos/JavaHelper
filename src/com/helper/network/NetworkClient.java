package com.helper.network;

import java.io.IOException;
import java.net.Socket;


/**
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

    public void setup(String server, int port) {
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
