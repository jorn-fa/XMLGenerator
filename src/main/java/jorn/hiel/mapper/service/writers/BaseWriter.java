package jorn.hiel.mapper.service.writers;

import jorn.hiel.mapper.pojo.MappedItem;
import jorn.hiel.mapper.service.I3DMapper;
import jorn.hiel.mapper.service.interfaces.DocWriter;
import jorn.hiel.mapper.service.interfaces.SingleXmlItem;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;

import java.util.List;


@Service
public class BaseWriter implements SingleXmlItem, DocWriter {

    @Autowired
    I3DMapper mapper;

    public void write(Document doc) {

        Node rootElement = doc.getElementsByTagName("Vehicle").item(0);

        Element base=doc.createElement("base");
        addSingleXmlItem(doc,base,mapper.getMappedItem("typeDesc"));
        addSingleXmlItem(doc,base, new MappedItem().setKey("fileName").setValue(mapper.getFileName()));

        //size
        Element size = doc.createElement("size");
        List<String>sizeList = List.of("width","length","lengthOffset");
        sizeList.forEach(a-> size.setAttribute(a, mapper.getMappedItem(a).getValue()));
        base.appendChild(size);


        Element speedLimit= doc.createElement("speedLimit");
        speedLimit.setAttribute("value",mapper.getMappedItem("speedLimit").getValue());
        base.appendChild(speedLimit);

        //components

        Element components = doc.createElement("components");
        Element component = doc.createElement("component");
        components.appendChild(component);
        List<String>compList = List.of("centerOfMass","solverIterationCount","mass");
        compList.forEach(a-> component.setAttribute(a, mapper.getMappedItem(a).getValue()));

        //schemaOverlay
        Element schemaOverlay = doc.createElement("schemaOverlay");
        Element def = doc.createElement("default");
        def.setAttribute("name","DEFAULT_IMPLEMENT");
        schemaOverlay.appendChild(def);

        Element turnedOn = doc.createElement("turnedOn");
        turnedOn.setAttribute("name","DEFAULT_IMPLEMENT_ON");
        schemaOverlay.appendChild(turnedOn);

        Element selected = doc.createElement("selected");
        selected.setAttribute("name","DEFAULT_IMPLEMENT_SELECTED");
        schemaOverlay.appendChild(selected);

        Element turnedOnSelected = doc.createElement("turnedOnSelected");
        turnedOnSelected.setAttribute("name","DEFAULT_IMPLEMENT_SELECTED_ON");
        schemaOverlay.appendChild(turnedOnSelected);

        base.appendChild(schemaOverlay);




        base.appendChild(components);

        rootElement.appendChild(base);

    }

}
