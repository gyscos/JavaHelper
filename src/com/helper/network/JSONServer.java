package com.helper.network;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.LinkedList;

public abstract class JSONServer {
    public abstract JSONHandler getHandler();

    public static class ServerAgent {
        JSONAgent agent;
        Thread t;

        public ServerAgent(JSONHandler handler) {
            agent = new JSONAgent(handler);
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

        public void stop() {
            try {
                agent.close();
                t.join();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

    ServerSocket socket;
    Thread thread;
    boolean running = false;
    public LinkedList<ServerAgent> agents  = new LinkedList<ServerAgent>();

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
            synchronized (agents) {
                for (ServerAgent agent : agents)
                    agent.stop();
            }
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
                ServerAgent agent = new ServerAgent(getHandler());
                agent.start(client);
                agents.addLast(agent);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
