package com.helper.network.json;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.net.Socket;

import org.json.JSONException;
import org.json.JSONObject;
import org.json.JSONTokener;

public class JSONAgent {
    public static void answer(String host, int port, JSONHandler handler,
            boolean threaded) {
        JSONAgent agent = connect(host, port);
        agent.answer(handler, threaded);
        agent.close();
    }

    public static JSONAgent connect(String server, int port) {
        try {
            return setup(new Socket(server, port));
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    public static JSONObject getAnswer(String host, int port, JSONObject command) {
        JSONAgent agent = connect(host, port);
        JSONObject result = agent.getAnswer(command);
        agent.close();
        return result;
    }

    public static JSONAgent setup(Socket socket) {
        return new JSONAgent(socket);
    }

    Socket       socket;

    InputStream  in;

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

    public void close() {
        try {
            socket.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public JSONObject getAnswer(JSONObject command) {
        send_JSON(command);
        return read_JSON();
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
}
