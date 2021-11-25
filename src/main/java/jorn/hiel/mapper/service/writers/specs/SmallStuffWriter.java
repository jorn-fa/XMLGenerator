package jorn.hiel.mapper.service.writers.specs;

import jorn.hiel.mapper.service.ConfigFileReader;
import jorn.hiel.mapper.service.I3DMapper;
import jorn.hiel.mapper.service.enums.VehicleSpec;
import jorn.hiel.mapper.service.helpers.NeedToWrite;
import jorn.hiel.mapper.service.interfaces.DocWriter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;

import java.util.List;

@Component
public class SmallStuffWriter implements DocWriter {
    @Autowired
    I3DMapper mapper;

    @Autowired
    NeedToWrite needToWrite;

    @Autowired
    ConfigFileReader configFileReader;

    public void write(Document doc){
            //fs19 = ok
            //fs22 = ok
            if (needToWrite.needsToWrite(VehicleSpec.WIPERS)) {

            Node rootElement = doc.getElementsByTagName("vehicle").item(0);


            Element fillFromAir = doc.createElement("allowFillFromAir");
            fillFromAir.setAttribute("value", "true");
            Element supportsFillTriggers = doc.createElement("supportsFillTriggers");
            supportsFillTriggers.setAttribute("value", "true");

            rootElement.appendChild(fillFromAir);
            rootElement.appendChild(supportsFillTriggers);

            //fs19 = ok
            //fs22 = ok
            if (needToWrite.needsToWrite(VehicleSpec.HONK)) {

                Element honk = doc.createElement("honk");
                Element sound = doc.createElement("sound");
                honk.appendChild(sound);
                sound.setAttribute("template", mapper.getMappedItem("honk_template").getValue());
                sound.setAttribute("linkNode", mapper.getMappedItem("honk_linknode").getValue());
                rootElement.appendChild(honk);

            }
            //fs19 = ok
            //fs22 = ok
            if (needToWrite.needsToWrite(VehicleSpec.BUNKERSILOCOMPACTER)) {
                Element bunkerSiloCompacter = doc.createElement("bunkerSiloCompacter");
                bunkerSiloCompacter.setAttribute("compactingScale", mapper.getMappedItem("bunkerSiloCompacter").getValue());
                rootElement.appendChild(bunkerSiloCompacter);

            }

            //fs19 = ok
            //fs22 = ok
            if (needToWrite.needsToWrite(VehicleSpec.WASHABLE)) {
                Element wash = doc.createElement("wash");
                rootElement.appendChild(wash);
                List<String> washList = List.of("fieldMultiplier", "washDuration", "workMultiplier", "dirtDuration");
                washList.forEach(a -> wash.setAttribute(a, mapper.getMappedItem(a).getValue()));
            }
            //fs19 = ok
            //fs22 = ok
            if (needToWrite.needsToWrite(VehicleSpec.WEARABLE)) {
                Element wear = doc.createElement("wearable");
                rootElement.appendChild(wear);
                List<String> wearList = List.of("fieldMultiplier", "wearDuration", "workMultiplier");
                wearList.forEach(a -> wear.setAttribute(a, mapper.getMappedItem(a).getValue()));

            }

            //fs19 = ok
            //fs22 = ok
            if (needToWrite.needsToWrite(VehicleSpec.FOLIAGEBENDING)) {
                Element foliage = doc.createElement("foliageBending");
                rootElement.appendChild(foliage);
                Element bendingNode = doc.createElement("bendingNode");
                List<String> sizeList = List.of("minX", "maxX", "minZ", "maxZ", "yOffset");
                sizeList.forEach(a -> bendingNode.setAttribute(a, "0.0"));
                foliage.appendChild(bendingNode);

            }



            if (needToWrite.needsToWrite(VehicleSpec.TURNONVEHICLE)) {


                Element turnOnVehicle = doc.createElement("turnOnVehicle");
                turnOnVehicle.setAttribute("turnOffIfNotAllowed", configFileReader.getMappedItem("turnOffIfNotAllowed").getValue());
                Element sounds = doc.createElement("sounds");
                Element work = doc.createElement("work");
                work.setAttribute("template", configFileReader.getMappedItem("workTemplate").getValue());


                turnOnVehicle.appendChild(sounds);
                sounds.appendChild(work);

                rootElement.appendChild(turnOnVehicle);
            }

            if (needToWrite.needsToWrite(VehicleSpec.POWERCONSUMER)) {

                String item = "powerConsumer";
                Element powerConsumer = doc.createElement(item);
                powerConsumer.setAttribute("ptoRpm", configFileReader.getMappedItem("powerConsumerPtoRpm").getValue());
                powerConsumer.setAttribute("neededMinPtoPower", configFileReader.getMappedItem("powerConsumerNeededMinPtoPower").getValue());
                powerConsumer.setAttribute("neededMaxPtoPower", configFileReader.getMappedItem("powerConsumerNeededMaxPtoPower").getValue());


                rootElement.appendChild(powerConsumer);
            }



        }
    }

}
