package com.helper.network;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.SocketException;

/**
 * Tries to find broadcast providers on the network.
 * 
 * @author gyscos
 * 
 */
public abstract class BroadcastFinder {
    DatagramSocket socket;
    boolean        running = false;
    Thread         thread;

    int            providerPort;
    int            finderPort;

    InetAddress    broadcastAddr;

    byte[]         buffer  = new byte[64];

    public void ask(InetAddress addr) throws IOException {
        DatagramPacket packet = new DatagramPacket(buffer, buffer.length, addr,
                providerPort);
        socket.send(packet);
    }

    /**
     * Finalize the socket.
     */
    void end() {
        socket.close();
    }

    /**
     * Callback function called when a provider is found.
     * Should be overridden by actual implementation.
     * 
     * @param addr
     *            Address of the provider
     * @param name
     *            Name of the provider
     */
    public abstract void onFind(InetAddress addr, String name);

    public void refind() {
        new Thread() {
            @Override
            public void run() {
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
            // Wait for newcomers...
            DatagramPacket packet = new DatagramPacket(buffer, buffer.length);
            socket.receive(packet);

            // We found one !
            String name = new String(packet.getData());
            InetAddress addr = packet.getAddress();
            onFind(addr, name);
        }
    }

    private void setup() throws IOException {
        // System.out.println("Listening on port " + finderPort);
        socket = new DatagramSocket(finderPort);

        // Check for providers already presents.
        ask(broadcastAddr);
    }

    public BroadcastFinder start(InetAddress broadcastAddr, int providerPort,
            int finderPort) {
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
