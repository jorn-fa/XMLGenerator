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
public class SprayerWriter implements DocWriter {

    @Autowired
    I3DMapper mapper;


    @Autowired
    NeedToWrite needToWrite;

    @Autowired
    ConfigFileReader configFileReader;


    @Override
    public void write(Document doc) {

        if (needToWrite.needsToWrite("sprayer")) {
            Node rootElement = doc.getElementsByTagName("Vehicle").item(0);{

                Element sprayer = doc.createElement("sprayer");
                sprayer.setAttribute("fillUnitIndex",configFileReader.getMappedItem("fillUnitIndex").getValue());
                sprayer.setAttribute("unloadInfoIndex",configFileReader.getMappedItem("unloadInfoIndex").getValue());
                sprayer.setAttribute("loadInfoIndex",configFileReader.getMappedItem("loadInfoIndex").getValue());

                Element usageScales = doc.createElement("usageScales");
                usageScales.setAttribute("workingWidth",configFileReader.getMappedItem("sprayerWorkingWidth").getValue());
                usageScales.setAttribute("scale",configFileReader.getMappedItem("sprayerScale").getValue());

                Element effects = doc.createElement("effects");

                int needed = Integer.parseInt(configFileReader.getMappedItem("numberOfSprayerEffects").getValue());

                for (int x=0;x<needed;x++) {

                    Element effectNode = doc.createElement("effectNode");
                    effectNode.setAttribute("effectNode", configFileReader.getMappedItem("sprayerEffectNode").getValue());
                    effectNode.setAttribute("delay", configFileReader.getMappedItem("sprayerDelay").getValue());
                    effectNode.setAttribute("materialType", configFileReader.getMappedItem("sprayerMaterialType").getValue());
                    effectNode.setAttribute("materialTypeId", configFileReader.getMappedItem("sprayerMaterialTypeId").getValue());
                    effects.appendChild(effectNode);

                }

                Element animationNodes = doc.createElement("animationNodes");
                needed = Integer.parseInt(configFileReader.getMappedItem("numberOfSprayerAnimationNodes").getValue());

                for (int x=0;x<needed;x++) {

                    Element animationNode = doc.createElement("animationNode");
                    animationNode.setAttribute("node", configFileReader.getMappedItem("sprayerAnimationNode"+x).getValue());
                    animationNode.setAttribute("rotSpeed", configFileReader.getMappedItem("sprayerAnimationRotSpeed"+x).getValue());
                    animationNode.setAttribute("rotAxis", configFileReader.getMappedItem("sprayerAnimationRotAxis"+x).getValue());
                    animationNode.setAttribute("turnOnFadeTime", configFileReader.getMappedItem("sprayerAnimationTurnOnFadeTime"+x).getValue());
                    animationNode.setAttribute("turnOffFadeTime", configFileReader.getMappedItem("sprayerAnimationTurnOffFadeTime"+x).getValue());

                    animationNodes.appendChild(animationNode);
                }
                Element sounds = doc.createElement("sounds");
                Element hydraulic = doc.createElement("hydraulic");
                hydraulic.setAttribute("template",configFileReader.getMappedItem("defaultHydraulic").getValue());
                hydraulic.setAttribute("linkNode", mapper.getMainNodeName());


                rootElement.appendChild(sprayer);
                sprayer.appendChild(usageScales);
                sprayer.appendChild(effects);
                sprayer.appendChild(animationNodes);
                sprayer.appendChild(sounds);
                sounds.appendChild(hydraulic);
                }
        }
    }
}
