package jorn.hiel.mapper.service.writers.fs19;

import jorn.hiel.mapper.service.ConfigFileReader;
import jorn.hiel.mapper.service.I3DMapper;
import jorn.hiel.mapper.service.helpers.NeedToWrite;
import jorn.hiel.mapper.service.interfaces.DocWriter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
@Component
public class CultivatorWriter implements DocWriter {

    @Autowired
    I3DMapper mapper;


    @Autowired
    NeedToWrite needToWrite;

    @Autowired
    ConfigFileReader configFileReader;


    @Override
    public void write(Document doc) {

        if (needToWrite.needsToWrite("cultivator")) {
            Node rootElement = doc.getElementsByTagName("Vehicle").item(0);

            Element fertilizingCultivator = doc.createElement("fertilizingCultivator");
            fertilizingCultivator.setAttribute("needsSetIsTurnedOn",configFileReader.getMappedItem("cultivatorNeedsSetIsTurnedOn").getValue());
            rootElement.appendChild(fertilizingCultivator);

            Element cultivator = doc.createElement("cultivator");
            cultivator.setAttribute("isSubsoiler",configFileReader.getMappedItem("cultivatorIsSubSoiler").getValue());
            rootElement.appendChild(cultivator);
            Element sounds = doc.createElement("sounds");
            cultivator.appendChild(sounds);
            Element work = doc.createElement("work");
            sounds.appendChild(work);
            work.setAttribute("template","DEFAULT_CULTIVATOR_WORK");
            work.setAttribute("linkNode",mapper.getMainNodeName());
            Element directionNode = doc.createElement("cultivator");
            directionNode.setAttribute("node",mapper.getMainNodeName());
            cultivator.appendChild(directionNode);




        }
    }

}
