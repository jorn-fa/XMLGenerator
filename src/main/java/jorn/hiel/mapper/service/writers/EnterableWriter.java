package jorn.hiel.mapper.service.writers;

import jorn.hiel.mapper.service.ConfigFileReader;
import jorn.hiel.mapper.service.helpers.NeedToWrite;
import jorn.hiel.mapper.service.interfaces.DocWriter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;

import java.util.ArrayList;
import java.util.List;

@Service
public class EnterableWriter implements DocWriter {

    @Autowired
    ConfigFileReader configFileReader;

    @Autowired
    NeedToWrite needToWrite;

    @Autowired
    CharacterWriter characterWriter;


    List<Element> toAdd;


    public void write(Document doc){



        if (needToWrite.needsToWrite("enterable")) {
            toAdd=new ArrayList<>();

            Node rootElement = doc.getElementsByTagName("Vehicle").item(0);

            Element enterable = doc.createElement("enterable");
            enterable.setAttribute("isTabbable","true");

            Element enterReferenceNode = doc.createElement("enterReferenceNode");
            toAdd.add(enterReferenceNode);
            enterReferenceNode.setAttribute("node",configFileReader.getMappedItem("enterReferenceNode").getValue());

            Element enterAnimation = doc.createElement("enterAnimation");
            enterAnimation.setAttribute("name",configFileReader.getMappedItem("enterAnimation").getValue());
            toAdd.add(enterAnimation);



            Element exitPoint = doc.createElement("exitPoint");
            toAdd.add(exitPoint);
            exitPoint.setAttribute("node",configFileReader.getMappedItem("exit").getValue());

            Element cameras = doc.createElement("cameras");
            toAdd.add(cameras);
            Element outdoorCamera = doc.createElement("camera");
            cameras.appendChild(outdoorCamera);
            outdoorCamera.setAttribute("node",configFileReader.getMappedItem("outdoorCamera").getValue());
            outdoorCamera.setAttribute("rotatable",configFileReader.getMappedItem("outdoorRotatable").getValue());
            outdoorCamera.setAttribute("rotateNode",configFileReader.getMappedItem("outdoorRotateNode").getValue());

            List<String> names = List.of("limit","useWorldXZRotation", "rotMinX","rotMaxX","transMin","transMax","translation","rotation");
            names.forEach(a-> outdoorCamera.setAttribute(a,configFileReader.getMappedItem("UnknownEntry").getValue()));
            Element raycastNode1=doc.createElement("raycastNode");
            raycastNode1.setAttribute("node",configFileReader.getMappedItem("cameraRaycastNode1").getValue());
            Element raycastNode2=doc.createElement("raycastNode");
            raycastNode2.setAttribute("node",configFileReader.getMappedItem("cameraRaycastNode2").getValue());
            Element raycastNode3=doc.createElement("raycastNode");
            raycastNode3.setAttribute("node",configFileReader.getMappedItem("cameraRaycastNode3").getValue());
            List<Element> states = List.of(raycastNode1, raycastNode2, raycastNode3);
            states.forEach(a-> outdoorCamera.appendChild(a));

            Element indoorCamera = doc.createElement("camera");
            cameras.appendChild(indoorCamera);
            indoorCamera.setAttribute("node",configFileReader.getMappedItem("indoorCamera").getValue());
            indoorCamera.setAttribute("rotatable",configFileReader.getMappedItem("indoorRotatable").getValue());
            names = List.of("limit","useWorldXZRotation", "rotMinX","rotMaxX","transMin","transMax","isInside");
            names.forEach(a-> indoorCamera.setAttribute(a,configFileReader.getMappedItem("UnknownEntry").getValue()));
            indoorCamera.setAttribute("shadowFocusBox",configFileReader.getMappedItem("shadowFocusBox").getValue());







            //add elements to parent
            toAdd.forEach(a-> enterable.appendChild(a));
            rootElement.appendChild(enterable);
            //add character xml
            characterWriter.write(doc);

        }
    }

}
