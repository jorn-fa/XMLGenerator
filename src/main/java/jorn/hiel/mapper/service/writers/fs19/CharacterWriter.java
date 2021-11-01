package jorn.hiel.mapper.service.writers.fs19;

import jorn.hiel.mapper.service.ConfigFileReader;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;

import java.util.ArrayList;
import java.util.List;

@Service
public class CharacterWriter {

    @Autowired
    ConfigFileReader configFileReader;

    public void write(Document doc){

        List<Element> toAdd = new ArrayList<>();

        Node rootElement = doc.getElementsByTagName("enterable").item(0);

        Element characterNode = doc.createElement("characterNode");
        characterNode.setAttribute("node",configFileReader.getMappedItem("characterNode").getValue());
        characterNode.setAttribute("cameraMinDistance",configFileReader.getMappedItem("cameraMinDistance").getValue());
        characterNode.setAttribute("filename",configFileReader.getMappedItem("CharacterNodeFileName").getValue());
        characterNode.setAttribute("spineRotation","-90 0 90");
        Element rightFoot = doc.createElement("target");
        rightFoot.setAttribute("ikChain","rightFoot");
        rightFoot.setAttribute("targetNode",configFileReader.getMappedItem("rightFoot").getValue());
        toAdd.add(rightFoot);

        Element leftFoot = doc.createElement("target");
        leftFoot.setAttribute("ikChain","leftFoot");
        leftFoot.setAttribute("targetNode",configFileReader.getMappedItem("leftFoot").getValue());
        toAdd.add(leftFoot);

        Element rightArm = doc.createElement("target");
        rightArm.setAttribute("ikChain","rightArm");
        rightArm.setAttribute("poseId","wideFingers");
        rightArm.setAttribute("targetNode",configFileReader.getMappedItem("rightArm").getValue());
        toAdd.add(rightArm);

        Element leftArm = doc.createElement("target");
        leftArm.setAttribute("ikChain","leftArm");
        leftArm.setAttribute("poseId","wideFingers");
        leftArm.setAttribute("targetNode",configFileReader.getMappedItem("leftArm").getValue());
        toAdd.add(leftArm);

        toAdd.forEach(a-> characterNode.appendChild(a));




        rootElement.appendChild(characterNode);



    }

}
