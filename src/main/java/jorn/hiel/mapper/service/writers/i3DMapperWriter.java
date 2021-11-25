package jorn.hiel.mapper.service.writers;

import jorn.hiel.mapper.pojo.I3dMap;
import jorn.hiel.mapper.service.I3DMapper;
import jorn.hiel.mapper.service.interfaces.DocWriter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;

@Service
public class i3DMapperWriter implements DocWriter {

    @Autowired
    I3DMapper mapper;

    public void write(Document doc){
        Node rootElement = doc.getElementsByTagName("vehicle").item(0);
        Element i3dMappings = doc.createElement("i3dMappings");

        for(I3dMap map:mapper.repo.getItems()){
            Element mapped = doc.createElement("i3dMapping");
            mapped.setAttribute("id",map.getId());
            mapped.setAttribute("node",map.getNode());
            i3dMappings.appendChild(mapped);
        }

        rootElement.appendChild(i3dMappings);

    }

}
