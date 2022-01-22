package jorn.hiel.mapper.service.writers.specs;

import jorn.hiel.mapper.service.ConfigFileReader;
import jorn.hiel.mapper.service.I3DMapper;
import jorn.hiel.mapper.service.enums.VehicleSpec;
import jorn.hiel.mapper.service.helpers.NeedToWrite;
import jorn.hiel.mapper.service.interfaces.DocWriter;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;

import java.util.List;
import java.util.Locale;
import java.util.stream.Collectors;

@Slf4j
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

        if (needToWrite.needsToWrite(VehicleSpec.FILLUNIT)) {
            Node rootElement = doc.getElementsByTagName("vehicle").item(0);

            Element mainFillUnit = doc.createElement("fillUnit");
            Element fillUnitConfigurations = doc.createElement("fillUnitConfigurations");
            Element fillUnitConfiguration = doc.createElement("fillUnitConfiguration");
            fillUnitConfiguration.setAttribute("name","l10n_configuration_valueDefault");
            fillUnitConfiguration.setAttribute("price","0");



            Element fillUnits = doc.createElement("fillUnits");

            String key = "numberOfFillUnits";
            int needed = Integer.parseInt(configFileReader.getMappedItem(key).getValue());

            if(needToWrite.getI3dSettings().containsKey(key)){
                log.info("found "+key+ " in i3d, priority over config file");
                needed = Integer.parseInt(needToWrite.getI3dSettings().get(key));
            }

            for (int x=1;x<=needed;x++) {
                key = "fillUnit"+x;
                boolean foundI3Ditem = needToWrite.getI3dSettings().containsKey(key);




                Element fillUnit = doc.createElement("fillUnit");
                List<String> sizeList = List.of("unitTextOverride", "fillTypeCategories", "showOnHud", "showInShop", "fillTypes", "capacity", "canBeUnloaded");
                sizeList.forEach(a -> fillUnit.setAttribute(a, mapper.getMappedItem("unknownKey").getValue()));

                if (foundI3Ditem){
                    log.info("found "+key+ " in i3d");
                    String fillType = needToWrite.getI3dSettings().get(key);
                    fillUnit.setAttribute("fillTypes", fillType);
                    fillUnit.setAttribute("unitTextOverride", "l10n_unit_literShort");
                    if(fillType.equals("diesel")||fillType.equals("def")||fillType.equals("air")){
                        fillUnit.setAttribute("canBeUnloaded", "false");
                        fillUnit.setAttribute("showInShop", "false");
                        fillUnit.setAttribute("showOnHud", "false");
                    }
                    else
                    {
                        fillUnit.setAttribute("canBeUnloaded", "true");
                        fillUnit.setAttribute("showInShop", "true");
                        fillUnit.setAttribute("showOnHud", "true");
                    }


                    if(fillType.toLowerCase(Locale.ROOT).equals("slurrytank")){
                        fillUnit.setAttribute("shopDisplayUnit", "CUBICMETER");
                        fillUnit.setAttribute("fillTypeCategories", "slurryTank");
                        List<String> removeList = sizeList.stream()
                                .filter(a->!a.equals("capacity"))
                                .filter(a->!a.equals("fillTypeCategories"))
                                .collect(Collectors.toList());
                        removeList.forEach(a -> fillUnit.removeAttribute(a));

                        Element exactfillRootNode = doc.createElement("exactFillRootNode");
                        exactfillRootNode.setAttribute( "node",mapper.getMappedItem("exactfillRootNode").getValue());
                        fillUnit.appendChild(exactfillRootNode);


                    }


                }


                key="fillUnitCapacity"+x;
                if(needToWrite.getI3dSettings().containsKey(key)){
                    fillUnit.setAttribute("capacity", needToWrite.getI3dSettings().get(key));
                    }


                fillUnits.appendChild(fillUnit);
            }

            rootElement.appendChild(mainFillUnit);
            mainFillUnit.appendChild(fillUnitConfigurations);
            fillUnitConfigurations.appendChild(fillUnitConfiguration);
            fillUnitConfiguration.appendChild(fillUnits);





        }





    }


}
