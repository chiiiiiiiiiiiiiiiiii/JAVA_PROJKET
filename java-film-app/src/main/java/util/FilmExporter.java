package util;

import controller.FilmController;
import model.Film;

import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.transform.*;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;

import org.w3c.dom.*;

import java.io.File;
import java.io.FileWriter;
import java.util.List;

public class FilmExporter {

    private static final String DOWNLOADS = System.getProperty("user.home") + "/Downloads";

    public static void exportToCSV() throws Exception {
        List<Film> films = new FilmController().getAllFilms();
        File file = new File(DOWNLOADS, "films.csv");

        try (FileWriter writer = new FileWriter(file)) {
            writer.write("ID,Title,Year,Description,ImagePath\n");
            for (Film f : films) {
                writer.write(String.format("%d,\"%s\",%d,\"%s\",\"%s\"\n",
                        f.getId(),
                        f.getTitle().replace("\"", "\"\""),
                        f.getYear(),
                        f.getDescription().replace("\"", "\"\""),
                        f.getImagePath().replace("\"", "\"\"")));
            }
        }
    }

    public static void exportToXML() throws Exception {
        List<Film> films = new FilmController().getAllFilms();
        File file = new File(DOWNLOADS, "films.xml");

        DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
        DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();
        Document doc = dBuilder.newDocument();

        Element root = doc.createElement("films");
        doc.appendChild(root);

        for (Film f : films) {
            Element filmElem = doc.createElement("film");

            root.appendChild(addElement(doc, filmElem, "id", String.valueOf(f.getId())));
            root.appendChild(addElement(doc, filmElem, "title", f.getTitle()));
            root.appendChild(addElement(doc, filmElem, "year", String.valueOf(f.getYear())));
            root.appendChild(addElement(doc, filmElem, "description", f.getDescription()));
            root.appendChild(addElement(doc, filmElem, "imagePath", f.getImagePath()));
        }

        Transformer transformer = TransformerFactory.newInstance().newTransformer();
        transformer.setOutputProperty(OutputKeys.INDENT, "yes");

        transformer.transform(new DOMSource(doc), new StreamResult(file));
    }

    private static Element addElement(Document doc, Element parent, String name, String value) {
        Element elem = doc.createElement(name);
        elem.appendChild(doc.createTextNode(value));
        parent.appendChild(elem);
        return parent;
    }
}
