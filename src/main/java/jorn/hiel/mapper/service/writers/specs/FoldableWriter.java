package jorn.hiel.mapper.service.writers.specs;

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
public class FoldableWriter implements DocWriter {

    @Autowired
    NeedToWrite needToWrite;

    @Autowired
    ConfigFileReader configFileReader;

    @Override
    public void write(Document doc) {
        if (needToWrite.needsToWrite(VehicleSpec.FOLDABLE)) {
            Node rootElement = doc.getElementsByTagName("vehicle").item(0);
            {
                Element foldAble = doc.createElement("foldable");
                rootElement.appendChild(foldAble);
                Element foldingParts = doc.createElement("foldingParts");
                foldingParts.setAttribute("startAnimTime","1");
                foldingParts.setAttribute("turnOnFoldMinLimit","0");
                foldingParts.setAttribute("turnOnFoldMaxLimit","0.5");
                foldingParts.setAttribute("turnOnFoldDirection","1");
                foldingParts.setAttribute("foldMiddleAnimTime","erase this if no pause is needed, else "+ configFileReader.getMappedItem("foldMiddleAnimTime").getValue());



                String animName=configFileReader.getMappedItem("foldableAnimation").getValue();
                String unknown = configFileReader.getMappedItem("UnknownEntry").getValue();

                if(animName.equals(unknown)){animName="foldableAnimation";}


                Element foldingPart = doc.createElement("foldingPart");


                foldingPart.setAttribute("animationName",animName);
                foldingPart.setAttribute("speedScale","1");

                configFileReader.addAnimation("foldableAnimation", configFileReader.getMappedItem("foldableAnimation").getValue());

                foldAble.appendChild(foldingParts);
                foldingParts.appendChild(foldingPart);



            }
        }
    }
}