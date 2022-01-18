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
public class PowerTakeOffWriter implements DocWriter {

    @Autowired
    I3DMapper mapper;

    @Autowired
    NeedToWrite needToWrite;

    @Autowired
    ConfigFileReader configFileReader;


    @Override
    public void write(Document doc) {

        if (needToWrite.needsToWrite(VehicleSpec.POWERTAKEOFFS)) {
            Node rootElement = doc.getElementsByTagName("vehicle").item(0);
            {

                Element powerTakeOffs = doc.createElement("powerTakeOffs");
                rootElement.appendChild(powerTakeOffs);

                {
                    //numberOfInputAttacherJoints
                    int needed = Integer.parseInt(configFileReader.getMappedItem("numberOfInputAttacherJoints").getValue());

                    for(int x=1;x<=needed;x++) {
                        String item = "input";
                        Element input = doc.createElement(item);
                        powerTakeOffs.appendChild(input);
                        input.setAttribute("inputAttacherJointIndices", "1");
                        input.setAttribute("inputNode", mapper.getMappedItem(item + "inputNode").getValue());
                        input.setAttribute("aboveAttacher", "true");
                        input.setAttribute("ptoDetachNode", mapper.getMappedItem(item + "PtoDetachNode").getValue());


                        Element objectChange = doc.createElement("objectChange");
                        input.appendChild(objectChange);
                        objectChange.setAttribute("node", mapper.getMappedItem(item + "PtoDetachObjectChangeNode").getValue());
                        objectChange.setAttribute("rotationActive", "0 0 0");
                        objectChange.setAttribute("rotationInactive", "-90 -90 -90");
                    }


                    needed = Integer.parseInt(configFileReader.getMappedItem("numberOfOutputAttacherJoints").getValue());

                    for(int x=1;x<=needed;x++) {
                        Element output = doc.createElement("output");
                        output.setAttribute("attacherJointIndices", mapper.getMappedItem("PtoattacherJointIndices"+x).getValue());
                        output.setAttribute("outputNode", mapper.getMappedItem("PtoOutputoutputNode"+x).getValue());
                        powerTakeOffs.appendChild(output);
                    }

                }
            }
        }
    }
}