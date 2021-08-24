package jorn.hiel.mapper.service.writers;

import jorn.hiel.mapper.service.ConfigFileReader;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;

import java.util.List;

@Service
public class WiperWriter {

    @Autowired
    ConfigFileReader configFileReader;

    public void write(Document doc){

        Node rootElement = doc.getElementsByTagName("Vehicle").item(0);

        Element wipers = doc.createElement("wipers");
        Element wiper = doc.createElement("wiper");
        wiper.setAttribute("animName",configFileReader.getMappedItem("wiperAnimName").getValue());
        Element stateA= doc.createElement("state");
        Element stateB= doc.createElement("state");
        Element stateC= doc.createElement("state");
        List<Element> states = List.of(stateA, stateB, stateC);
        states.forEach(a-> a.setAttribute("animSpeed","1"));
        states.forEach(a-> a.setAttribute("animPause","1"));
        states.forEach(a-> wiper.appendChild(a));

        wipers.appendChild(wiper);
        rootElement.appendChild(wipers);

    }
}
