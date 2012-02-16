package com.helper;

public class StringHelper {

    public static String broadcastize(String ip) {
        String[] list = ip.split("\\.");

        if (list.length != 4)
            return ip;

        list[3] = "255";
        return merge(".", list);
    }

    public static String merge(String del, Object[] list) {
        if (list.length == 0)
            return "";

        String result = list[0].toString();
        for (int i = 1; i < list.length; i++)
            result += del + list[i].toString();

        return result;
    }
}
