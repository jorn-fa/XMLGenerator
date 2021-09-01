package jorn.hiel.mapper.service.writers;

import jorn.hiel.mapper.service.ConfigFileReader;
import jorn.hiel.mapper.service.helpers.NeedToWrite;
import jorn.hiel.mapper.service.interfaces.DocWriter;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;

import java.util.List;

@Service
@Slf4j
public class WiperWriter implements DocWriter {

    @Autowired
    ConfigFileReader configFileReader;

    @Autowired
    NeedToWrite needToWrite;

    public void write(Document doc){

        if (needToWrite.needsToWrite("needsWipers")) {

            Node rootElement = doc.getElementsByTagName("Vehicle").item(0);

            Element wipers = doc.createElement("wipers");
            Element wiper = doc.createElement("wiper");
            wiper.setAttribute("animName", configFileReader.getMappedItem("wiperAnimName").getValue());
            Element stateA = doc.createElement("state");
            Element stateB = doc.createElement("state");
            Element stateC = doc.createElement("state");
            List<Element> states = List.of(stateA, stateB, stateC);
            states.forEach(a -> a.setAttribute("animSpeed", "1"));
            states.forEach(a -> a.setAttribute("animPause", "1"));
            states.forEach(a -> wiper.appendChild(a));




            Node animations = doc.getElementsByTagName("animations").item(0);
            //create when not found
            if (animations==null){animations=doc.createElement("animations");}

            Element animation = doc.createElement("animation");
            animation.setAttribute("name",configFileReader.getMappedItem("wiperAnimName").getValue());


            Element part = doc.createElement("part");
            part.setAttribute("node",configFileReader.getMappedItem("wiper1").getValue());

            //animition attributes
            List<String> names = List.of("startTime","endTime","startRot","endRot");
            names.forEach(a-> part.setAttribute(a, configFileReader.getMappedItem("UnknownEntry").getValue()));

            animation.appendChild(part);
            animations.appendChild(animation);



            wipers.appendChild(wiper);
            rootElement.appendChild(wipers);
            rootElement.appendChild(animations);

        }

    }






}
