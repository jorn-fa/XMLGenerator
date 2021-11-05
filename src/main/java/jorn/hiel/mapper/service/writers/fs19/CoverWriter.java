package jorn.hiel.mapper.service.writers.fs19;

import jorn.hiel.mapper.service.ConfigFileReader;
import jorn.hiel.mapper.service.helpers.NeedToWrite;
import jorn.hiel.mapper.service.interfaces.DocWriter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
@Component
public class CoverWriter implements DocWriter {


    @Autowired
    NeedToWrite needToWrite;

    @Autowired
    ConfigFileReader configFileReader;


    @Override
    public void write(Document doc) {

        if (needToWrite.needsToWrite("cover")) {
            Node rootElement = doc.getElementsByTagName("Vehicle").item(0);
            {

                Element cover = doc.createElement("cover");
                rootElement.appendChild(cover);

                Element coverConfigurations = doc.createElement("coverConfigurations");
                cover.appendChild(coverConfigurations);

                int needed = Integer.parseInt(configFileReader.getMappedItem("numberOfCoverConfigurations").getValue());

                for (int x=0;x<needed;x++) {
                    String item = "coverConfigurations";
                    Element element = doc.createElement(item);
                    element.setAttribute("openCoverWhileTipping", "true");


                    String animName = configFileReader.getMappedItem("coverAnimation").getValue();
                    String unknown = configFileReader.getMappedItem("UnknownEntry").getValue();
                    if (animName.equals(unknown)) {
                        animName = "toggleCover";
                    }
                    configFileReader.addAnimation(animName, "");
                    Element cover2 = doc.createElement("cover");
                    element.appendChild(cover2);
                    cover2.setAttribute("openAnimation", animName);
                    cover2.setAttribute("fillUnitIndices", unknown);
                    coverConfigurations.appendChild(element);
                }






            }
        }
    }
}