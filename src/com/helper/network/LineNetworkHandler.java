package com.helper.network;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.PrintWriter;

public abstract class LineNetworkHandler extends NetworkHandler {

    protected abstract boolean readLine(String line, PrintWriter out);

    @Override
    public boolean readMessage(BufferedReader in, PrintWriter out)
            throws IOException {
        String line = in.readLine();
        if (line == null)
            return false;

        return readLine(line, out);
    }
}
