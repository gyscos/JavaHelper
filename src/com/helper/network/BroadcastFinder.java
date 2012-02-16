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

    int            providerPort;
    int            finderPort;

    InetAddress    broadcastAddr;

    byte[]         buffer  = new byte[64];

    public void ask(InetAddress addr) throws IOException {
        DatagramPacket packet = new DatagramPacket(buffer, buffer.length, addr, providerPort);
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
                    ask(broadcastAddr);
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }.start();
    }

    private void run() throws IOException {
        while (running) {
            DatagramPacket packet = new DatagramPacket(buffer, buffer.length);
            socket.receive(packet);

            String name = new String(packet.getData());
            InetAddress addr = packet.getAddress();
            onFind(addr, name);
        }
    }

    private void setup() throws IOException {
        // System.out.println("Listening on port " + finderPort);
        socket = new DatagramSocket(finderPort);

        ask(broadcastAddr);
    }

    public BroadcastFinder start(InetAddress broadcastAddr, int providerPort, int finderPort) {
        System.out.println("Broadcasting on " + broadcastAddr);
        this.broadcastAddr = broadcastAddr;
        this.providerPort = providerPort;
        this.finderPort = finderPort;

        running = true;
        thread = new Thread() {
            @Override
            public void run() {
                try {
                    setup();
                    BroadcastFinder.this.run();
                    end();
                } catch (SocketException e) {
                    // e.printStackTrace();
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
