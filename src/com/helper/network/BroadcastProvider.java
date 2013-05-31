package com.helper.network;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.SocketException;

/**
 * Provides a service on a network. Answer to broadcast finders.
 * 
 * @author gyscos
 * 
 */
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

    /**
     * Wait for incoming requests, and answer them.
     * 
     * @throws IOException
     */
    void run() throws IOException {
        while (running) {
            DatagramPacket packet = new DatagramPacket(buffer, buffer.length);
            socket.receive(packet);

            InetAddress addr = packet.getAddress();

            // Send him our name.
            tell(addr);
        }
    }

    void setup() throws IOException {
        socket = new DatagramSocket(providerPort);

        // We introduce ourselves
        tell(broadcastAddr);
    }

    /**
     * Main function. Start providing a service.
     * 
     * @param name
     *            Identifier for this provider.
     * @param broadcastAddr
     *            Interface to use for the provider.
     * @param providerPort
     *            Port to listen on for finder requests.
     * @param finderPort
     *            Port to send notifications to. Finders will listen to this
     *            port.
     * @return The broadcast provider itself.
     */
    public BroadcastProvider start(String name, InetAddress broadcastAddr,
            int providerPort, int finderPort) {
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

    /**
     * Stops providing. Returns when the socket is effectively closed.
     */
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
        DatagramPacket packet = new DatagramPacket(byteName, byteName.length,
                addr, finderPort);
        socket.send(packet);
    }
}
