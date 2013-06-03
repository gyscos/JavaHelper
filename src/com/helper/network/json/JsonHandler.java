package com.helper.network.json;

import java.io.BufferedReader;
import java.io.IOException;

import org.json.JSONException;
import org.json.JSONObject;
import org.json.JSONTokener;

import com.helper.network.NetworkHandler;

/**
 * NetworkHandler implementation that reads a JSON command and answer with a
 * JSON object.
 * 
 * @author gyscos
 * 
 */
public abstract class JsonHandler extends NetworkHandler {
    public abstract JSONObject getAnswer(JSONObject message);

    @Override
    public boolean readMessage(BufferedReader in)
            throws IOException {

        try {
            JSONObject message = new JSONObject(new JSONTokener(in));
            JSONObject answer = getAnswer(message);
            if (answer != null)
                sendObject(answer);
            return true;

        } catch (JSONException e) {
            e.printStackTrace();
            return false;
        }
    }

    public void sendObject(JSONObject object) {
        agent.sendMessage(object.toString());
    }
}
