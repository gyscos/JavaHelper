package com.helper.network.commands;

import java.util.HashMap;

import com.helper.StringHelper;
import com.helper.network.NetworkHandler;

/**
 * Network handler implementation that uses a delimiter-based protocol, and a
 * list of possible commands as enums.
 * 
 * @author gyscos
 * 
 * @param <T>
 */
public abstract class NetworkCommandHandler<T extends Enum<T> & NetworkCommand>
        extends NetworkHandler {

    public final static String mainDelim = "::";
    public final static String secDelim  = ";";

    HashMap<String, T>         commands  = new HashMap<String, T>();

    public NetworkCommandHandler(Class<T> c) {
        super();

        for (T t : c.getEnumConstants()) {
            addCommand(t.getName(), t);
        }
    }

    public NetworkCommandHandler(Class<T> c, NetworkCommandAgent<T> agent) {
        this(c);
        setAgent(agent);
    }

    public void addCommand(String command, T cmdId) {
        commands.put(command, cmdId);
    }

    @SuppressWarnings("unchecked")
    protected NetworkCommandAgent<T> getCommandAgent() {
        return (NetworkCommandAgent<T>) agent;
    }

    protected boolean handleCommand(String command, String[] data) {
        return handleCommand(commands.get(command), data);
    }

    public abstract boolean handleCommand(T command, String[] data);

    @Override
    public boolean handleMessage(String line) {
        String[] list = line.split(mainDelim);
        if (list.length == 0)
            // ??
            return false;

        String command = list[0];
        String[] data = null;
        if (list.length > 1)
            data = list[1].split(secDelim);

        if (handleUrgent(command, data))
            return true;

        return handleCommand(command, data);
    }

    public boolean handleUrgent(String command, String[] data) {
        if (command.equals("QUIT")) {
            agent.close();
            return true;
        }
        return false;
    }

    public synchronized void send(String command, Object... data) {
        send(command + mainDelim + StringHelper.merge(secDelim, data));
    }

    public synchronized void send(T command, Object... data) {
        send(command.getName(), data);
    }

    public synchronized void send(T command, String... data) {
        send(command, (Object[]) data);
    }

    public void setAgent(NetworkCommandAgent<T> agent) {
        super.setAgent(agent);
    }
}
