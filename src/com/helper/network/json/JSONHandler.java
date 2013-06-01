package com.helper.network.json;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.PrintWriter;

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
public abstract class JSONHandler extends NetworkHandler {
    public abstract JSONObject getAnswer(JSONObject command);

    @Override
    public boolean readMessage(BufferedReader in, PrintWriter out)
            throws IOException {

        try {
            JSONObject command = new JSONObject(new JSONTokener(in));
            JSONObject answer = getAnswer(command);
            if (answer != null)
                out.print(answer.toString());
            return true;

        } catch (JSONException e) {
            e.printStackTrace();
            return false;
        }
    }
}
