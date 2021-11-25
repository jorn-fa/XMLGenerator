package jorn.hiel.mapper.service.writers.specs;

import jorn.hiel.mapper.service.ConfigFileReader;
import jorn.hiel.mapper.service.I3DMapper;
import jorn.hiel.mapper.service.enums.VehicleSpec;
import jorn.hiel.mapper.service.helpers.NeedToWrite;
import jorn.hiel.mapper.service.interfaces.DocWriter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;

@Component
public class CylindredWriter implements DocWriter {

    @Autowired
    NeedToWrite needToWrite;

    @Autowired
    I3DMapper mapper;

    @Autowired
    ConfigFileReader configFileReader;



    @Override
    public void write(Document doc) {
        if (needToWrite.needsToWrite(VehicleSpec.CYLINDERED)) {

            Node rootElement = doc.getElementsByTagName("vehicle").item(0);
            {
                Element cylindered = doc.createElement("cylindered");
                rootElement.appendChild(cylindered);
                {
                    Element movingTools = doc.createElement("movingTools");
                    cylindered.appendChild(movingTools);
                    Element movingTool = doc.createElement("movingTool");
                    movingTools.appendChild(movingTool);

                    Element movingParts = doc.createElement("movingParts");
                    cylindered.appendChild(movingParts);
                    Element movingPart = doc.createElement("movingPart");
                    movingParts.appendChild(movingPart);



                    Element sounds = doc.createElement("sounds");
                    Element hydraulic = doc.createElement("hydraulic");
                    hydraulic.setAttribute("template","DEFAULT_HYDRAULIC_SOUND");
                    cylindered.appendChild(sounds);
                    sounds.appendChild(hydraulic);



                }
            }
        }
    }
}
