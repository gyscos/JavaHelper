package com.helper.network.commands;

import com.helper.network.NetworkAgent;

public class NetworkCommandAgent<T extends Enum<T> & NetworkCommand> extends
        NetworkAgent {

    NetworkCommandAgent()
    {
    }

    public NetworkCommandAgent(NetworkCommandHandler<T> handler) {
        super(handler);
    }
}
