package jorn.hiel.mapper.service.writers;


import jorn.hiel.mapper.service.I3DMapper;
import jorn.hiel.mapper.service.interfaces.SingleXmlItem;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;

import java.util.Arrays;
import java.util.List;

@Service
public class StoreDataWriter implements SingleXmlItem {

    @Autowired
    I3DMapper mapper;



    public void write(Document doc) {

        Node rootElement = doc.getElementsByTagName("Vehicle").item(0);

        List<String> names = List.of("name","canBeSold", "showInStore","lifetime", "image", "price","lifetime","rotation","brand",
        "category","shopTranslationOffset","shopRotationOffset");

        List<String> specNames = Arrays.asList("power","maxSpeed","neededPower","workingWidth");

        List<String> functionNames = List.of("function");



        Element storeData = doc.createElement("storeData");
        names.forEach(a-> addSingleXmlItem(doc, storeData,mapper.getMappedItem(a)));

        Element specs = doc.createElement("spec");
        specNames.forEach(a-> addSingleXmlItem(doc, specs,mapper.getMappedItem(a)));
        storeData.appendChild(specs);



        Element functions = doc.createElement("functions");
        functionNames.forEach(a-> addSingleXmlItem(doc, functions,mapper.getMappedItem(a)));
        storeData.appendChild(functions);


        rootElement.appendChild(storeData);


    }
}
