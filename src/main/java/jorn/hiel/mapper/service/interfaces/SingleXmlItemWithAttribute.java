package jorn.hiel.mapper.service.interfaces;

import jorn.hiel.mapper.pojo.MappedItem;
import org.w3c.dom.Document;
import org.w3c.dom.Element;

public interface SingleXmlItemWithAttribute {

    /**
     *
     * @param document  The Document
     * @param mappedItem The mappedItem
     * @param attributeName The attribute name
     *
     *
     * adds a single item entry to xml <br>
     * example = &#60;multiplayer supported="true"/&#62;
     */
    default void addSingleItemWithAttribute(Document document, Element rootElement, MappedItem mappedItem, String attributeName) {
        Element element=document.createElement(mappedItem.getKey());
        element.setTextContent(mappedItem.getValue());
        rootElement.appendChild(element);

    }
}
