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
public class MaterialWriter implements DocWriter {

    @Autowired
    NeedToWrite needToWrite;

    @Autowired
    I3DMapper mapper;

    @Autowired
    ConfigFileReader configFileReader;

    @Override
    public void write(Document doc) {
        if (needToWrite.needsToWrite(VehicleSpec.BASEMATERIAL)) {

        Node rootElement = doc.getElementsByTagName("Vehicle").item(0);

        Element baseMaterial= doc.createElement("baseMaterial");
        rootElement.appendChild(baseMaterial);

        Element material= doc.createElement("material");
        baseMaterial.appendChild(material);

        material.setAttribute("name",configFileReader.getMappedItem("baseMaterialName").getValue());
        material.setAttribute("baseNode", mapper.getMainNodeName());

        for(int i=0; i<8;i++){
            Element shaderParameter= doc.createElement("shaderParameter");
            shaderParameter.setAttribute("name","colorMat"+i);
            shaderParameter.setAttribute("value",configFileReader.getMappedItem("colorMat"+i).getValue());
            material.appendChild(shaderParameter);
        }




            Element baseMaterialConfigurations = doc.createElement("baseMaterialConfigurations");
            rootElement.appendChild(baseMaterialConfigurations);
            Element baseMaterialConfiguration = doc.createElement("baseMaterialConfiguration");
            baseMaterialConfiguration.setAttribute("color", configFileReader.getMappedItem("BaseMaterialConfigMatColor").getValue());
            baseMaterialConfiguration.setAttribute("price", configFileReader.getMappedItem("BaseMaterialConfigMatPrice").getValue());
            baseMaterialConfigurations.appendChild(baseMaterialConfiguration);
            Element configMaterial = doc.createElement("material");
            configMaterial.setAttribute("name", configFileReader.getMappedItem("ConfigMatName").getValue());
            configMaterial.setAttribute("shaderParameter", configFileReader.getMappedItem("UnknownItem").getValue());
            baseMaterialConfigurations.appendChild(configMaterial);



        Element designMaterialConfigurations= doc.createElement("designMaterialConfigurations");
        designMaterialConfigurations.setAttribute("useDefaultColors","true");
        designMaterialConfigurations.setAttribute("price",configFileReader.getMappedItem("DesignMaterialConfigMatPrice").getValue());
        rootElement.appendChild(designMaterialConfigurations);
        Element designMaterial= doc.createElement("material");
        designMaterial.setAttribute("name",configFileReader.getMappedItem("ConfigMatName").getValue());
        designMaterial.setAttribute("shaderParameter",configFileReader.getMappedItem("UnknownItem").getValue());
        designMaterialConfigurations.appendChild(designMaterial);
        }

    }
}
