package com.helper.network;

import java.io.BufferedReader;
import java.io.IOException;

public abstract class LineNetworkHandler extends NetworkHandler {

    protected abstract boolean readLine(String line);

    @Override
    public boolean readMessage(BufferedReader in)
            throws IOException {
        String line = in.readLine();
        if (line == null)
            return false;

        return readLine(line);
    }
}
