package com.helper.network;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.net.SocketException;

public class NetworkAgent {
    protected Socket         socket;
    protected PrintWriter    out;
    protected BufferedReader in;

    protected NetworkHandler handler;

    protected boolean        running = false;

    public NetworkAgent() {
    }

    public NetworkAgent(NetworkHandler handler) {
        setHandler(handler);
    }

    public synchronized void close() {
        try {
            running = false;

            out.close();
            in.close();
            socket.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public String getIp() {
        if (socket != null)
            return socket.getInetAddress().getHostAddress();
        else
            return "127.0.0.1";
    }

    protected void handleMessage(String line) {
        if (handler != null)
            handler.handleMessage(line);
    }

    protected void onConnect() {
        if (handler != null)
            handler.onConnect(getIp());
    }

    protected void onDisconnect() {
        if (handler != null)
            handler.onDisconnect();
    }

    public void run() {
        try {
            onConnect();

            while (running) {
                String line = in.readLine();
                // System.out.println("Received : " + line);
                if (line == null) {
                    running = false;
                    break;
                }

                handleMessage(line);

            }
            System.out.println("Clean disconnection.");
            close();
        } catch (SocketException e) {
            System.out.println("Socket Exception !");
        } catch (IOException e) {
            e.printStackTrace();
        }
        onDisconnect();
    }

    public synchronized void send(String msg) {
        out.println(msg);
    }

    public void setHandler(NetworkHandler handler) {
        this.handler = handler;
        handler.setAgent(this);
    }

    public synchronized void setup(Socket socket) {
        try {
            this.socket = socket;
            out = new PrintWriter(socket.getOutputStream(), true);
            in = new BufferedReader(new InputStreamReader(
                    socket.getInputStream()));
        } catch (IOException e) {
            e.printStackTrace();
        }
        running = true;
    }

}
