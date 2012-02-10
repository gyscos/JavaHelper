package com.helper.network;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;

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

    public abstract void handleCommand(String command, String[] data);

    public boolean handleUrgent(String command, String[] data) {
        if (command.equals("QUIT")) {
            close();
            return true;
        }
        return false;
    }

    /**
     * Allows to say "Hi" on connect.
     */
    public abstract void onConnect();

    public void run() {
        try {
            onConnect();

            while (running) {
                String[] line = in.readLine().split("::");
                if (line.length == 0)
                    // ??
                    continue;

                String command = line[0];
                String[] data = null;
                if (line.length > 1)
                    data = line[1].split(";");

                if (!handleUrgent(command, data))
                    handleCommand(command, data);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public synchronized void send(String command, Object[] data) {
        out.println(command);
    }

    public synchronized void setup(Socket socket) {
        try {

            this.socket = socket;
            out = new PrintWriter(socket.getOutputStream());
            in = new BufferedReader(new InputStreamReader(socket.getInputStream()));

        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
