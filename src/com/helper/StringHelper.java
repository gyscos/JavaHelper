package com.helper;

import java.io.IOException;

public class StringHelper {

    public static String broadcastize(String ip) {
        String[] list = ip.split("\\.");

        if (list.length != 4)
            return ip;

        list[3] = "255";
        return merge(".", list);
    }

    public static String merge(String del, double... list) {
        if (list.length == 0)
            return "";

        String result = "" + list[0];
        for (int i = 1; i < list.length; i++)
            result += del + list[i];

        return result;
    }

    public static String merge(String del, Object... list) {
        if (list.length == 0)
            return "";

        String result = list[0].toString();
        for (int i = 1; i < list.length; i++)
            result += del + list[i].toString();

        return result;
    }

    public static String merge(String del, String... list) {
        return merge(del, (Object[]) list);
    }

    // TODO ! Base64 ?
    public static byte[] stringToBytes(String str) {
        try {
            return Base64.decode(str);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    // TODO ! Base64 ?
    public static String bytesToString(byte[] bytes) {
        return Base64.encodeBytes(bytes);
    }
}
