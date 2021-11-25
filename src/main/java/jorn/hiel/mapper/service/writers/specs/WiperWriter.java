package jorn.hiel.mapper.service.writers.specs;

import jorn.hiel.mapper.service.ConfigFileReader;
import jorn.hiel.mapper.service.enums.VehicleSpec;
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
        //fs19 = ok
        //fs22 = ok

        if (needToWrite.needsToWrite(VehicleSpec.WIPERS)) {

            String animName=configFileReader.getMappedItem("wiperAnimName").getValue();
            String unknown = configFileReader.getMappedItem("UnknownEntry").getValue();

            if(animName.equals(unknown)){animName="WiperAnim";}


            Node rootElement = doc.getElementsByTagName("vehicle").item(0);

            Element wipers = doc.createElement("wipers");
            Element wiper = doc.createElement("wiper");
            wiper.setAttribute("animName", animName);
            Element stateA = doc.createElement("state");
            Element stateB = doc.createElement("state");
            Element stateC = doc.createElement("state");
            List<Element> states = List.of(stateA, stateB, stateC);
            states.forEach(a -> a.setAttribute("animSpeed", "1.0"));
            states.forEach(a -> a.setAttribute("animPause", "1.0"));
            states.forEach(wiper::appendChild);

            configFileReader.addAnimation(animName,"");

            wipers.appendChild(wiper);
            rootElement.appendChild(wipers);


        }

    }






}
