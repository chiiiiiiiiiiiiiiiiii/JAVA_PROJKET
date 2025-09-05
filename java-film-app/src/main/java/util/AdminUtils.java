package util;

import java.io.File;

public class AdminUtils {

    public static void deleteAllImages(String folderPath) {
        File folder = new File(folderPath);
        if (folder.exists() && folder.isDirectory()) {
            for (File file : folder.listFiles()) {
                if (!file.isDirectory()) {
                    file.delete();
                }
            }
        }
    }
}
