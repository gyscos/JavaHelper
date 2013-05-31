package com.helper.network;

/**
 * Slave class used by a network agent to do the actual work.
 * 
 * @author gyscos
 * 
 */
public abstract class NetworkHandler {

    protected NetworkAgent agent;

    public NetworkHandler() {
    }

    public NetworkHandler(NetworkAgent agent) {
        setAgent(agent);
    }

    /**
     * Handle an incoming network message.
     * 
     * @param line
     *            Content of the message.
     * @return
     */
    public abstract boolean handleMessage(String line);

    /**
     * Allows to say "Hi" on connect.
     */
    public abstract void onConnect(String ip);

    /**
     * Called when the connection was lost.
     */
    public abstract void onDisconnect();

    public synchronized void send(String line) {
        agent.send(line);
    }

    public void setAgent(NetworkAgent agent) {
        this.agent = agent;
    }
}
