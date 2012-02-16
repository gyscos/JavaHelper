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

    int            finderPort;
    int            providerPort;

    InetAddress    broadcastAddr;

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
        socket = new DatagramSocket(providerPort);

        tell(broadcastAddr);
    }

    public BroadcastProvider start(String name, InetAddress broadcastAddr, int providerPort, int finderPort) {
        System.out.println("Start providing");
        this.name = name;
        this.providerPort = providerPort;
        this.finderPort = finderPort;
        this.broadcastAddr = broadcastAddr;

        running = true;
        thread = new Thread() {
            @Override
            public void run() {
                try {
                    setup();
                    BroadcastProvider.this.run();
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
        DatagramPacket packet = new DatagramPacket(byteName, byteName.length, addr, finderPort);
        socket.send(packet);
    }
}
