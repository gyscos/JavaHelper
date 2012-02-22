package com.helper.network;

public abstract class NetworkHandler<T extends Enum<T> & NetworkCommand> {

    protected NetworkAgent<T> agent;

    public NetworkHandler() {

    }

    public NetworkHandler(NetworkAgent<T> agent) {
        this.agent = agent;
    }

    public abstract void handleCommand(T command, String[] data);

    /**
     * Allows to say "Hi" on connect.
     */
    public abstract void onConnect();

    /**
     * Called when the connection was lost.
     */
    public abstract void onDisconnect();

    public synchronized void send(String command, Object... data) {
        agent.send(command, data);
    }

    public synchronized void send(T command, Object... data) {
        agent.send(command, data);
    }

    public void setAgent(NetworkAgent<T> agent) {
        this.agent = agent;
    }

}
