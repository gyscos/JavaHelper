package com.helper.network;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.LinkedList;

/**
 * Network server that listens on a specific port and accept connections from
 * clients in separate threads.
 * 
 * @author gyscos
 * 
 */
public abstract class NetworkServer {

    /**
     * Worker thread that handles a single connection with a client.
     * 
     * @author gyscos
     * 
     */
    public abstract class NetworkHandler<T extends Enum<T> & NetworkCommand> extends
            NetworkAgent<T> {

        Thread t;

        public NetworkHandler(Class<T> c) {
            super(c);
        }

        @Override
        public synchronized void close() {
            try {
                super.close();
                t.join();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }

        public void start(final Socket socket) {
            t = new Thread() {
                @Override
                public void run() {
                    NetworkHandler.this.setup(socket);
                    NetworkHandler.this.run();
                }
            };
            t.start();
        }
    }

    Thread                               thread;
    ServerSocket                         socket;
    boolean                              running  = false;

    public LinkedList<NetworkHandler<?>> handlers = new LinkedList<NetworkHandler<?>>();

    public void close() {
        try {
            running = false;
            socket.close();
            synchronized (handlers) {
                for (NetworkHandler<?> handler : handlers)
                    handler.close();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public abstract NetworkHandler<?> getHandler();

    /**
     * Tells the server to listen on the given port.
     * 
     * @param port
     */
    public void listen(final int port) {
        running = true;
        thread = new Thread() {
            @Override
            public void run() {
                NetworkServer.this.setup(port);
                NetworkServer.this.run();
            }
        };
        thread.start();
    }

    public void onHandlerDone(NetworkHandler<?> handler) {
        synchronized (handlers) {
            handlers.remove(handler);
        }
    }

    public void run() {
        try {
            while (running) {
                final Socket client = socket.accept();
                NetworkHandler<?> handler = getHandler();
                handler.start(client);
                synchronized (handlers) {
                    handlers.addLast(handler);
                }
            }
        } catch (IOException e) {
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
}
