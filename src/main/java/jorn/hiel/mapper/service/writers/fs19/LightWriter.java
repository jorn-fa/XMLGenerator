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
                    StringBuilder insert = new StringBuilder();
                    for(int y=0;y<=x;y++){insert.append(" " + y);}
                    state.setAttribute("lightTypes", insert.toString().trim());
                    states.appendChild(state);
                }
                rootElement.appendChild(lights);
                lights.appendChild(states);


                Element realLights = doc.createElement("realLights");
                lights.appendChild(realLights);
                Element low = doc.createElement("low");
                Element high = doc.createElement("high");
                realLights.appendChild(low);

                needed = Integer.parseInt(configFileReader.getMappedItem("numberOfLowLights").getValue());
                for (int x=0;x<needed;x++) {
                    Element light = doc.createElement("light");
                    String item="lowLight";
                    light.setAttribute("node", mapper.getMappedItem(item+x+"node").getValue());
                    light.setAttribute("lightTypes", mapper.getMappedItem(item+x+"lightTypes").getValue());
                    light.setAttribute("excludedLightTypes", mapper.getMappedItem(item+x+"excludedLightTypes").getValue());
                    low.appendChild(light);
                }


                realLights.appendChild(high);
                needed = Integer.parseInt(configFileReader.getMappedItem("numberOfHighLights").getValue());
                for (int x=0;x<needed;x++) {
                    Element light = doc.createElement("light");
                    String item="highLight";
                    light.setAttribute("node", mapper.getMappedItem(item+x+"node").getValue());
                    light.setAttribute("lightTypes", mapper.getMappedItem(item+x+"lightTypes").getValue());
                    light.setAttribute("excludedLightTypes", mapper.getMappedItem(item+x+"excludedLightTypes").getValue());
                    high.appendChild(light);
                }

                needed = Integer.parseInt(configFileReader.getMappedItem("numberOfBrakeLights").getValue());
                for (int x=0;x<needed;x++) {
                    Element light = doc.createElement("brakeLight");
                    String item="brakeLight";
                    light.setAttribute("node", mapper.getMappedItem(item+x+"node").getValue());
                    high.appendChild(light);
                }

                needed = Integer.parseInt(configFileReader.getMappedItem("numberOfTurnLightLeft").getValue());
                for (int x=0;x<needed;x++) {
                    Element light = doc.createElement("turnLightLeft");
                    String item="turnLightLeft";
                    light.setAttribute("node", mapper.getMappedItem(item+x+"node").getValue());
                    high.appendChild(light);
                }

                needed = Integer.parseInt(configFileReader.getMappedItem("numberOfTurnLightRight").getValue());
                for (int x=0;x<needed;x++) {
                    Element light = doc.createElement("turnLightRight");
                    String item="turnLightRight";
                    light.setAttribute("node", mapper.getMappedItem(item+x+"node").getValue());
                    high.appendChild(light);
                }

                needed = Integer.parseInt(configFileReader.getMappedItem("numberOfReverseLight").getValue());
                for (int x=0;x<needed;x++) {
                    Element light = doc.createElement("reverseLight");
                    String item="reverseLight";
                    light.setAttribute("node", mapper.getMappedItem(item+x+"node").getValue());
                    high.appendChild(light);
                }

                needed = Integer.parseInt(configFileReader.getMappedItem("numberOfInteriorLight").getValue());
                for (int x=0;x<needed;x++) {
                    Element light = doc.createElement("reverseLight");
                    String item="reverseLight";
                    light.setAttribute("node", mapper.getMappedItem(item+x+"node").getValue());
                    high.appendChild(light);
                }
                Element beaconLights = doc.createElement("beaconLights");
                lights.appendChild(beaconLights);
                needed = Integer.parseInt(configFileReader.getMappedItem("numberOfBeaconLight").getValue());
                for (int x=0;x<needed;x++) {
                    String item="beaconLight";
                    Element light = doc.createElement(item);
                    light.setAttribute("node", mapper.getMappedItem(item+x+"node").getValue());
                    light.setAttribute("filename", mapper.getMappedItem(item+x+"fileName").getValue());
                    beaconLights.appendChild(light);
                }



            }
        }
    }
}
