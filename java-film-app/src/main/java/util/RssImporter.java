package util;

import controller.FilmController;
import model.Film;
import org.w3c.dom.*;
import javax.xml.parsers.DocumentBuilderFactory;
import java.io.InputStream;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

@FunctionalInterface
interface ImagePathExtractor {
    String extractImagePath(Element itemElement);
}

public class RssImporter {

    public static int importFromRss(InputStream inputStream, ImagePathExtractor extractor) {
        int importedCount = 0;

        try {
            Document doc = DocumentBuilderFactory.newInstance()
                    .newDocumentBuilder()
                    .parse(inputStream);
            doc.getDocumentElement().normalize();

            NodeList items = doc.getElementsByTagName("item");
            List<Film> films = new ArrayList<>();

            for (int i = 0; i < items.getLength(); i++) {
                Element e = (Element) items.item(i);

                String title = getTextContent(e, "title");
                String desc = getTextContent(e, "description");
                int year = 2025; // or getTextContent(e, "year") if needed

                String imagePath = extractor.extractImagePath(e);
                if (imagePath == null || imagePath.trim().isEmpty()) {
                    imagePath = "assets/default_movie.jpg";
                }

                films.add(new Film(title, year, desc, imagePath));
            }

            FilmController repo = new FilmController();
            for (Film f : films) {
                repo.addFilm(f);
                importedCount++;
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

        return importedCount;
    }

    private static String getTextContent(Element parent, String tag) {
        NodeList list = parent.getElementsByTagName(tag);
        if (list.getLength() > 0 && list.item(0) != null) {
            return list.item(0).getTextContent().trim();
        }
        return "";
    }

    public static int importFromFile(String path) {
        try (InputStream is = RssImporter.class.getClassLoader().getResourceAsStream(path)) {
            if (is == null) throw new RuntimeException("File not found: " + path);
            return importFromRss(is, e -> e.getElementsByTagName("image").item(0).getTextContent().trim());
        } catch (Exception e) {
            e.printStackTrace();
            return 0;
        }
    }

    public static int importFromUrl(String urlStr) {
        try (InputStream is = new URL(urlStr).openStream()) {
            return importFromRss(is, e -> {
                NodeList imageList = e.getElementsByTagName("image");
                if (imageList.getLength() > 0) {
                    Element imageElem = (Element) imageList.item(0);
                    String link = getTextContent(imageElem, "link");
                    return ImageDownloader.downloadToAssets(link, getTextContent(e, "title"));
                }
                return null;
            });
        } catch (Exception e) {
            e.printStackTrace();
            return 0;
        }
    }

}
