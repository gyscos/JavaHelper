package com.helper;

import java.util.Iterator;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class JsonHelper {

    public static void merge(JSONArray source, JSONArray target)
            throws JSONException {
        for (int i = 0; i < source.length(); i++) {
            target.put(source.get(i));
        }
    }

    public static void merge(JSONObject source, JSONObject target)
            throws JSONException {
        Iterator<?> it = source.keys();
        while (it.hasNext()) {
            String key = (String) it.next();
            // If key is not present, obj = null
            Object obj = target.opt(key);

            if (obj instanceof JSONObject) {
                merge(source.getJSONObject(key), target.getJSONObject(key));
            } else if (obj instanceof JSONArray) {
                merge(source.getJSONArray(key), target.getJSONArray(key));
            } else {
                // Also happens if obj = null
                target.put(key, source.get(key));
            }
        }

    }

}
