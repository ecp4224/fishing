package com.boxtrotstudio.fishing.utils;

import org.jetbrains.annotations.NotNull;

import java.io.IOException;
import java.io.InputStreamReader;
import java.io.LineNumberReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class WebUtils {

    public static List<String> readContentsToList(URL url) throws IOException {
        LineNumberReader reader = new LineNumberReader(new InputStreamReader(url.openStream()));
        List<String> lines = new ArrayList<String>();
        String line;
        while ((line = reader.readLine()) != null)
            lines.add(line);

        reader.close();
        return lines;
    }

    public static String[] readContentsToArray(URL url) throws IOException {
        List<String> lines = readContentsToList(url);
        return lines.toArray(new String[lines.size()]);
    }

    public static String readContentsToString(URL url) throws IOException {
        LineNumberReader reader = new LineNumberReader(new InputStreamReader(url.openStream()));
        StringBuilder content = new StringBuilder();

        String line;
        while ((line = reader.readLine()) != null)
            content.append(line);

        return content.toString();
    }

    @NotNull
    public static String postToURL(@NotNull URL url, @NotNull String contents) throws IOException {
        String type = "application/x-www-form-urlencoded";

        HttpURLConnection conn = (HttpURLConnection)url.openConnection();

        conn.setDoOutput(true);
        conn.setDoInput(true);
        conn.setRequestMethod("POST");
        conn.setRequestProperty("Content-Type", type);
        conn.setRequestProperty("Content-Length", String.valueOf(contents.length()));

        try (OutputStream os = conn.getOutputStream()) {
            os.write(contents.getBytes());
        }

        Scanner reader = new Scanner(conn.getInputStream());
        StringBuilder output = new StringBuilder();
        String line;
        while (reader.hasNext()) {
            line = reader.nextLine();
            output.append(line).append("\n");
        }
        reader.close();

        return output.toString();
    }
}
