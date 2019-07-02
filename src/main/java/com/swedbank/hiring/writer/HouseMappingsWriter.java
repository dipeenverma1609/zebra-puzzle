package com.swedbank.hiring.writer;

import com.swedbank.hiring.entity.HouseLists;
import org.w3c.dom.Attr;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.ProcessingInstruction;

import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.OutputKeys;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Map;
import java.util.Set;

public class HouseMappingsWriter {

    private static final HouseMappingsWriter INSTANCE = new HouseMappingsWriter();

    private HouseMappingsWriter() {}

    public static HouseMappingsWriter getInstance() {
        return INSTANCE;
    }

    public void write(HouseLists houseMappings, String filename) {
        try {
            File f = new File(filename);
            if (f.exists()) {
                f.delete();
            }
            f.createNewFile();

            final Transformer transformer = TransformerFactory.newInstance().newTransformer();
            transformer.setOutputProperty(OutputKeys.INDENT, "yes");
            transformer.setOutputProperty("{http://xml.apache.org/xslt}indent-amount", "2");
            transformer.transform(new DOMSource(convertToXml(houseMappings)), new StreamResult(new FileOutputStream(filename)));

        } catch (IOException e) {
            System.out.println("Unable to find file" + filename);
            System.exit(-1);
        } catch (TransformerException e) {
            System.out.println("Error while transforming house mappings to XML");
            System.exit(-1);
        } catch (ParserConfigurationException e) {
            System.out.println("Error configuring xml parser");
            System.exit(-1);
        }
    }

    protected Document convertToXml(HouseLists houses) throws ParserConfigurationException {

        final Document doc = DocumentBuilderFactory.newInstance().newDocumentBuilder().newDocument();

        final Element solutions = doc.createElement("solutions");
        doc.appendChild(solutions);
        doc.setXmlStandalone(true);

        final ProcessingInstruction pi = doc.createProcessingInstruction("xml-stylesheet", "type=\"text/xsl\" href=\"renderer.xsl\"");
        doc.insertBefore(pi, solutions);

        final Element solution = doc.createElement("solution");
        solutions.appendChild(solution);

        final Set<String> attributesSet = houses.attributesSet();
        for (Map<String, String> entry : houses) {
            final Element house = doc.createElement("house");
            solution.appendChild(house);
            for (String key : attributesSet) {
                final Attr attribute = doc.createAttribute(key);
                attribute.setValue(entry.get(key));
                house.setAttributeNode(attribute);
            }
        }
        return doc;
    }
}
