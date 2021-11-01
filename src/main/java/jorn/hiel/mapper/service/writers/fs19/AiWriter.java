package jorn.hiel.mapper.service.writers.fs19;

import jorn.hiel.mapper.service.ConfigFileReader;
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
    NeedToWrite needToWrite;

    @Autowired
    ConfigFileReader configFileReader;

    @Override
    public void write(Document doc) {
        if (needToWrite.needsToWrite("ai")) {
            Node rootElement = doc.getElementsByTagName("Vehicle").item(0);
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

                Element areaMarkers = doc.createElement("areaMarkers");
                areaMarkers.setAttribute("leftNode",configFileReader.getMappedItem("areaMarkerLeftNode").getValue());
                areaMarkers.setAttribute("rightNode",configFileReader.getMappedItem("areaMarkerRightNode").getValue());
                areaMarkers.setAttribute("backNode",configFileReader.getMappedItem("areaMarkerBackNode").getValue());

                Element sizeMarkers = doc.createElement("sizeMarkers");
                sizeMarkers.setAttribute("leftNode",configFileReader.getMappedItem("sizeMarkerLeftNode").getValue());
                sizeMarkers.setAttribute("rightNode",configFileReader.getMappedItem("sizeMarkerRightNode").getValue());
                sizeMarkers.setAttribute("backNode",configFileReader.getMappedItem("sizeMarkerBackNode").getValue());


                List<Element> elements = List.of(needsLowering,allowTurnBackward,turningRadiusLimitation,toolReverserDirectionNode,areaMarkers,sizeMarkers);

                rootElement.appendChild(ai);
                elements.forEach(a-> ai.appendChild(a));
            }
        }
    }
}