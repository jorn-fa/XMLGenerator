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
public class ConnectionHoseWriter implements DocWriter {

    @Autowired
    I3DMapper mapper;

    @Autowired
    NeedToWrite needToWrite;

    @Autowired
    ConfigFileReader configFileReader;


    @Override
    public void write(Document doc) {

        if (needToWrite.needsToWrite("connectionHoses")) {
            Node rootElement = doc.getElementsByTagName("Vehicle").item(0);
            {

                Element connectionHoses = doc.createElement("connectionHoses");
                rootElement.appendChild(connectionHoses);
                {
                    int needed = Integer.parseInt(configFileReader.getMappedItem("numberOfConnectionHoses").getValue());

                    for (int x=0;x<needed;x++) {
                        String item="hose";
                        Element hose = doc.createElement(item);
                        hose.setAttribute("inputAttacherJointIndices", "1");
                        hose.setAttribute("type", mapper.getMappedItem("connectionHoseType"+x).getValue());
                        hose.setAttribute("node", mapper.getMappedItem("connectionHose"+x+"Node").getValue());
                        hose.setAttribute("length", mapper.getMappedItem("connectionHose"+x+"Length").getValue());
                        hose.setAttribute("diameter", configFileReader.getMappedItem("connectionHoseDiameter").getValue());
                        hose.setAttribute("straighteningFactor", configFileReader.getMappedItem("connectionHoseStraightening").getValue());

                        connectionHoses.appendChild(hose);
                    }
                }
            }
        }
    }
}