package jorn.hiel.mapper.service;

import jorn.hiel.mapper.service.interfaces.SingleXmlItem;
import jorn.hiel.mapper.service.writers.StoreDataWriter;
import jorn.hiel.mapper.service.writers.XmlFileWriter;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.w3c.dom.Document;
import org.w3c.dom.Element;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.TransformerException;

@Service
@Slf4j
public class VehicleBuilder implements SingleXmlItem {

    private boolean canWrite;

    @Autowired
    XmlFileWriter xmlFileWriter;

    @Autowired
    ConfigFileReader configFileReader;

    @Autowired
    StoreDataWriter storedataWriter;

    private Document doc;


    public void setFileLocation(String fileLocation){
        xmlFileWriter.setFileLocation(fileLocation);
        this.canWrite=true;

    }

    public void writeVehicle() throws TransformerException, ParserConfigurationException {

        if(canWrite) {



            DocumentBuilderFactory docFactory = DocumentBuilderFactory.newInstance();
            DocumentBuilder docBuilder = docFactory.newDocumentBuilder();
            doc = docBuilder.newDocument();

            Element rootElement = doc.createElement("Vehicle");
            doc.appendChild(rootElement);

            rootElement.setAttribute("type",configFileReader.getMappedItem("vehicleType").getValue());
            addSingleXmlItem(doc,rootElement,configFileReader.getMappedItem("annotationVehicle"));



            storedataWriter.write(doc);


            xmlFileWriter.writeXml(doc);

        }



        }

}
