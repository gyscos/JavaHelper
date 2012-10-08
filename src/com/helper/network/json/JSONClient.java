package com.helper.network.json;

import java.io.IOException;
import java.net.Socket;

public class JSONClient extends JSONAgent {

    Thread thread;

    public JSONClient(JSONHandler handler) {
        super(handler);
    }


    public void connect(final String server, final int port) {
        thread = new Thread() {
            @Override
            public void run() {
                JSONClient.this.setup(server, port);
                JSONClient.this.run();
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
