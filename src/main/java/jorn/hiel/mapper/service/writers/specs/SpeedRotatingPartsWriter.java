package jorn.hiel.mapper.service.writers.specs;

import jorn.hiel.mapper.service.ConfigFileReader;
import jorn.hiel.mapper.service.enums.VehicleSpec;
import jorn.hiel.mapper.service.helpers.NeedToWrite;
import jorn.hiel.mapper.service.interfaces.DocWriter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
@Service
public class SpeedRotatingPartsWriter implements DocWriter {


    @Autowired
    ConfigFileReader configFileReader;

    @Autowired
    NeedToWrite needToWrite;

    public void write(Document doc) {

        if (configFileReader.getSpeedRotatingParts().size() > 0 && needToWrite.needsToWrite(VehicleSpec.SPEEDROTATINGPARTS))  {


            Node rootElement = doc.getElementsByTagName("vehicle").item(0);

            Node speedRotatingParts = doc.getElementsByTagName("speedRotatingParts").item(0);
            //create when not found
            if (speedRotatingParts == null) {
                speedRotatingParts = doc.createElement("speedRotatingParts");
            }

            rootElement.appendChild(speedRotatingParts);
            //convert to node to allow lambda access
            Node appendHere = speedRotatingParts;

            configFileReader.getSpeedRotatingParts().forEach((name,item)-> {

                Element speedRotatingPart = doc.createElement("speedRotatingPart");
                speedRotatingPart.setAttribute("node",name);
                speedRotatingPart.setAttribute("wheelIndex",configFileReader.getMappedItem("UnknownEntry").getValue());
                appendHere.appendChild(speedRotatingPart);

            });



        }
    }
}
