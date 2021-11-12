package jorn.hiel.mapper.service.writers.fs19;

import jorn.hiel.mapper.service.ConfigFileReader;
import jorn.hiel.mapper.service.enums.VehicleSpec;
import jorn.hiel.mapper.service.helpers.NeedToWrite;
import jorn.hiel.mapper.service.interfaces.DocWriter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;

@Component
public class FillVolumeWriter implements DocWriter {

    @Autowired
    NeedToWrite needToWrite;

    @Autowired
    ConfigFileReader configFileReader;

    @Override
    public void write(Document doc) {
        if (needToWrite.needsToWrite(VehicleSpec.FILLVOLUME)) {
            Node rootElement = doc.getElementsByTagName("Vehicle").item(0);

            Element fillVolume = doc.createElement("fillVolume");
            rootElement.appendChild(fillVolume);

            //fillvolume configuration
            Element fillVolumeConfigurations = doc.createElement("fillVolumeConfigurations");
            Element fillVolumeConfiguration = doc.createElement("fillVolumeConfiguration");
            Element volumes = doc.createElement("volumes");

            fillVolume.appendChild(fillVolumeConfigurations);
            fillVolumeConfigurations.appendChild(fillVolumeConfiguration);
            fillVolumeConfiguration.appendChild(volumes);

            Element volume = doc.createElement("volume");

            volume.setAttribute("node",configFileReader.getMappedItem("fillVolume").getValue());
            volume.setAttribute("maxDelta",configFileReader.getMappedItem("maxDelta").getValue());
            volume.setAttribute("maxAllowedHeapAngle",configFileReader.getMappedItem("maxAllowedHeapAngle").getValue());
            volume.setAttribute("fillUnitIndex",configFileReader.getMappedItem("fillUnitIndex").getValue());

            volumes.appendChild(volume);

            Element deformNode = doc.createElement("deformNode");
            deformNode.setAttribute("node",configFileReader.getMappedItem("deformNode").getValue());
            volume.appendChild(deformNode);


            Element loadInfos = doc.createElement("loadInfos");
            Element loadInfo = doc.createElement("loadInfo");
            Element node = doc.createElement("node");

            node.setAttribute("node",configFileReader.getMappedItem("loadInfoNode").getValue());
            node.setAttribute("width",configFileReader.getMappedItem("loadInfoWidth").getValue());
            node.setAttribute("length",configFileReader.getMappedItem("loadInfoLength").getValue());

            fillVolume.appendChild(loadInfos);
            loadInfos.appendChild(loadInfo);
            loadInfo.appendChild(node);

            //dischargeInfo

            Element dischargeInfos = doc.createElement("dischargeInfos");
            Element dischargeInfo = doc.createElement("dischargeInfo");
            Element disNode = doc.createElement("node");

            disNode.setAttribute("node",configFileReader.getMappedItem("loadInfoNode").getValue());
            disNode.setAttribute("width",configFileReader.getMappedItem("loadInfoWidth").getValue());
            disNode.setAttribute("length",configFileReader.getMappedItem("loadInfoLength").getValue());

            disNode.setAttribute("alsoUseLoadInfoForDischarge","true");
            disNode.setAttribute("loadInfoFillFactor",configFileReader.getMappedItem("loadInfoFillFactor").getValue());
            disNode.setAttribute("loadInfoSizeScale",configFileReader.getMappedItem("loadInfoSizeScale").getValue());




            fillVolume.appendChild(dischargeInfos);
            dischargeInfos.appendChild(dischargeInfo);
            dischargeInfo.appendChild(disNode);



        }

}


}
