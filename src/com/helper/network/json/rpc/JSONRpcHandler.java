package com.helper.network.json.rpc;

import com.helper.network.json.JSONHandler;

import org.json.JSONException;
import org.json.JSONObject;

public abstract class JSONRpcHandler implements JSONHandler {

    public abstract Object getResult(String method, Object params);

    public JSONObject getAnswer(JSONObject command) {

        try {
            int id = command.optInt("id", 0);
            String method = command.getString("method");
            Object params = command.get("params");

            Object result = getResult(method, params);

            JSONObject answer = new JSONObject();

            answer.put("id", id);
            answer.put("jsonrpc", "2.0");
            answer.put("result", result);

            return answer;
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return null;

    }
}
