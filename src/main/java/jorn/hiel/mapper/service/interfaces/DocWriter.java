package jorn.hiel.mapper.service.interfaces;

import org.w3c.dom.Document;

public interface DocWriter {

    public default void write(Document doc){ }
}

