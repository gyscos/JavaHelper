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
    public static class ServerAgent {

        NetworkAgent agent;

        Thread       t;

        public ServerAgent(NetworkHandler handler) {
            agent = new NetworkAgent(handler);
        }

        public void start(final Socket socket) {
            t = new Thread() {
                @Override
                public void run() {
                    agent.setup(socket);
                    agent.run();
                }
            };
            t.start();
        }

        public synchronized void stop() {
            try {
                agent.close();
                t.join();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

    Thread                         thread;
    ServerSocket                   socket;
    boolean                        running = false;

    public LinkedList<ServerAgent> agents  = new LinkedList<ServerAgent>();

    public NetworkServer() {
    }

    public void close() {
        try {
            running = false;
            socket.close();
            synchronized (agents) {
                for (ServerAgent agent : agents)
                    agent.stop();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public abstract NetworkHandler getHandler();

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

    public void onHandlerDone(ServerAgent handler) {
        synchronized (agents) {
            agents.remove(handler);
        }
    }

    public void run() {
        try {
            while (running) {
                final Socket client = socket.accept();
                NetworkHandler handler = getHandler();
                ServerAgent agent = new ServerAgent(handler);

                agent.start(client);
                synchronized (agents) {
                    agents.addLast(agent);
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
