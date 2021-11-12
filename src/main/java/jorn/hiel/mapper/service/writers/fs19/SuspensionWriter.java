package jorn.hiel.mapper.service.writers.fs19;

import jorn.hiel.mapper.service.ConfigFileReader;
import jorn.hiel.mapper.service.I3DMapper;
import jorn.hiel.mapper.service.enums.VehicleSpec;
import jorn.hiel.mapper.service.helpers.NeedToWrite;
import jorn.hiel.mapper.service.interfaces.DocWriter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.w3c.dom.Comment;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
@Component
public class SuspensionWriter implements DocWriter {

    @Autowired
    I3DMapper mapper;

    @Autowired
    NeedToWrite needToWrite;

    @Autowired
    ConfigFileReader configFileReader;


    @Override
    public void write(Document doc) {

        if (needToWrite.needsToWrite(VehicleSpec.SUSPENSIONS)) {
            Node rootElement = doc.getElementsByTagName("Vehicle").item(0);
            {

                Element suspensions = doc.createElement("suspensions");
                rootElement.appendChild(suspensions);

                int needed = Integer.parseInt(configFileReader.getMappedItem("numberOfSuspensions").getValue());

                for (int x=0;x<needed;x++) {
                    String item = "suspension";
                    Element element = doc.createElement(item);
                    element.setAttribute("node", mapper.getMappedItem(item+x+"Node").getValue());
                    element.setAttribute("weight", mapper.getMappedItem(item+x+"Weight").getValue());
                    element.setAttribute("minRotation", mapper.getMappedItem(item+x+"MinRotation").getValue());
                    element.setAttribute("maxRotation", mapper.getMappedItem(item+x+"MaxRotation").getValue());
                    element.setAttribute("suspensionParametersX", mapper.getMappedItem(item+x+"SuspensionParametersX").getValue());
                    element.setAttribute("suspensionParametersZ", mapper.getMappedItem(item+x+"SuspensionParametersZ").getValue());
                    suspensions.appendChild(element);
                }
                //character
                Comment comment = doc.createComment("Character");
                suspensions.appendChild(comment);
                Element charSuspension = doc.createElement("suspension");
                suspensions.appendChild(charSuspension);
                charSuspension.setAttribute("useCharacterTorso", "true");
                charSuspension.setAttribute("weight", "90");
                charSuspension.setAttribute("minRotation", "0 -5 -5" );
                charSuspension.setAttribute("maxRotation", "0 5 5");
                charSuspension.setAttribute("suspensionParametersX", "7 1");
                charSuspension.setAttribute("suspensionParametersZ", "7 1");


            }
        }
    }
}
