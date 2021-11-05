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

import java.util.List;

@Component
public class BaleLoaderWriter implements DocWriter {

    @Autowired
    I3DMapper mapper;

    @Autowired
    NeedToWrite needToWrite;

    @Autowired
    ConfigFileReader configFileReader;


    @Override
    public void write(Document doc) {

        if (needToWrite.needsToWrite("baleLoader")) {
            Node rootElement = doc.getElementsByTagName("Vehicle").item(0);
            {

                Element baleLoader = doc.createElement("baleLoader");
                rootElement.appendChild(baleLoader);

                Element grabber = doc.createElement("grabber");
                baleLoader.appendChild(grabber);
                grabber.setAttribute("grabNode", mapper.getMappedItem("baleGrabNode").getValue());
                grabber.setAttribute("triggerNode", mapper.getMappedItem("baleTriggerNode").getValue());
                Element grabParticleSystem = doc.createElement("grabParticleSystem");
                grabber.appendChild(grabParticleSystem);
                grabParticleSystem.setAttribute("disableDuration",configFileReader.getMappedItem("baleParticleDisableDuration").getValue());
                grabParticleSystem.setAttribute("position",configFileReader.getMappedItem("baleParticlePosition").getValue());
                grabParticleSystem.setAttribute("rotation",configFileReader.getMappedItem("baleParticleRotation").getValue());
                grabParticleSystem.setAttribute("file",configFileReader.getMappedItem("baleParticleFile").getValue());

                Element balePlaces = doc.createElement("balePlaces");
                baleLoader.appendChild(balePlaces);
                balePlaces.setAttribute("startBalePlace",mapper.getMappedItem("baleStartBalePlace").getValue());

                int needed = Integer.parseInt(configFileReader.getMappedItem("numberOfBalePlaces").getValue());

                for (int x=0;x<needed;x++) {
                    String item = "balePlace";
                    Element balePlace = doc.createElement(item);
                    balePlace.setAttribute("node", mapper.getMappedItem(item + x).getValue());
                    balePlaces.appendChild(balePlace);
                }

                Element baleTypes = doc.createElement("baleTypes");
                baleLoader.appendChild(baleTypes);
                Element baleType = doc.createElement("baleType");
                baleTypes.appendChild(baleType);
                baleType.setAttribute("minBaleWidth",configFileReader.getMappedItem("baleTypeMinBaleWidth").getValue());
                baleType.setAttribute("maxBaleWidth",configFileReader.getMappedItem("baleTypeMaxBaleWidth").getValue());
                baleType.setAttribute("minBaleHeight",configFileReader.getMappedItem("baleTypeMinBaleHeight").getValue());
                baleType.setAttribute("maxBaleHeight",configFileReader.getMappedItem("baleTypeMaxBaleHeight").getValue());
                baleType.setAttribute("minBaleLength",configFileReader.getMappedItem("baleTypeMinBaleLength").getValue());
                baleType.setAttribute("maxBaleLength",configFileReader.getMappedItem("baleTypeMaxBaleLength").getValue());

                String animName;
                String unknown = configFileReader.getMappedItem("UnknownEntry").getValue();


                List<String>animations = List.of("baleGrabberWorkToDrop","baleGrabberDropBale","baleGrabberTransportToWork","frontBalePusher","balesToOtherRow",
                        "rotatePlatform","rotatePlatformBack","emptyRotate","moveBalePlaces","moveBalePlacesToEmpty","moveBalePusherToEmpty","emptyHidePusher1",
                        "releaseFrontplattform","closeGrippers" );

                for(String animationName :animations) {

                    animName = configFileReader.getMappedItem(animationName).getValue();

                    if (animName.equals(unknown)) {
                        animName = animationName;
                    }
                    configFileReader.addAnimation(animName, "AddComment:BaleLoader animation - required ");
                }






            }
        }
    }
}
