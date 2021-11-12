package jorn.hiel.mapper.service.writers.fs19;

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
public class WoodHarvesterWriter implements DocWriter {

    @Autowired
    NeedToWrite needToWrite;

    @Autowired
    I3DMapper mapper;

    @Autowired
    ConfigFileReader configFileReader;

    @Override
    public void write(Document doc) {
        if (needToWrite.needsToWrite(VehicleSpec.WOODHARVESTER)) {

            Node rootElement = doc.getElementsByTagName("Vehicle").item(0);
            Element woodHarvester = doc.createElement("woodHarvester");
            rootElement.appendChild(woodHarvester);

            Element cutNode = doc.createElement("cutNode");
            woodHarvester.appendChild(cutNode);
            cutNode.setAttribute("node", mapper.getMappedItem("WoodHarvesterCutNode").getValue());
            cutNode.setAttribute("attachNode", mapper.getMappedItem("WoodHarvesterCutNodeAttachNode").getValue());
            cutNode.setAttribute("attachReferenceNode", mapper.getMappedItem("WoodHarvesterCutNodeAttachReferenceNode").getValue());
            cutNode.setAttribute("sizeY", mapper.getMappedItem("WoodHarvesterCutNodeSizeY").getValue());
            cutNode.setAttribute("sizeZ", mapper.getMappedItem("WoodHarvesterCutNodeSizeZ").getValue());
            cutNode.setAttribute("maxRadius", mapper.getMappedItem("WoodHarvesterCutNodeMaxRadius").getValue());
            cutNode.setAttribute("releasedComponentJointIndex", mapper.getMappedItem("WoodHarvesterCutNodeReleasedComponentJointIndex").getValue());
            cutNode.setAttribute("releasedComponentJoint2Index", mapper.getMappedItem("WoodHarvesterCutNodeReleasedComponentJoint2Index").getValue());

            Element cutLengths = doc.createElement("cutLengths");
            woodHarvester.appendChild(cutLengths);
            cutLengths.setAttribute("min", mapper.getMappedItem("WoodHarvesterCutLengthsMin").getValue());
            cutLengths.setAttribute("max", mapper.getMappedItem("WoodHarvesterCutLengthsMax").getValue());
            cutLengths.setAttribute("step", mapper.getMappedItem("WoodHarvesterCutLengthsStep").getValue());

            Element delimbNode = doc.createElement("delimbNode");
            woodHarvester.appendChild(delimbNode);
            delimbNode.setAttribute("node", mapper.getMappedItem("WoodHarvesterDelimbNode").getValue());
            delimbNode.setAttribute("sizeX", mapper.getMappedItem("WoodHarvesterDelimbSizeX").getValue());
            delimbNode.setAttribute("sizeY", mapper.getMappedItem("WoodHarvesterDelimbSizeY").getValue());
            delimbNode.setAttribute("sizeZ", mapper.getMappedItem("WoodHarvesterDelimbSizeZ").getValue());
            delimbNode.setAttribute("delimbOnCut", "true");

            Element cutAnimation = doc.createElement("cutAnimation");
            woodHarvester.appendChild(cutAnimation);

            String animName=configFileReader.getMappedItem("WoodHarvesterCutAnimation").getValue();
            String unknown = configFileReader.getMappedItem("UnknownEntry").getValue();
            if(animName.equals(unknown)){animName="cutAnimation";}
            configFileReader.addAnimation(animName,"");
            cutAnimation.setAttribute("name", animName);
            cutAnimation.setAttribute("speedScale", "1");
            cutAnimation.setAttribute("cutTime", "0.5");



            animName=configFileReader.getMappedItem("WoodHarvesterGrabAnimation").getValue();
            if(animName.equals(unknown)){animName="grabberAnimation";}
            configFileReader.addAnimation(animName,"");
            Element grabAnimation = doc.createElement("grabAnimation");
            woodHarvester.appendChild(grabAnimation);
            grabAnimation.setAttribute("name", animName);
            grabAnimation.setAttribute("speedScale", "1");

            Element treeSizeMeasure = doc.createElement("treeSizeMeasure");
            woodHarvester.appendChild(treeSizeMeasure);
            treeSizeMeasure.setAttribute("node", mapper.getMappedItem("woodHarvesterTreeSizeMeasureNode").getValue());
            treeSizeMeasure.setAttribute("rotMaxRadius", mapper.getMappedItem("woodHarvesterTreeSizeMeasureRotMaxRadius").getValue());

            Element cutEffects = doc.createElement("cutEffects");
            woodHarvester.appendChild(cutEffects);
            Element cutEffectWood = doc.createElement("effectNode");
            cutEffects.appendChild(cutEffectWood);
            cutEffectWood.setAttribute("effectClass", "ParticleEffect");
            cutEffectWood.setAttribute("effectNode", mapper.getMappedItem("woodHarvesterCutNodeEffectWood").getValue());
            cutEffectWood.setAttribute("particleType", "CRUSHER_WOOD");
            cutEffectWood.setAttribute("emitCountScale", "100");
            Element cutEffectDust = doc.createElement("effectNode");
            cutEffects.appendChild(cutEffectDust);
            cutEffectDust.setAttribute("effectClass", "ParticleEffect");
            cutEffectDust.setAttribute("effectNode", mapper.getMappedItem("woodHarvesterCutNodeEffectDust").getValue());
            cutEffectDust.setAttribute("particleType", "CRUSHER_DUST");
            cutEffectDust.setAttribute("emitCountScale", "20");

            Element delimbEffects = doc.createElement("delimbEffects");
            woodHarvester.appendChild(delimbEffects);
            Element delimbEffectWood = doc.createElement("effectNode");
            delimbEffects.appendChild(delimbEffectWood);
            delimbEffectWood.setAttribute("effectClass", "ParticleEffect");
            delimbEffectWood.setAttribute("effectNode", mapper.getMappedItem("woodHarvesterCutNodeEffectWood").getValue());
            delimbEffectWood.setAttribute("particleType", "CRUSHER_WOOD");
            delimbEffectWood.setAttribute("emitCountScale", "20");
            Element delimbEffectDust = doc.createElement("effectNode");
            delimbEffects.appendChild(delimbEffectDust);
            delimbEffectDust.setAttribute("effectClass", "ParticleEffect");
            delimbEffectDust.setAttribute("effectNode", mapper.getMappedItem("woodHarvesterCutNodeEffectDust").getValue());
            delimbEffectDust.setAttribute("particleType", "CRUSHER_DUST");
            delimbEffectDust.setAttribute("emitCountScale", "4");

            Element forwardingNodes = doc.createElement("forwardingNodes");
            woodHarvester.appendChild(forwardingNodes);
            Element animationNode = doc.createElement("animationNode");
            forwardingNodes.appendChild(animationNode);
            animationNode.setAttribute("node",  mapper.getMappedItem("woodHarvesterForwardNode").getValue());
            animationNode.setAttribute("rotAxis",  "3");
            animationNode.setAttribute("rotSpeed",  "300");
            animationNode.setAttribute("turnOnFadeTime",  "0");
            animationNode.setAttribute("turnOffFadeTime",  "0");

            Element sounds = doc.createElement("sounds");
            woodHarvester.appendChild(sounds);
            Element delimb = doc.createElement("delimb");
            Element cut = doc.createElement("cut");
            sounds.appendChild(cut);
            cut.setAttribute("template",  "DEFAULT_TREE_CUT");
            cut.setAttribute("linkNode",  mapper.getMappedItem("woodHarvesterAudioSourceNode").getValue());
            sounds.appendChild(delimb);
            delimb.setAttribute("template",  "DEFAULT_TREE_DELIMB");
            delimb.setAttribute("linkNode",  mapper.getMappedItem("woodHarvesterAudioSourceNode").getValue());

            Element dashboards = doc.createElement("dashboards");
            woodHarvester.appendChild(dashboards);
            Element dashboardLength = doc.createElement("dashboard");
            dashboards.appendChild(dashboardLength);
            dashboardLength.setAttribute("displayType",  "NUMBER");
            dashboardLength.setAttribute("valueType",  "cutLength");
            dashboardLength.setAttribute("numbers",  "cutLength");
            dashboardLength.setAttribute("precision",  "0");
            dashboardLength.setAttribute("groups",  "MOTOR_ACTIVE");

            Element dashboardWidth = doc.createElement("dashboard");
            dashboards.appendChild(dashboardWidth);
            dashboardWidth.setAttribute("displayType",  "NUMBER");
            dashboardWidth.setAttribute("valueType",  "diameter");
            dashboardWidth.setAttribute("numbers",  "diameter");
            dashboardWidth.setAttribute("precision",  "0");
            dashboardWidth.setAttribute("groups",  "MOTOR_ACTIVE");








        }
    }
}