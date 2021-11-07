package jorn.hiel.mapper.service.writers.fs19;

import jorn.hiel.mapper.service.ConfigFileReader;
import jorn.hiel.mapper.service.I3DMapper;
import jorn.hiel.mapper.service.helpers.NeedToWrite;
import jorn.hiel.mapper.service.interfaces.DocWriter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
@Component
public class GroundReferenceWriter implements DocWriter {

    @Autowired
    I3DMapper mapper;

    @Autowired
    NeedToWrite needToWrite;

    @Autowired
    ConfigFileReader configFileReader;


    @Override
    public void write(Document doc) {

        if (needToWrite.needsToWrite("groundReferenceNodes")) {
            Node rootElement = doc.getElementsByTagName("Vehicle").item(0);
            {

                Element groundReferenceNodes = doc.createElement("groundReferenceNodes");
                rootElement.appendChild(groundReferenceNodes);

                Element groundReferenceNode = doc.createElement("groundReferenceNode");
                groundReferenceNodes.appendChild(groundReferenceNode);
                groundReferenceNode.setAttribute("node",mapper.getMappedItem("groundReferenceNode").getValue());
                groundReferenceNode.setAttribute("forceFactor",configFileReader.getMappedItem("groundReferenceNodeForceFactor").getValue());
                groundReferenceNode.setAttribute("threshold",configFileReader.getMappedItem("groundReferenceNodeThreshold").getValue());
                groundReferenceNode.setAttribute("chargeValue",configFileReader.getMappedItem("groundReferenceNodeChargeValue").getValue());
                groundReferenceNode.setAttribute("depthNode",mapper.getMappedItem("groundReferenceNodeDepthNode").getValue());
                groundReferenceNode.setAttribute("maxDepth",configFileReader.getMappedItem("groundReferenceNodeMaxDepth").getValue());

            }
        }
    }
}

