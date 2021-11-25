package jorn.hiel.mapper.service.writers;


import jorn.hiel.mapper.pojo.MappedItem;
import jorn.hiel.mapper.service.ConfigFileReader;
import jorn.hiel.mapper.service.enums.LocalLanguage;
import jorn.hiel.mapper.service.interfaces.SingleXmlItem;
import jorn.hiel.mapper.service.interfaces.SingleXmlItemWithAttribute;
import jorn.hiel.mapper.service.writers.specs.XmlFileWriter;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.w3c.dom.Document;
import org.w3c.dom.Element;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.TransformerException;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

@Service
@Slf4j
public class ModDescWriterDom implements SingleXmlItem, SingleXmlItemWithAttribute {



    @Autowired
    ConfigFileReader configFileReader;

    @Autowired
    XmlFileWriter xmlFileWriter;

    private boolean canWrite;
    private Document doc;


    public void setFileLocation(String fileLocation){
        xmlFileWriter.setFileLocation(fileLocation);
        this.canWrite=true;

    }

    public void writeModDesc() throws TransformerException , ParserConfigurationException {

        if(canWrite) {

            DocumentBuilderFactory docFactory = DocumentBuilderFactory.newInstance();
            DocumentBuilder docBuilder = docFactory.newDocumentBuilder();
            doc = docBuilder.newDocument();

            MappedItem mappedItem;
            Element rootElement = doc.createElement("modDesc");
            doc.appendChild(rootElement);

            //descVersion
            mappedItem=configFileReader.getMappedItem("modDescVersion");
            rootElement.setAttribute(mappedItem.getKey(),mappedItem.getValue());

            addSingleXmlItem(doc, rootElement,configFileReader.getMappedItem("author"));
            addSingleXmlItem(doc, rootElement,configFileReader.getMappedItem("version"));

            Element multiplayer = doc.createElement("multiplayer");
            multiplayer.setAttribute("supported",configFileReader.getMappedItem("multiplayer").getValue());
            rootElement.appendChild(multiplayer);

            addSingleXmlItem(doc, rootElement,configFileReader.getMappedItem("iconFilename"));
            createTitlesAndDescriptions(rootElement,doc);

            Element storeItems = doc.createElement("storeItems");
            Element storeItem = doc.createElement("storeItem");
            storeItem.setAttribute("rootNode","vehicle");
            storeItem.setAttribute("xmlFilename",configFileReader.getMappedItem("vehicleFileName").getValue());
            storeItems.appendChild(storeItem);

            rootElement.appendChild(storeItems);

            xmlFileWriter.writeXml(doc);

        }

    }

    /**
     *
     * @param rootElement the rootElement
     * @param doc Document
     *
     * Create title and description entries based title elements found in configuration file
     */
    private void createTitlesAndDescriptions(Element rootElement, Document doc) {
        List<String> languages = new ArrayList<>();

        List<String> foundLanguages = new ArrayList<>();
        for (LocalLanguage local:LocalLanguage.values()){foundLanguages.add(local.toString().toLowerCase(Locale.ROOT)+"Title");}

        for (String foundLanguage : foundLanguages) {
            log.info("looking for " + foundLanguage + " in config file");
            if (configFileReader.getMappedItem(foundLanguage).getValue().equals("true")){
                languages.add(foundLanguage.substring(0,2));
                log.info("adding "  + foundLanguage);
            }
            else{
                if(configFileReader.getMappedItem(foundLanguage).getValue().equals("false"))
                log.info("item found but disabled by user");
            }

        }

        Element title = doc.createElement("title");
        Element description = doc.createElement("description");


        languages.forEach(a-> {
            //title
            Element titleElement = doc.createElement(a);
            titleElement.appendChild(doc.createCDATASection(configFileReader.getMappedItem("randomString").getValue()));
            title.appendChild(titleElement);
            //desc
            Element descriptionElement = doc.createElement(a);
            descriptionElement.appendChild(doc.createCDATASection(configFileReader.getMappedItem("randomString").getValue()));
            description.appendChild(descriptionElement);
        });


        rootElement.appendChild(title);
        rootElement.appendChild(description);


    }





}
