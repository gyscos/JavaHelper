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
    JSONHandler handler;
    Socket socket;
    InputStream in;
    OutputStream out;

    public JSONAgent(JSONHandler handler) {
        this.handler = handler;
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

    public void run() {
        JSONObject object = read_JSON();
        JSONObject answer = handler.getAnswer(object);
        send_JSON(answer);
        close();
    }

    public void close() {
        try {
            socket.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void setup(Socket socket) {
        try {
            this.socket = socket;
            in = socket.getInputStream();
            out = socket.getOutputStream();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}
