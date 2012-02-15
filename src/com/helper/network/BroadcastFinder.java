package com.helper.network;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.SocketException;

public abstract class BroadcastFinder {
    DatagramSocket socket;
    boolean        running = false;
    Thread         thread;
    int            port;
    String         broadcastIp;

    byte[]         buffer  = new byte[64];

    public void ask(InetAddress addr) throws IOException {
        DatagramPacket packet = new DatagramPacket(buffer, buffer.length, addr, port);
        socket.send(packet);
    }

    public void end() {
        socket.close();
    }

    public abstract void onFind(InetAddress addr, String name);

    public void refind() {
        new Thread() {
            @Override
            public void run() {
                InetAddress addr;
                try {
                    addr = InetAddress.getByName(broadcastIp);
                    ask(addr);
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }.start();
    }

    public void run() throws IOException {
        while (running) {
            DatagramPacket packet = new DatagramPacket(buffer, buffer.length);
            socket.receive(packet);

            String name = new String(packet.getData());
            InetAddress addr = packet.getAddress();
            onFind(addr, name);
        }
    }

    public void setup() throws IOException {
        socket = new DatagramSocket(port);

        InetAddress addr = InetAddress.getByName(broadcastIp);
        ask(addr);
    }

    public BroadcastFinder start(String broadcastIp, int port) {
        this.broadcastIp = broadcastIp;
        this.port = port;

        running = true;
        thread = new Thread() {
            @Override
            public void run() {
                try {
                    setup();
                    run();
                    end();
                } catch (SocketException e) {
                    e.printStackTrace();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        };
        thread.start();
        return this;
    }

    public void stop() {
        try {
            if (!running)
                return;

            running = false;
            socket.close();
            thread.join();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}
