package jorn.hiel.mapper.service.writers;


import jorn.hiel.mapper.service.I3DMapper;
import jorn.hiel.mapper.service.interfaces.SingleXmlItem;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;

@Service
public class StoredataWriter implements SingleXmlItem {

    @Autowired
    I3DMapper mapper;



    public void write(Document doc) {

        Node rootElement = doc.getElementsByTagName("Vehicle").item(0);


        Element storeData = doc.createElement("storeData");



        addSingleXmlItem(doc, storeData,mapper.getMappedItem("name"));
        addSingleXmlItem(doc, storeData,mapper.getMappedItem("canBeSold"));
        addSingleXmlItem(doc, storeData,mapper.getMappedItem("showInStore"));
        addSingleXmlItem(doc, storeData,mapper.getMappedItem("price"));
        addSingleXmlItem(doc, storeData,mapper.getMappedItem("lifetime"));




        rootElement.appendChild(storeData);


    }
}
