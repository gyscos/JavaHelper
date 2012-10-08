package com.helper.network.json;

import org.json.*;

import java.io.InputStream;
import java.io.OutputStream;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.net.SocketException;

public class JSONAgent {
    Socket socket;
    InputStream in;
    OutputStream out;

    public JSONAgent(Socket socket) {
        try {
            this.socket = socket;
            in = socket.getInputStream();
            out = socket.getOutputStream();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public JSONObject read_JSON() {
        try {
            return new JSONObject(new JSONTokener(in));
        } catch (JSONException e) {
            e.printStackTrace();
            return null;
        }
    }

    public void send_JSON(JSONObject msg) {
        PrintWriter writer = new PrintWriter(out);
        writer.print(msg.toString());
        writer.close();
    }

    public void close() {
        try {
            socket.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static JSONAgent setup(Socket socket) {
        return new JSONAgent(socket);
    }


    public static JSONAgent connect(String server, int port) {
        try {
            return setup(new Socket(server, port));
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    public JSONObject getAnswer(JSONObject command) {
        send_JSON(command);
        return read_JSON();
    }

    public void answer(final JSONHandler handler, boolean threaded) {
        Thread thread = new Thread() {
            @Override
            public void run() {
                JSONObject command = read_JSON();
                JSONObject answer = handler.getAnswer(command);
                send_JSON(answer);
            }
        };
        if (threaded)
            thread.start();
        else
            thread.run();
    }

    public static JSONObject getAnswer(String host, int port, JSONObject command) {
        JSONAgent agent = connect(host, port);
        JSONObject result = agent.getAnswer(command);
        agent.close();
        return result;
    }

    public static void answer(String host, int port, JSONHandler handler, boolean threaded) {
        JSONAgent agent = connect(host, port);
        agent.answer(handler, threaded);
        agent.close();
    }
}
