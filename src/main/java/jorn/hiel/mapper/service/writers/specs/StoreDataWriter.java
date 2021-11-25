package jorn.hiel.mapper.service.writers.specs;


import jorn.hiel.mapper.service.ConfigFileReader;
import jorn.hiel.mapper.service.I3DMapper;
import jorn.hiel.mapper.service.enums.GameVersion;
import jorn.hiel.mapper.service.helpers.NeedToWrite;
import jorn.hiel.mapper.service.interfaces.DocWriter;
import jorn.hiel.mapper.service.interfaces.SingleXmlItem;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;

import java.util.Arrays;
import java.util.List;
import java.util.Locale;

@Service
@Slf4j
public class StoreDataWriter  implements SingleXmlItem, DocWriter {

    @Autowired
    I3DMapper mapper;

    @Autowired
    NeedToWrite needToWrite;

    @Autowired
    ConfigFileReader configFileReader;



    public void write(Document doc) {

        Node rootElement = doc.getElementsByTagName("vehicle").item(0);

        List<String> names = List.of("name","lifetime", "image", "price","rotation",
        "category","shopTranslationOffset","shopRotationOffset");

        List<String> specNames = Arrays.asList("power","maxSpeed","neededPower","workingWidth");

        List<String> functionNames = List.of("function");





        Element storeData = doc.createElement("storeData");
        names.forEach(a-> addSingleXmlItem(doc, storeData,mapper.getMappedItem(a)));

        Element brand = doc.createElement("brand");
        storeData.appendChild(brand);
        brand.setTextContent(mapper.getMappedItem("brand").getValue());
        if(mapper.getEntryRepo().getItems().containsKey("brand")) {
            log.info("found brand in i3d file");
            brand.setTextContent(mapper.getEntryRepo().getItems().get("brand").toUpperCase(Locale.ROOT));
        }
        //todo


        List<String> elements = List.of("canBeSold", "showInStore", "allowLeasing");
        for(String element : elements){
            Element addMe = doc.createElement(element);
            addMe.setTextContent("true");
            storeData.appendChild(addMe);
        }

        Element specs = doc.createElement("spec");
        specNames.forEach(a-> addSingleXmlItem(doc, specs,mapper.getMappedItem(a)));
        storeData.appendChild(specs);



        Element functions = doc.createElement("functions");
        functionNames.forEach(a-> addSingleXmlItem(doc, functions,mapper.getMappedItem(a)));
        storeData.appendChild(functions);


        if (needToWrite.isFullWrite() || needToWrite.getGameVersion().equals(GameVersion.FS22)) {

            Element species = doc.createElement("species");
            species.setTextContent("vehicle");
            storeData.appendChild(species);

            Element maxItemCount = doc.createElement("maxItemCount");
            maxItemCount.setTextContent(configFileReader.getMappedItem("maxItemCount").getValue());
            storeData.appendChild(maxItemCount);

            Element dailyUpkeep = doc.createElement("dailyUpkeep");
            dailyUpkeep.setTextContent(configFileReader.getMappedItem("dailyUpkeep").getValue());
            storeData.appendChild(dailyUpkeep);

            Element runningLeasingFactor = doc.createElement("runningLeasingFactor");
            runningLeasingFactor.setTextContent(configFileReader.getMappedItem("runningLeasingFactor").getValue());
            storeData.appendChild(runningLeasingFactor);





        }


        rootElement.appendChild(storeData);


    }
}
