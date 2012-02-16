package com.helper.network;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.HashMap;

import com.helper.StringHelper;

public abstract class NetworkAgent<T extends Enum<T> & NetworkCommand> {
    public final static String mainDelim = "::";

    public final static String secDelim  = ";";
    Socket                     socket;
    PrintWriter                out;
    BufferedReader             in;

    boolean                    running   = false;

    HashMap<String, T>         commands  = new HashMap<String, T>();

    public NetworkAgent(Class<T> c) {
        for (T t : c.getEnumConstants()) {
            addCommand(t.getName(), t);
        }
    }

    public void addCommand(String command, T cmdId) {
        commands.put(command, cmdId);
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

    public abstract void handleCommand(T command, String[] data);

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
                String line = in.readLine();
                // System.out.println("Received : " + line);
                if (line == null) {
                    running = false;
                    break;
                }

                String[] list = line.split(mainDelim);
                if (list.length == 0)
                    // ??
                    continue;

                String command = list[0];
                String[] data = null;
                if (list.length > 1)
                    data = list[1].split(secDelim);

                if (!handleUrgent(command, data))
                    handleCommand(commands.get(command), data);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private synchronized void send(String msg) {
        out.println(msg);
    }

    public synchronized void send(String command, Object... data) {
        send(command + mainDelim + StringHelper.merge(secDelim, data));
    }

    public synchronized void send(T command, Object... data) {
        send(command.getName(), data);
    }

    public synchronized void setup(Socket socket) {
        try {
            this.socket = socket;
            out = new PrintWriter(socket.getOutputStream(), true);
            in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
        } catch (IOException e) {
            e.printStackTrace();
        }
        running = true;
    }
}
