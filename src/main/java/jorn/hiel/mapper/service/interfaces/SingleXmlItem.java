package jorn.hiel.mapper.service.interfaces;

import jorn.hiel.mapper.pojo.MappedItem;
import org.w3c.dom.Document;
import org.w3c.dom.Element;

import javax.xml.stream.XMLStreamException;


public interface SingleXmlItem {

    /**
     *
     * @param document  Document
     * @param rootElement
     * @param mappedItem The mappedItem
     * @throws XMLStreamException
     *
     * adds a single item entry to xml <br>
     * example = &#60;example&#62;  text &#60;/example &#62;
     */

    default void addSingleXmlItem(Document document, Element rootElement, MappedItem mappedItem){
        Element element=document.createElement(mappedItem.getKey());
        element.setTextContent(mappedItem.getValue());
        rootElement.appendChild(element);

    }

}
