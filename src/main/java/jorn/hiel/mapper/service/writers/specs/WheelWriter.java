package jorn.hiel.mapper.service.writers.specs;

import jorn.hiel.mapper.service.ConfigFileReader;
import jorn.hiel.mapper.service.I3DMapper;
import jorn.hiel.mapper.service.enums.VehicleSpec;
import jorn.hiel.mapper.service.helpers.NeedToWrite;
import jorn.hiel.mapper.service.interfaces.DocWriter;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
@Component
@Slf4j
public class WheelWriter implements DocWriter {

    //todo aanpassen naar inlezen etc

    @Autowired
    I3DMapper mapper;

    @Autowired
    NeedToWrite needToWrite;

    @Autowired
    ConfigFileReader configFileReader;


    @Override
    public void write(Document doc) {

        if (needToWrite.needsToWrite(VehicleSpec.WHEELS)) {
            Node rootElement = doc.getElementsByTagName("vehicle").item(0);
            {

                Element wheels = doc.createElement("wheels");
                rootElement.appendChild(wheels);
                Element wheelConfigurations = doc.createElement("wheelConfigurations");
                wheels.appendChild(wheelConfigurations);




                String key = "numberOfWheelConfigurations";
                int configurations = Integer.parseInt(configFileReader.getMappedItem(key).getValue());


                if(needToWrite.getI3dSettings().containsKey(key)){
                    log.info("found "+key+ " in i3d, priority over config file");
                    configurations = Integer.parseInt(needToWrite.getI3dSettings().get(key));
                }


                for (int x=0;x<configurations;x++) {
                    Element wheelConfiguration = doc.createElement("wheelConfiguration");
                    wheelConfiguration.setAttribute("name", configFileReader.getMappedItem("wheelConfigurationDefaultName").getValue());
                    wheelConfiguration.setAttribute("price", configFileReader.getMappedItem("wheelConfigurationPrice").getValue());
                    wheelConfiguration.setAttribute("brand", configFileReader.getMappedItem("wheelConfigurationBrand").getValue());
                    wheelConfigurations.appendChild(wheelConfiguration);
                    Element configWheels = doc.createElement("wheels");
                    configWheels.setAttribute("autoRotateBackSpeed", configFileReader.getMappedItem("autoRotateBackSpeed").getValue());
                    wheelConfiguration.appendChild(configWheels);


                    int wheelAmount = Integer.parseInt(configFileReader.getMappedItem("numberOfWheels").getValue());
                    int numberOfWheels=needToWrite.getNumberOfWheels();
                    if (numberOfWheels==0){numberOfWheels=wheelAmount;}


                    for (int i = 0; i < numberOfWheels; i++) {
                        Element wheel = doc.createElement("wheel");
                        needToWrite.decide("wheelFileName",wheel,"filename");

                        wheel.setAttribute("hasTireTracks", configFileReader.getMappedItem("wheelTyreTracks").getValue());
                        wheel.setAttribute("hasParticles", configFileReader.getMappedItem("wheelHasParticles").getValue());

                        Element physics = doc.createElement("physics");
                        physics.setAttribute("rotSpeed", configFileReader.getMappedItem("wheelRotSpeed").getValue());
                        physics.setAttribute("restLoad", configFileReader.getMappedItem("wheelRestLoad").getValue());

                        if(needToWrite.getWheels()!=null && needToWrite.getWheels().size()>0 ) {

                            physics.setAttribute("repr", needToWrite.getWheels().get(i).getNode());
                            physics.setAttribute("driveNode", needToWrite.getWheelsDrive().get(i).getNode());
                            wheel.setAttribute("isLeft", needToWrite.getWheelsLeftRight().get(i));
                        }

                        else{
                            physics.setAttribute("repr", configFileReader.getMappedItem("wheelRepr").getValue());
                            physics.setAttribute("driveNode", configFileReader.getMappedItem("wheelDriveNode").getValue());
                            wheel.setAttribute("isLeft", configFileReader.getMappedItem("isLeft").getValue());
                        }

                        physics.setAttribute("suspTravel", configFileReader.getMappedItem("wheelSuspTravel").getValue());
                        physics.setAttribute("spring", configFileReader.getMappedItem("wheelSpring").getValue());
                        physics.setAttribute("damper", configFileReader.getMappedItem("wheelDamper").getValue());
                        physics.setAttribute("frictionScale", configFileReader.getMappedItem("wheelFrictionScale").getValue());


                        configWheels.appendChild(wheel);
                        wheel.appendChild(physics);
                    }




                }

                Element hubs = doc.createElement("hubs");
                wheels.appendChild(hubs);
                Element color0 = doc.createElement("color0");
                Element color1 = doc.createElement("color1");

                hubs.appendChild(color0);
                color0.setTextContent(mapper.getMappedItem("HubColor0").getValue());
                hubs.appendChild(color1);
                color1.setTextContent(mapper.getMappedItem("HubColor1").getValue());

                int hubsneeded = Integer.parseInt(configFileReader.getMappedItem("numberOfHubs").getValue());
                for (int x=0;x<hubsneeded;x++) {
                    String item="hub";
                    Element hub = doc.createElement(item);
                    hubs.appendChild(hub);
                    hub.setAttribute("linkNode", mapper.getMappedItem(item+x+"LinkNode").getValue());
                    hub.setAttribute("filename", mapper.getMappedItem(item+x+"FileName").getValue());
                    hub.setAttribute("isLeft", mapper.getMappedItem(item+x+"isLeft").getValue());
                    hub.setAttribute("scale", "1 1 1");



                }



                Element ackermannSteeringConfigurations = doc.createElement("ackermannSteeringConfigurations");
                wheels.appendChild(ackermannSteeringConfigurations);
                Element ackermannSteering = doc.createElement("ackermannSteering");
                ackermannSteeringConfigurations.appendChild(ackermannSteering);
                ackermannSteering.setAttribute("rotSpeed", configFileReader.getMappedItem("ackerManRotSpeed").getValue());
                ackermannSteering.setAttribute("rotMax", configFileReader.getMappedItem("ackerManRotMax").getValue());
                ackermannSteering.setAttribute("rotCenterWheel1", configFileReader.getMappedItem("ackerManCenterWheel1").getValue());
                ackermannSteering.setAttribute("rotCenterWheel2", configFileReader.getMappedItem("ackerManCenterWheel2").getValue());


            }
        }
    }
}
