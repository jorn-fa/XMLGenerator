package jorn.hiel.mapper.service.writers.specs;

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
@Slf4j
@Component
public class FillTriggerWriter implements DocWriter {



    @Autowired
    NeedToWrite needToWrite;


    @Autowired
    I3DMapper mapper;

    @Override
    public void write(Document doc) {

        if (needToWrite.needsToWrite(VehicleSpec.FILLTRIGGERVEHICLE)) {
            Node rootElement = doc.getElementsByTagName("vehicle").item(0);

            Element fillTriggerVehicle = doc.createElement("fillTriggerVehicle");

            String key = "fillTriggerFillUnit";
            boolean foundI3Ditem = needToWrite.getI3dSettings().containsKey(key);
            if(foundI3Ditem){
                fillTriggerVehicle.setAttribute("triggerNode",needToWrite.getI3dSettings().get(key));
                log.info("found "+key+ " in i3d");
            }
            else {
                fillTriggerVehicle.setAttribute("triggerNode",mapper.getMappedItem("triggerNode").getValue());
            }

            fillTriggerVehicle.setAttribute("fillUnitIndex",mapper.getMappedItem("fillUnitIndex").getValue());
            fillTriggerVehicle.setAttribute("litersPerSecond",mapper.getMappedItem("litersPerSecond").getValue());
            rootElement.appendChild(fillTriggerVehicle);
        }
    }
}
