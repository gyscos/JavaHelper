package com.helper.network;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.SocketException;

public class BroadcastProvider {
    DatagramSocket socket;
    boolean        running = false;
    Thread         thread;
    String         name;
    int            port;
    String         broadcastIp;

    byte[]         buffer  = new byte[64];

    public void end() {
        socket.close();
    }

    public void run() throws IOException {
        while (running) {
            DatagramPacket packet = new DatagramPacket(buffer, buffer.length);
            socket.receive(packet);

            InetAddress addr = packet.getAddress();

            // Send him our name.
            tell(addr);
        }
    }

    public void setup() throws IOException {
        socket = new DatagramSocket(port);

        InetAddress addr = InetAddress.getByName(broadcastIp);
        tell(addr);
    }

    public BroadcastProvider start(String name, String broadcastIp, int port) {
        this.name = name;
        this.port = port;
        this.broadcastIp = broadcastIp;

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
            running = false;
            socket.close();
            thread.join();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    private void tell(InetAddress addr) throws IOException {
        // Send a broadcast
        byte[] byteName = name.getBytes();
        DatagramPacket packet = new DatagramPacket(byteName, byteName.length, addr, port);
        socket.send(packet);
    }
}
