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
public class PowerTakeOffWriter implements DocWriter {

    @Autowired
    I3DMapper mapper;

    @Autowired
    NeedToWrite needToWrite;

    @Autowired
    ConfigFileReader configFileReader;


    @Override
    public void write(Document doc) {

        if (needToWrite.needsToWrite("powerTakeOffs")) {
            Node rootElement = doc.getElementsByTagName("Vehicle").item(0);
            {

                Element powerTakeOffs = doc.createElement("powerTakeOffs");
                rootElement.appendChild(powerTakeOffs);

                {
                    String item="input";
                    Element input = doc.createElement(item);
                    powerTakeOffs.appendChild(input);
                    input.setAttribute("inputAttacherJointIndices", "1");
                    input.setAttribute("inputNode", mapper.getMappedItem(item+"inputNode").getValue());
                    input.setAttribute("aboveAttacher", "true");
                    input.setAttribute("ptoDetachNode", mapper.getMappedItem(item+"PtoDetachNode").getValue());

                    Element objectChange = doc.createElement("objectChange");
                    input.appendChild(objectChange);
                    objectChange.setAttribute("node", mapper.getMappedItem(item+"PtoDetachObjectChangeNode").getValue());
                    objectChange.setAttribute("rotationActive", "0 0 0");
                    objectChange.setAttribute("rotationInactive", "-90 -90 -90");


                }
            }
        }
    }
}