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
public class LightWriter implements DocWriter {

    @Autowired
    I3DMapper mapper;

    @Autowired
    NeedToWrite needToWrite;

    @Autowired
    ConfigFileReader configFileReader;

    @Override
    public void write(Document doc) {
        if (needToWrite.needsToWrite("lights")) {
            Node rootElement = doc.getElementsByTagName("Vehicle").item(0);
            {

                Element lights = doc.createElement("lights");
                int needed = Integer.parseInt(configFileReader.getMappedItem("numberOfSharedLights").getValue());

                for (int x=0;x<needed;x++) {
                    String item="sharedLight";
                    Element sharedLight = doc.createElement(item);
                    lights.appendChild(sharedLight);
                    sharedLight.setAttribute("linkNode", mapper.getMappedItem(item+x+"linkNode").getValue());
                    sharedLight.setAttribute("lightTypes", mapper.getMappedItem(item+x+"lightTypes").getValue());
                    sharedLight.setAttribute("intensity", mapper.getMappedItem(item+x+"intensity").getValue());
                    sharedLight.setAttribute("filename", mapper.getMappedItem(item+x+"filename").getValue());

                }
                Element states = doc.createElement("states");

                needed = Integer.parseInt(configFileReader.getMappedItem("numberOfLightsStates").getValue());
                for (int x=0;x<needed;x++) {
                    Element state = doc.createElement("state");
                    //generate the sequential states ( ie   0 1 2 3 )
                    String insert = "";
                    for(int y=0;y<=x;y++){insert = insert +" " + y;}
                    state.setAttribute("lightTypes", insert.trim());
                    states.appendChild(state);
                }
                rootElement.appendChild(lights);
                lights.appendChild(states);


                Element realLights = doc.createElement("realLights");
                rootElement.appendChild(realLights);
                Element low = doc.createElement("low");
                Element high = doc.createElement("high");
                realLights.appendChild(low);

                needed = Integer.parseInt(configFileReader.getMappedItem("numberOfLowLights").getValue());
                for (int x=0;x<needed;x++) {
                    Element light = doc.createElement("light");
                    String item="sharedLight";
                    light.setAttribute("node", mapper.getMappedItem(item+x+"node").getValue());
                    light.setAttribute("lightTypes", mapper.getMappedItem(item+x+"lightTypes").getValue());
                    light.setAttribute("excludedLightTypes", mapper.getMappedItem(item+x+"excludedLightTypes").getValue());
                    low.appendChild(light);
                }


                realLights.appendChild(high);
                needed = Integer.parseInt(configFileReader.getMappedItem("numberOfHighLights").getValue());
                for (int x=0;x<needed;x++) {
                    Element light = doc.createElement("light");
                    String item="sharedLight";
                    light.setAttribute("node", mapper.getMappedItem(item+x+"node").getValue());
                    light.setAttribute("lightTypes", mapper.getMappedItem(item+x+"lightTypes").getValue());
                    light.setAttribute("excludedLightTypes", mapper.getMappedItem(item+x+"excludedLightTypes").getValue());
                    high.appendChild(light);
                }



            }
        }
    }
}
