package jorn.hiel.mapper.service.writers.fs19;

import lombok.Setter;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.w3c.dom.Document;

import javax.xml.XMLConstants;
import javax.xml.transform.OutputKeys;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;
import java.io.File;

@Service
@Slf4j
public class XmlFileWriter {

    @Setter
    private String fileLocation;

    /**
     *
     * @param document Document
     * @throws TransformerException
     *
     * writes the passed document to a xml file
     */

    public void writeXml(Document document)
            throws TransformerException {

        TransformerFactory transformerFactory = TransformerFactory.newInstance();
        transformerFactory.setAttribute(XMLConstants.ACCESS_EXTERNAL_DTD, "");
        transformerFactory.setAttribute(XMLConstants.ACCESS_EXTERNAL_STYLESHEET, "");

        Transformer transformer = transformerFactory.newTransformer();
        transformer.setOutputProperty(OutputKeys.INDENT, "yes");
        transformer.setOutputProperty(OutputKeys.METHOD, "xml");
        transformer.setOutputProperty(OutputKeys.ENCODING, "UTF-8");

        transformer.setOutputProperty("{http://xml.apache.org/xslt}indent-amount", "5");

        DOMSource domSource = new DOMSource(document);
        if(!this.fileLocation.isEmpty()) {
            StreamResult streamResult = new StreamResult(new File(this.fileLocation));
            transformer.transform(domSource, streamResult);
        }
        else {log.error("Trying to save to empty location");}




    }




}
