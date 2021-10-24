package jorn.hiel.mapper.service.writers;

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
public class FillUnitWriter implements DocWriter {



    @Autowired
    I3DMapper mapper;

    @Autowired
    NeedToWrite needToWrite;

    @Autowired
    ConfigFileReader configFileReader;


    @Override
    public void write(Document doc) {

        if (needToWrite.needsToWrite("fillUnit")) {
            Node rootElement = doc.getElementsByTagName("Vehicle").item(0);

            Element mainFillUnit = doc.createElement("fillUnit");
            Element fillUnitConfigurations = doc.createElement("fillUnitConfigurations");
            Element fillUnitConfiguration = doc.createElement("fillUnitConfiguration");
            fillUnitConfiguration.setAttribute("name","l10n_configuration_valueDefault");
            fillUnitConfiguration.setAttribute("price","0");



            Element fillUnits = doc.createElement("fillUnits");

            int needed = Integer.valueOf(configFileReader.getMappedItem("numberOfFillUnits").getValue());

            for (int x=0;x<needed;x++) {
                Element fillUnit = doc.createElement("fillUnit");
                List<String> sizeList = List.of("unit", "fillTypeCategories", "showOnHud", "showInShop", "fillTypes", "capacity", "canBeUnloaded");
                sizeList.forEach(a -> fillUnit.setAttribute(a, mapper.getMappedItem("unknownKey").getValue()));
                fillUnits.appendChild(fillUnit);
            }

            rootElement.appendChild(mainFillUnit);
            mainFillUnit.appendChild(fillUnitConfigurations);
            fillUnitConfigurations.appendChild(fillUnitConfiguration);
            fillUnitConfiguration.appendChild(fillUnits);





        }





    }


}
