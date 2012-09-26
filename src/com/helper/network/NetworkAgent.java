package com.helper.network;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.net.SocketException;

public abstract class NetworkAgent {
    Socket         socket;
    PrintWriter    out;
    BufferedReader in;

    boolean        running = false;

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

    protected abstract void handleMessage(String line);

    protected abstract void onConnect();

    protected abstract void onDisconnect();

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

    private synchronized void send(String msg) {
        out.println(msg);
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
