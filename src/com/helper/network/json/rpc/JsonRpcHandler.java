package com.helper.network.json.rpc;

import org.json.JSONException;
import org.json.JSONObject;

import com.helper.network.json.JsonHandler;

/**
 * JSON handler implementation that specifically handle a JSON-RPC conversation.
 * 
 * @author gyscos
 * 
 */
public abstract class JsonRpcHandler extends JsonHandler {

    @Override
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

    /**
     * Actually react to a command.
     * 
     * @param method
     * @param params
     * @return
     */
    public abstract Object getResult(String method, Object params);
}
