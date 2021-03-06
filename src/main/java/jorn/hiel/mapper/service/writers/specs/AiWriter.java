package jorn.hiel.mapper.service.writers.specs;

import jorn.hiel.mapper.service.ConfigFileReader;
import jorn.hiel.mapper.service.I3DMapper;
import jorn.hiel.mapper.service.enums.GameVersion;
import jorn.hiel.mapper.service.enums.VehicleSpec;
import jorn.hiel.mapper.service.helpers.NeedToWrite;
import jorn.hiel.mapper.service.interfaces.DocWriter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;

import java.util.List;
@Component
public class AiWriter implements DocWriter {

    @Autowired
    I3DMapper mapper;

    @Autowired
    NeedToWrite needToWrite;

    @Autowired
    ConfigFileReader configFileReader;

    @Override
    public void write(Document doc) {
        if (needToWrite.needsToWrite(VehicleSpec.AIVEHICLE)) {
            Node rootElement = doc.getElementsByTagName("vehicle").item(0);
            {
                Element ai = doc.createElement("ai");
                Element needsLowering = doc.createElement("needsLowering");
                needsLowering.setAttribute("value",configFileReader.getMappedItem("needsLowering").getValue());
                Element allowTurnBackward = doc.createElement("allowTurnBackward");
                allowTurnBackward.setAttribute("value",configFileReader.getMappedItem("allowTurnBackward").getValue());
                Element turningRadiusLimitation = doc.createElement("turningRadiusLimitation");
                turningRadiusLimitation.setAttribute("radius",configFileReader.getMappedItem("turningRadiusLimitation").getValue());
                Element toolReverserDirectionNode = doc.createElement("toolReverserDirectionNode");
                toolReverserDirectionNode.setAttribute("node",configFileReader.getMappedItem("toolReverserDirectionNode").getValue());
                Element collisionTrigger = doc.createElement("collisionTrigger");
                collisionTrigger.setAttribute("node",mapper.getMappedItem("aiCollisionTrigger").getValue());
                ai.appendChild(collisionTrigger);

                Element areaMarkers = doc.createElement("areaMarkers");
                areaMarkers.setAttribute("leftNode",mapper.getMappedItem("areaMarkerLeftNode").getValue());
                areaMarkers.setAttribute("rightNode",mapper.getMappedItem("areaMarkerRightNode").getValue());
                areaMarkers.setAttribute("backNode",mapper.getMappedItem("areaMarkerBackNode").getValue());

                Element sizeMarkers = doc.createElement("sizeMarkers");
                sizeMarkers.setAttribute("leftNode",mapper.getMappedItem("sizeMarkerLeftNode").getValue());
                sizeMarkers.setAttribute("rightNode",mapper.getMappedItem("sizeMarkerRightNode").getValue());
                sizeMarkers.setAttribute("backNode",mapper.getMappedItem("sizeMarkerBackNode").getValue());


                List<Element> elements = List.of(needsLowering,allowTurnBackward,turningRadiusLimitation,toolReverserDirectionNode,areaMarkers,sizeMarkers);


                if (needToWrite.isFullWrite() || needToWrite.getGameVersion().equals(GameVersion.FS22)) {
                    Element agent = doc.createElement("agent");
                    agent.setAttribute("width",mapper.getMappedItem("AIagentWidht").getValue());
                    agent.setAttribute("length",mapper.getMappedItem("AIagentLength").getValue());
                    agent.setAttribute("lengthoffset",mapper.getMappedItem("AIagentLengthOffset").getValue());
                    agent.setAttribute("height",mapper.getMappedItem("AIagentHeight").getValue());
                    agent.setAttribute("frontOffset",mapper.getMappedItem("AIagentFrontOffset").getValue());
                    ai.appendChild(agent);
                    collisionTrigger.setAttribute("width",mapper.getMappedItem("AICollisionWidth").getValue());
                    collisionTrigger.setAttribute("height",mapper.getMappedItem("AICollisionHeight").getValue());





                    ai.setAttribute("supportsAIJob","true");


                }


                rootElement.appendChild(ai);
                elements.forEach(ai::appendChild);
            }
        }
    }
}