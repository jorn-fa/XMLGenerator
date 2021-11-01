package jorn.hiel.mapper.service.writers;

import jorn.hiel.mapper.service.ConfigFileReader;
import jorn.hiel.mapper.service.I3DMapper;
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

    @Autowired
    I3DMapper mapper;




    List<Element> toAdd;


    public void write(Document doc){



        if (needToWrite.needsToWrite("enterable")) {
            toAdd=new ArrayList<>();

            Node rootElement = doc.getElementsByTagName("Vehicle").item(0);

            Element enterable = doc.createElement("enterable");
            enterable.setAttribute("isTabbable","true");

            Element enterReferenceNode = doc.createElement("enterReferenceNode");
            toAdd.add(enterReferenceNode);
            enterReferenceNode.setAttribute("node",mapper.getMappedItem("enterReferenceNode").getValue());

            Element enterAnimation = doc.createElement("enterAnimation");

            String animName=configFileReader.getMappedItem("enterAnimation").getValue();
            String unknown = configFileReader.getMappedItem("UnknownEntry").getValue();

            enterAnimation.setAttribute("name",animName);

            if(animName.equals(unknown)){animName="enterAnimation";}
            configFileReader.addAnimation(animName,"");

            toAdd.add(enterAnimation);



            Element exitPoint = doc.createElement("exitPoint");
            toAdd.add(exitPoint);
            exitPoint.setAttribute("node",mapper.getMappedItem("exit").getValue());

            Element cameras = doc.createElement("cameras");
            toAdd.add(cameras);
            Element outdoorCamera = doc.createElement("camera");
            cameras.appendChild(outdoorCamera);
            outdoorCamera.setAttribute("node",mapper.getMappedItem("outdoorCamera").getValue());
            outdoorCamera.setAttribute("rotatable",mapper.getMappedItem("outdoorRotatable").getValue());
            outdoorCamera.setAttribute("rotateNode",mapper.getMappedItem("outdoorRotateNode").getValue());

            List<String> names = List.of("limit","useWorldXZRotation", "rotMinX","rotMaxX","transMin","transMax","translation","rotation");
            names.forEach(a-> outdoorCamera.setAttribute(a,mapper.getMappedItem("UnknownEntry").getValue()));
            Element raycastNode1=doc.createElement("raycastNode");
            raycastNode1.setAttribute("node",mapper.getMappedItem("cameraRaycastNode1").getValue());
            Element raycastNode2=doc.createElement("raycastNode");
            raycastNode2.setAttribute("node",mapper.getMappedItem("cameraRaycastNode2").getValue());
            Element raycastNode3=doc.createElement("raycastNode");
            raycastNode3.setAttribute("node",mapper.getMappedItem("cameraRaycastNode3").getValue());
            List<Element> states = List.of(raycastNode1, raycastNode2, raycastNode3);
            states.forEach(a-> outdoorCamera.appendChild(a));

            Element indoorCamera = doc.createElement("camera");
            cameras.appendChild(indoorCamera);
            indoorCamera.setAttribute("node",mapper.getMappedItem("indoorCamera").getValue());
            indoorCamera.setAttribute("rotatable",mapper.getMappedItem("indoorRotatable").getValue());
            names = List.of("limit","useWorldXZRotation", "rotMinX","rotMaxX","transMin","transMax","isInside");
            names.forEach(a-> indoorCamera.setAttribute(a,mapper.getMappedItem("UnknownEntry").getValue()));
            indoorCamera.setAttribute("shadowFocusBox",mapper.getMappedItem("shadowFocusBox").getValue());

            Element mirrors = doc.createElement("mirrors");
            toAdd.add(mirrors);





            int needed = Integer.valueOf(configFileReader.getMappedItem("numberOfMirrors").getValue());

            for (int x=0;x<needed;x++) {
                Element mirror = doc.createElement("mirror");
                mirror.setAttribute("node", mapper.getMappedItem("mirror"+x).getValue());
                mirror.setAttribute("prio", mapper.getMappedItem("mirror"+x+"prior").getValue());


                mirrors.appendChild(mirror);
            }




            //add elements to parent
            toAdd.forEach(a-> enterable.appendChild(a));
            rootElement.appendChild(enterable);
            //add character xml
            characterWriter.write(doc);

        }
    }

}
