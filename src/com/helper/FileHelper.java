package com.helper;

import java.io.UnsupportedEncodingException;
import java.io.FileNotFoundException;
import java.io.FileInputStream;
import java.io.InputStreamReader;
import java.io.BufferedReader;
import java.io.PrintWriter;
import java.io.IOException;

class FileHelper {
    public static void writeToFile(String filename, String content) throws FileNotFoundException {
        try {
            PrintWriter writer = new PrintWriter(filename, "UTF-8");
            writer.println(content);
            writer.close();
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
    }

    public static String getContent(String filename) throws FileNotFoundException {
        try {
            FileInputStream fis = new FileInputStream(filename);
            InputStreamReader in = new InputStreamReader(fis, "UTF-8");
            BufferedReader reader = new BufferedReader(in);

            String result = "";
            String line;
            while ((line = reader.readLine()) != null)
                result += line;

            reader.close();

            return result;

        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }

        return "";
    }
}
