package com.helper.network;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.net.SocketException;
import java.util.HashMap;

import com.helper.StringHelper;

public class NetworkAgent<T extends Enum<T> & NetworkCommand> {
    public final static String mainDelim = "::";

    public final static String secDelim  = ";";
    Socket                     socket;
    PrintWriter                out;
    BufferedReader             in;

    boolean                    running   = false;

    HashMap<String, T>         commands  = new HashMap<String, T>();

    NetworkHandler<T>          handler;

    public NetworkAgent(Class<T> c) {
        for (T t : c.getEnumConstants()) {
            addCommand(t.getName(), t);
        }
    }

    public NetworkAgent(Class<T> c, NetworkHandler<T> handler) {
        this(c);
        setHandler(handler);
    }

    public void addCommand(String command, T cmdId) {
        commands.put(command, cmdId);
    }

    public void close() {
        System.out.println("Network agent closing unsynced...");
        try {
            running = false;

            out.close();
            in.close();
            socket.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
        System.out.println("Network agent closed unsynced...");
    }

    public String getIp() {
        return socket.getInetAddress().getHostAddress();
    }

    protected boolean handleCommand(String command, String[] data) {
        return handleCommand(commands.get(command), data);
    }

    protected boolean handleCommand(T command, String[] data) {
        return handler.handleCommand(command, data);
    }

    public boolean handleUrgent(String command, String[] data) {
        if (command.equals("QUIT")) {
            System.out.println("Closing agent FROM URGENT HANDLING !");
            close();
            return true;
        }
        return false;
    }

    protected void onConnect() {
        handler.onConnect(getIp());
    }

    protected void onDisconnect() {
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

                String[] list = line.split(mainDelim);
                if (list.length == 0)
                    // ??
                    continue;

                String command = list[0];
                String[] data = null;
                if (list.length > 1)
                    data = list[1].split(secDelim);

                if (!handleUrgent(command, data))
                    handleCommand(command, data);
            }
            System.out.println("Clean disconnection.");
            close();
            System.out.println("Closed. Cool.");
        } catch (SocketException e) {
            System.out.println("Socket Exception !");
        } catch (IOException e) {
            e.printStackTrace();
        }
        System.out.println("Calling onDisconnect...");
        onDisconnect();
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

    public void setHandler(NetworkHandler<T> handler) {
        this.handler = handler;
        handler.setAgent(this);
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
