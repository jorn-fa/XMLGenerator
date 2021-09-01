package jorn.hiel.mapper.service.writers;

import jorn.hiel.mapper.service.I3DMapper;
import jorn.hiel.mapper.service.interfaces.DocWriter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;

import java.util.List;

@Service
public class WearAndWashWriter implements DocWriter {
    @Autowired
    I3DMapper mapper;

    public void write(Document doc){

        Node rootElement = doc.getElementsByTagName("Vehicle").item(0);

        Element wear = doc.createElement("wearable");
        Element wash = doc.createElement("wash");
        Element bunkerSiloCompacter = doc.createElement("bunkerSiloCompacter");

        List<String> wearList = List.of("fieldMultiplier","wearDuration","workMultiplier");
        wearList.forEach(a-> wear.setAttribute(a, mapper.getMappedItem(a).getValue()));

        List<String> washList = List.of("fieldMultiplier","washDuration","workMultiplier","dirtDuration");
        washList.forEach(a-> wash.setAttribute(a, mapper.getMappedItem(a).getValue()));

        bunkerSiloCompacter.setAttribute("compactingScale",mapper.getMappedItem("bunkerSiloCompacter").getValue());


        rootElement.appendChild(wear);
        rootElement.appendChild(wash);
        rootElement.appendChild(bunkerSiloCompacter);


    }

}
