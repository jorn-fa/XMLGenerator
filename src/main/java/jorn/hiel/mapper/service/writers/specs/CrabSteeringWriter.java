package jorn.hiel.mapper.service.writers.specs;

import jorn.hiel.mapper.service.ConfigFileReader;
import jorn.hiel.mapper.service.I3DMapper;
import jorn.hiel.mapper.service.enums.VehicleSpec;
import jorn.hiel.mapper.service.helpers.NeedToWrite;
import jorn.hiel.mapper.service.interfaces.DocWriter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;

@Service
public class CrabSteeringWriter implements DocWriter {

        @Autowired
        I3DMapper mapper;

        @Autowired
        NeedToWrite needToWrite;

        @Autowired
        ConfigFileReader configFileReader;


        @Override
        public void write(Document doc) {

            if (needToWrite.needsToWrite(VehicleSpec.CRABSTEERING)) {




                Node rootElement = doc.getElementsByTagName("vehicle").item(0);
                {
                    Element crabSteering = doc.createElement("crabSteering");
                    rootElement.appendChild(crabSteering);
                    crabSteering.setAttribute("distFromCompJointToCenterOfBackWheels",mapper.getMappedItem("crabSteeringDistFromCompJointToCenterOfBackWheels").getValue());
                    crabSteering.setAttribute("aiSteeringModeIndex",mapper.getMappedItem("crabSteeringModeIndex").getValue());
                    crabSteering.setAttribute("toggleSpeedFactor",mapper.getMappedItem("crabSteeringToggleSpeedFactor").getValue());

                    Element steeringMode = doc.createElement("steeringMode");
                    steeringMode.setAttribute("name", "l10n_action_steeringModeAllWheel");
                    crabSteering.appendChild(steeringMode);

                    int wheelAmount = Integer.parseInt(configFileReader.getMappedItem("numberOfWheels").getValue());

                    for(int x=1;x<=wheelAmount;x++){
                        Element element = doc.createElement("wheel");
                        element.setAttribute("index",x+"");
                        steeringMode.appendChild(element);
                    }
                    Element articulatedAxis = doc.createElement("articulatedAxis");
                    steeringMode.appendChild(articulatedAxis);
                    articulatedAxis.setAttribute("offset",mapper.getMappedItem("crabSteeringAxisOffset").getValue());
                    articulatedAxis.setAttribute("locked","true");


                }
            }
        }

}
