package com.ssc.tortoise.automate.ai;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;

public class ReadFromFile {
    public static String readFileByLines(String filename) {
        StringBuffer sb = new StringBuffer();

        try (BufferedReader br = new BufferedReader(new FileReader(new File((String) filename)))) {
            String line = br.readLine();
            while (line != null) {
                sb.append(line).append("\n");
                line = br.readLine();
            }
        } catch (Exception e) {
            System.err.println("Failed to read file: " + e.getMessage());
        }
        return sb.toString();
    }
}
