package com.helper.network.json.rpc;

import java.util.Map;

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

    Map<Integer, JSONObject> answers;

    @Override
    public JSONObject getAnswer(JSONObject message) {

        try {
            int id = message.optInt("id", 0);

            if (message.has("method")) {
                String method = message.getString("method");
                Object params = message.get("params");

                Object result = getResult(method, params);

                JSONObject answer = new JSONObject();

                answer.put("id", id);
                answer.put("jsonrpc", "2.0");
                answer.put("result", result);

                return answer;
            } else if (message.has("result")) {
                // Success !
                answers.put(id, message.getJSONObject("result"));
                notifyAll();
            }

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

    public JSONObject sendCommand(String command, JSONObject params) {
        JSONObject request = new JSONObject();

        try {
            int id = 0;

            request.put("method", command);
            request.put("params", params);
            request.put("id", id);
            sendObject(request);

            // Now, wait for answer...
            synchronized (this) {
                while (true) {
                    if (answers.containsKey(id))
                        break;
                    else
                        wait();
                }
                return answers.remove(id);
            }

        } catch (JSONException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        return null;
    }
}
