package com.helper;

public class StringHelper {

    public static String merge(String del, Object[] list) {
        if (list.length == 0)
            return "";

        String result = list[0].toString();
        for (int i = 1; i < list.length; i++)
            result += del + list[i].toString();

        return result;
    }
}
