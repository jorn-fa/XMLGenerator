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
public class SmallStuffWriter implements DocWriter {
    @Autowired
    I3DMapper mapper;

    public void write(Document doc){



        Node rootElement = doc.getElementsByTagName("Vehicle").item(0);

        Element wear = doc.createElement("wearable");
        Element wash = doc.createElement("wash");
        Element bunkerSiloCompacter = doc.createElement("bunkerSiloCompacter");
        Element honk = doc.createElement("honk");
        Element foliage = doc.createElement("foliageBending");



        List<String> wearList = List.of("fieldMultiplier","wearDuration","workMultiplier");
        wearList.forEach(a-> wear.setAttribute(a, mapper.getMappedItem(a).getValue()));

        List<String> washList = List.of("fieldMultiplier","washDuration","workMultiplier","dirtDuration");
        washList.forEach(a-> wash.setAttribute(a, mapper.getMappedItem(a).getValue()));

        bunkerSiloCompacter.setAttribute("compactingScale",mapper.getMappedItem("bunkerSiloCompacter").getValue());

        Element sound = doc.createElement("sound");
        honk.appendChild(sound);
        sound.setAttribute("template",mapper.getMappedItem("honk_template").getValue());
        sound.setAttribute("linkNode",mapper.getMappedItem("honk_linknode").getValue());

        Element bendingNode=doc.createElement("bendingNode");
        List<String>sizeList = List.of("minX","maxX","minZ","maxZ","yOffset");
        sizeList.forEach(a-> bendingNode.setAttribute(a, "0.0"));
        foliage.appendChild(bendingNode);

        List<Element>elements = List.of(wear,wash,bunkerSiloCompacter,honk,foliage);
        elements.forEach(a-> rootElement.appendChild(a));

        Element fillFromAir = doc.createElement("allowFillFromAir");
        fillFromAir.setAttribute("value","true");
        Element supportsFillTriggers  = doc.createElement("supportsFillTriggers");
        supportsFillTriggers.setAttribute("value","true");

        rootElement.appendChild(fillFromAir);
        rootElement.appendChild(supportsFillTriggers);


    }

}
