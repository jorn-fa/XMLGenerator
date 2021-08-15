package jorn.hiel.mapper.service.writers;


import jorn.hiel.mapper.pojo.MappedItem;
import jorn.hiel.mapper.service.ConfigFileReader;
import jorn.hiel.mapper.service.I3DMapper;
import jorn.hiel.mapper.service.interfaces.SingleXmlItem;
import jorn.hiel.mapper.service.interfaces.SingleXmlItemWithAttribute;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.w3c.dom.Document;
import org.w3c.dom.Element;

import javax.xml.XMLConstants;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.OutputKeys;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;
import java.io.File;

@Service
@Slf4j
public class ModDescWriterDom implements SingleXmlItem, SingleXmlItemWithAttribute {


    @Autowired
    I3DMapper mapper;

    @Autowired
    ConfigFileReader configFileReader;

    private boolean canWrite;
    private Document doc;
    private String fileLocation;


    public void setFileLocation(String fileLocation) throws ParserConfigurationException {
        
        this.fileLocation = fileLocation;
        DocumentBuilderFactory docFactory = DocumentBuilderFactory.newInstance();
        DocumentBuilder docBuilder = docFactory.newDocumentBuilder();
        doc = docBuilder.newDocument();

        this.canWrite=true;

    }

    public void writeModdesc() throws TransformerException {

        if(canWrite) {
            MappedItem mappedItem;
            Element rootElement = doc.createElement("modDesc");
            doc.appendChild(rootElement);

            //descVersion
            mappedItem=configFileReader.getMappedItem("descVersion");
            rootElement.setAttribute(mappedItem.getKey(),mappedItem.getValue());

            addSingleXmlItem(doc, rootElement,configFileReader.getMappedItem("author"));
            addSingleXmlItem(doc, rootElement,configFileReader.getMappedItem("version"));
            addSingleItemWithAttribute(doc, rootElement,configFileReader.getMappedItem("multiplayer"),"supported");
            addSingleXmlItem(doc, rootElement,configFileReader.getMappedItem("iconFilename"));


            writeXml(doc);

        }

    }

    // write doc to output stream
    private void writeXml(Document document)
            throws TransformerException {

        TransformerFactory transformerFactory = TransformerFactory.newInstance();
        transformerFactory.setAttribute(XMLConstants.ACCESS_EXTERNAL_DTD, "");
        transformerFactory.setAttribute(XMLConstants.ACCESS_EXTERNAL_STYLESHEET, "");

        Transformer transformer = transformerFactory.newTransformer();
        transformer.setOutputProperty(OutputKeys.INDENT, "yes");
        transformer.setOutputProperty(OutputKeys.METHOD, "xml");
        transformer.setOutputProperty("{http://xml.apache.org/xslt}indent-amount", "5");

        DOMSource domSource = new DOMSource(document);
        if(!this.fileLocation.isEmpty()) {
            StreamResult streamResult = new StreamResult(new File(this.fileLocation));
            transformer.transform(domSource, streamResult);
        }
        else {log.error("Trying to save to empty string");}




    }



}
