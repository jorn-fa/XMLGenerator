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
public class WheelWriter implements DocWriter {

    @Autowired
    I3DMapper mapper;


    @Autowired
    NeedToWrite needToWrite;

    @Autowired
    ConfigFileReader configFileReader;


    @Override
    public void write(Document doc) {

        if (needToWrite.needsToWrite("wheels")) {
            Node rootElement = doc.getElementsByTagName("Vehicle").item(0);
            {

                Element wheels = doc.createElement("wheels");
                rootElement.appendChild(wheels);
                Element wheelConfigurations = doc.createElement("wheelConfigurations");
                wheels.appendChild(wheelConfigurations);

                int needed = Integer.valueOf(configFileReader.getMappedItem("numberOfWheelConfigurations").getValue());

                for (int x=0;x<needed;x++) {
                    Element wheelConfiguration = doc.createElement("wheelConfiguration");
                    wheelConfiguration.setAttribute("name", configFileReader.getMappedItem("wheelConfigurationDefaultName").getValue());
                    wheelConfiguration.setAttribute("price", configFileReader.getMappedItem("wheelConfigurationPrice").getValue());
                    wheelConfiguration.setAttribute("brand", configFileReader.getMappedItem("wheelConfigurationBrand").getValue());
                    wheelConfigurations.appendChild(wheelConfiguration);
                    Element configWheels = doc.createElement("wheels");
                    configWheels.setAttribute("autoRotateBackSpeed", configFileReader.getMappedItem("autoRotateBackSpeed").getValue());
                    wheelConfiguration.appendChild(configWheels);

                    int wheelAmount = Integer.valueOf(configFileReader.getMappedItem("numberOfWheelConfigurations").getValue());

                    for (int i = 0; i < wheelAmount; i++) {
                        Element wheel = doc.createElement("wheel");
                        wheel.setAttribute("filename", configFileReader.getMappedItem("wheel"+i+"FileName").getValue());
                        wheel.setAttribute("isLeft", configFileReader.getMappedItem("wheel"+i+"FileName").getValue());
                        wheel.setAttribute("hasTireTracks", configFileReader.getMappedItem("wheel"+i+"FileName").getValue());
                        wheel.setAttribute("hasParticles", configFileReader.getMappedItem("wheel"+i+"FileName").getValue());

                        Element physics = doc.createElement("physics");
                        physics.setAttribute("rotSpeed", configFileReader.getMappedItem("wheel"+i+"rotSpeed").getValue());
                        physics.setAttribute("restLoad", configFileReader.getMappedItem("wheel"+i+"restLoad").getValue());
                        physics.setAttribute("repr", configFileReader.getMappedItem("wheel"+i+"repr").getValue());
                        physics.setAttribute("driveNode", configFileReader.getMappedItem("wheel"+i+"driveNode").getValue());
                        physics.setAttribute("suspTravel", configFileReader.getMappedItem("wheel"+i+"suspTravel").getValue());
                        physics.setAttribute("spring", configFileReader.getMappedItem("wheel"+i+"spring").getValue());
                        physics.setAttribute("damper", configFileReader.getMappedItem("wheel"+i+"damper").getValue());
                        physics.setAttribute("frictionScale", configFileReader.getMappedItem("wheel"+i+"frictionScale").getValue());


                        configWheels.appendChild(wheel);
                        wheel.appendChild(physics);
                    }




                }

            }
        }
    }
}
