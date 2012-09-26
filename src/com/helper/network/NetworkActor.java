package com.helper.network;

import java.util.HashMap;

import com.helper.StringHelper;

public class NetworkActor<T extends Enum<T> & NetworkCommand> extends
        NetworkAgent {
    public final static String mainDelim = "::";

    public final static String secDelim  = ";";

    HashMap<String, T>         commands  = new HashMap<String, T>();

    NetworkHandler<T>          handler;

    public NetworkActor(Class<T> c) {
        for (T t : c.getEnumConstants()) {
            addCommand(t.getName(), t);
        }
    }

    public NetworkActor(Class<T> c, NetworkHandler<T> handler) {
        this(c);
        setHandler(handler);
    }

    public void addCommand(String command, T cmdId) {
        commands.put(command, cmdId);
    }

    protected boolean handleCommand(String command, String[] data) {
        return handleCommand(commands.get(command), data);
    }

    protected boolean handleCommand(T command, String[] data) {
        if (handler != null)
            return handler.handleCommand(command, data);
        else
            return false;
    }

    @Override
    protected void handleMessage(String line) {
        String[] list = line.split(mainDelim);
        if (list.length == 0)
            // ??
            return;

        String command = list[0];
        String[] data = null;
        if (list.length > 1)
            data = list[1].split(secDelim);

        if (!handleUrgent(command, data))
            handleCommand(command, data);
    }

    public boolean handleUrgent(String command, String[] data) {
        if (command.equals("QUIT")) {
            close();
            return true;
        }
        return false;
    }

    @Override
    protected void onConnect() {
        if (handler != null)
            handler.onConnect(getIp());
    }

    @Override
    protected void onDisconnect() {
        if (handler != null)
            handler.onDisconnect();
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

}
