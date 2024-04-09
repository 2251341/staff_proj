
package org.example.util;

import java.io.*;
import java.time.LocalDateTime;

public class Util {
    public static String getNowDateStr() {
        LocalDateTime now = LocalDateTime.now();
        return now.toString();
    }

    public static String lcfirst(String str) {
        String newStr = "";
        newStr += str.charAt(0);
        newStr = newStr.toLowerCase();
        return newStr + str.substring(1);
    }

    public static boolean isFileExists(String filePath) {
        File f = new File(filePath);
        return f.isFile();
    }

    public static String getFileContents(String filePath) {
        String rs = null;
        try {
            FileInputStream fileStream = new FileInputStream(filePath);
            byte[] readBuffer = new byte[fileStream.available()];
            while (fileStream.read(readBuffer) != -1) {}
            rs = new String(readBuffer);
            fileStream.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return rs;
    }

    public static void writeFileContents(String filePath, String contents) {
        try (BufferedOutputStream bs = new BufferedOutputStream(new FileOutputStream(filePath))) {
            bs.write(contents.getBytes());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
