package com.helper.network;

import java.io.IOException;
import java.net.Socket;

/**
 * 
 * @author gyscos
 * 
 */
public abstract class NetworkClient extends NetworkAgent {

    Thread thread;

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
            socket = new Socket(server, port);

            super.setup(socket);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
