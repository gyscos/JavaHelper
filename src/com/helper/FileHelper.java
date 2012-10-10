package com.helper;

import java.io.*;
import java.util.zip.GZIPInputStream;
import java.util.zip.GZIPOutputStream;

public class FileHelper {

    public static void writeToFile(String filename, String content) {
        writeToFile(filename, content, false);
    }

    public static void writeToFile(String filename, String content, boolean compress) {
        try {
            writeToFile(new FileOutputStream(filename), content, compress);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
    }

    static void writeToFile(OutputStream out, String content, boolean compress) {
        try {
            OutputStream os = compress ? new GZIPOutputStream(out) : out;

            OutputStreamWriter ow = new OutputStreamWriter(os, "UTF-8");
            PrintWriter writer = new PrintWriter(ow);
            writer.println(content);
            writer.close();
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static String getContent(String filename) throws FileNotFoundException {
        return getContent(filename, false);
    }

    public static String getContent(String filename, boolean decompress) throws FileNotFoundException {
        FileInputStream fis = new FileInputStream(filename);
        return getContent(fis, decompress);
    }

    static String getContent(InputStream in, boolean decompress) {
        try {
            InputStream is = decompress ? new GZIPInputStream(in) : in;
            InputStreamReader ir = new InputStreamReader(is, "UTF-8");
            BufferedReader reader = new BufferedReader(ir);

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
