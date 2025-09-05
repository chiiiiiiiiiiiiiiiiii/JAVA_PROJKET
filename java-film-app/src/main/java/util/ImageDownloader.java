package util;

import java.io.*;
import java.net.URL;
import java.nio.file.Files;
import java.nio.file.Paths;

public class ImageDownloader {

    public static String downloadToAssets(String imageUrl, String fileNameHint) {
        try {

            if (imageUrl == null || imageUrl.isEmpty()) {
                return "assets/default_movie.jpg";
            }
            // Create the assets folder if it doesn't exist
            Files.createDirectories(Paths.get("assets"));

            // Create a safe file name
            String extension = imageUrl.endsWith(".png") ? ".png" : ".jpg";
            String safeName = fileNameHint.replaceAll("[^a-zA-Z0-9\\-]", "_").toLowerCase() + extension;
            String localPath = "assets/" + safeName;

            // Download image
            try (InputStream in = new URL(imageUrl).openStream();
                 OutputStream out = new FileOutputStream(localPath)) {
                byte[] buffer = new byte[4096];
                int n;
                while ((n = in.read(buffer)) != -1) {
                    out.write(buffer, 0, n);
                }
            }

            return localPath; // store this path in the DB
        } catch (Exception e) {
            System.err.println("Failed to download image: " + e.getMessage());
            return null;
        }
    }
}
