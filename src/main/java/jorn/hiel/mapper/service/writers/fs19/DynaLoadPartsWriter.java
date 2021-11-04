package jorn.hiel.mapper.service.writers.fs19;

import jorn.hiel.mapper.service.ConfigFileReader;
import jorn.hiel.mapper.service.I3DMapper;
import jorn.hiel.mapper.service.helpers.NeedToWrite;
import jorn.hiel.mapper.service.interfaces.DocWriter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
@Component
public class DynaLoadPartsWriter implements DocWriter {

    @Autowired
    I3DMapper mapper;

    @Autowired
    NeedToWrite needToWrite;

    @Autowired
    ConfigFileReader configFileReader;


    @Override
    public void write(Document doc) {

        if (needToWrite.needsToWrite("dynamicallyLoadedParts")) {
            Node rootElement = doc.getElementsByTagName("Vehicle").item(0);
            {

                Element dyna = doc.createElement("dynamicallyLoadedParts");
                rootElement.appendChild(dyna);{

                int needed = Integer.parseInt(configFileReader.getMappedItem("numberOfDynamicallyLoadedParts").getValue());

                for (int x=0;x<needed;x++) {
                    Element dynamicallyLoadedPart = doc.createElement("dynamicallyLoadedPart");
                    dynamicallyLoadedPart.setAttribute("linkNode", mapper.getMappedItem("dynamicallyLoadedPart"+x+"Node").getValue());
                    dynamicallyLoadedPart.setAttribute("Node", "0");
                    dynamicallyLoadedPart.setAttribute("linkNode", mapper.getMappedItem("dynamicallyLoadedPart"+x+"Filename").getValue());




                    dyna.appendChild(dynamicallyLoadedPart);
                }

}}}}}

