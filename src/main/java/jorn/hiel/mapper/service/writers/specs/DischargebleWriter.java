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

@Component
public class DischargebleWriter implements DocWriter {

    @Autowired
    NeedToWrite needToWrite;

    @Autowired
    I3DMapper mapper;

    @Autowired
    ConfigFileReader configFileReader;



    @Override
    public void write(Document doc) {
        if (needToWrite.needsToWrite(VehicleSpec.DISCHARGEABLE)) {

            Node rootElement = doc.getElementsByTagName("vehicle").item(0);
            {
                Element dischargeAble = doc.createElement("dischargeable");
                rootElement.appendChild(dischargeAble);{



                int needed = Integer.parseInt(configFileReader.getMappedItem("numberOfDischargeNodes").getValue());
                for (int x=0;x<needed;x++) {
                    Element dischargeNode = doc.createElement("dischargeNode");
                    dischargeAble.appendChild(dischargeNode);
                    dischargeNode.setAttribute("node", mapper.getMappedItem("disChargeNode" + x).getValue());
                    dischargeNode.setAttribute("emptySpeed", "3000");
                    dischargeNode.setAttribute("fillUnitIndex", "3");
                    dischargeNode.setAttribute("unloadInfoIndex", "3");

                    Element info = doc.createElement("info");
                    Element effects = doc.createElement("effects");
                    dischargeNode.appendChild(info);
                    info.setAttribute("length", configFileReader.getMappedItem("disChargeNodeLength" + x).getValue());
                    info.setAttribute("width", configFileReader.getMappedItem("disChargeNodeWidth" + x).getValue());
                    info.setAttribute("zOffset", configFileReader.getMappedItem("disChargeNodeZOffset" + x).getValue());
                    dischargeNode.appendChild(effects);
                    Element effectNodeGroup = doc.createElement("effectNode");
                    effects.appendChild(effectNodeGroup);
                    effectNodeGroup.setAttribute("effectClass","TipEffect");
                    Element effectParticle = doc.createElement("effectNode");
                    effectNodeGroup.appendChild(effectParticle);
                    effectParticle.setAttribute("effectClass","ParticleEffect");
                    effectParticle.setAttribute("effectNode",mapper.getMappedItem("ParticleEffect").getValue());

                    Element effectGrain = doc.createElement("effectNode");
                    effectNodeGroup.appendChild(effectGrain);
                    effectGrain.setAttribute("effectNode",mapper.getMappedItem("dischargeEffectNode").getValue());
                    effectGrain.setAttribute("extraDistance",configFileReader.getMappedItem("disChargeExtraDistance").getValue());
                    effectGrain.setAttribute("extraDistanceNode",configFileReader.getMappedItem("disChargeExtraDistanceNode").getValue());
                    effectGrain.setAttribute("fadeTime",configFileReader.getMappedItem("fadeTime").getValue());
                    effectGrain.setAttribute("materialType","unloading");
                    effectGrain.setAttribute("materialTypeID","1");

                    Element smoke = doc.createElement("effectNode");
                    effects.appendChild(smoke);
                    smoke.setAttribute("effectNode",mapper.getMappedItem("dischargeEffectSmoke").getValue());
                    smoke.setAttribute("fadeTime",configFileReader.getMappedItem("fadeTime").getValue());
                    smoke.setAttribute("materialType","smoke");
                    smoke.setAttribute("materialTypeID","1");

                    Element trigger = doc.createElement("trigger");
                    dischargeNode.appendChild(trigger);
                    needToWrite.decide("dischargeTrigger",trigger,"node");

                    Element dischargeSound = doc.createElement("dischargeSound");
                    needToWrite.decide("dischargeSound",dischargeSound,"template");
                    dischargeNode.appendChild(dischargeSound);


                }



}}}}}
