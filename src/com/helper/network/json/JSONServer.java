package com.helper.network.json;

import java.net.SocketException;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.LinkedList;

public abstract class JSONServer {
    public abstract JSONHandler getHandler();

    ServerSocket socket;
    Thread thread;
    boolean running = false;

    public boolean isRunning() {
        return running;
    }

    public void start(final int port) {
        running = true;
        thread = new Thread() {
            @Override
            public void run() {
                JSONServer.this.setup(port);
                JSONServer.this.run();
            }
        };
        thread.start();
    }
    public void close() {
        try {
            running = false;
            socket.close();
            thread.join();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    public void setup(int port) {
        try {
            socket = new ServerSocket(port);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void run() {
        try {
            while (running) {
                final Socket client = socket.accept();
                JSONAgent.setup(client).answer(getHandler(), true);
            }
        } catch(SocketException e) {
            // Socket closed. All is well.
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
