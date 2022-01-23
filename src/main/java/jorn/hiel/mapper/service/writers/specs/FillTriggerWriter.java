package jorn.hiel.mapper.service.writers.specs;

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


    @Override
    public void write(Document doc) {

        if (needToWrite.needsToWrite(VehicleSpec.FILLTRIGGERVEHICLE)) {
            Node rootElement = doc.getElementsByTagName("vehicle").item(0);

            Element fillTriggerVehicle = doc.createElement("fillTriggerVehicle");

            needToWrite.decide("fillTriggerFillUnitIndex",fillTriggerVehicle,"fillUnitIndex");
            needToWrite.decide("fillTriggerTriggerNode",fillTriggerVehicle,"triggerNode");
            needToWrite.decide("fillTriggerLitersPerSecond",fillTriggerVehicle,"litersPerSecond");

            rootElement.appendChild(fillTriggerVehicle);
        }
    }
}
