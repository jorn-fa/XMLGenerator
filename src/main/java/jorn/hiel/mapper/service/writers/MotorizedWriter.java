package jorn.hiel.mapper.service.writers;

import jorn.hiel.mapper.service.ConfigFileReader;
import jorn.hiel.mapper.service.I3DMapper;
import jorn.hiel.mapper.service.helpers.NeedToWrite;
import jorn.hiel.mapper.service.interfaces.DocWriter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;

import java.util.List;

@Component
public class MotorizedWriter implements DocWriter {

    @Autowired
    NeedToWrite needToWrite;

    @Autowired
    I3DMapper mapper;

    @Autowired
    ConfigFileReader configFileReader;

    @Override
    public void write(Document doc) {
        if (needToWrite.needsToWrite("motorized")) {

            Node rootElement = doc.getElementsByTagName("Vehicle").item(0);

            Element motorized = doc.createElement("motorized");
            rootElement.appendChild(motorized);

            Element consumerConfigurations = doc.createElement("consumerConfigurations");
            motorized.appendChild(consumerConfigurations);
            Element consumerConfiguration = doc.createElement("consumerConfiguration");
            consumerConfigurations.appendChild(consumerConfiguration);




            int needed = Integer.valueOf(configFileReader.getMappedItem("numberOfConsumers").getValue());

            for (int x=0;x<needed;x++) {
                Element consumer = doc.createElement("consumer");
                List<String> sizeList = List.of("fillUnitIndex", "usage", "fillType");
                sizeList.forEach(a -> consumer.setAttribute(a, mapper.getMappedItem("unknownKey").getValue()));
                consumerConfiguration.appendChild(consumer);
            }




        }

    }



}
