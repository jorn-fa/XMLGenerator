package jorn.hiel.mapper.service.writers.specs;

import jorn.hiel.mapper.service.I3DMapper;
import jorn.hiel.mapper.service.enums.VehicleSpec;
import jorn.hiel.mapper.service.helpers.NeedToWrite;
import jorn.hiel.mapper.service.interfaces.DocWriter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;

@Service
public class ArticulatedAxisWriter implements DocWriter {

    @Autowired
    I3DMapper mapper;

    @Autowired
    NeedToWrite needToWrite;




    @Override
    public void write(Document doc) {

        if (needToWrite.needsToWrite(VehicleSpec.ARTICULATEDAXIS)) {

            Node rootElement = doc.getElementsByTagName("vehicle").item(0);

            Element articulatedAxis = doc.createElement("articulatedAxis");
            rootElement.appendChild(articulatedAxis);
            articulatedAxis.setAttribute("componentJointIndex",mapper.getMappedItem("ArticulatedComponentJointIndex").getValue());
            articulatedAxis.setAttribute("rotSpeed",mapper.getMappedItem("ArticulatedRotSpeed").getValue());
            articulatedAxis.setAttribute("rotMin",mapper.getMappedItem("ArticulatedRotMin").getValue());
            articulatedAxis.setAttribute("rotMax",mapper.getMappedItem("ArticulatedRotMax").getValue());
            articulatedAxis.setAttribute("anchorActor",mapper.getMappedItem("ArticulatedAnchorActor").getValue());
            articulatedAxis.setAttribute("rotNode",mapper.getMappedItem("ArticulatedRotNode").getValue());
            articulatedAxis.setAttribute("aiReverseNode",mapper.getMappedItem("ArticulatedAiReverseNode").getValue());
            articulatedAxis.setAttribute("customWheelIndices1",mapper.getMappedItem("ArticulatedCustomWheelIndices1").getValue());
            articulatedAxis.setAttribute("customWheelIndices2",mapper.getMappedItem("ArticulatedCustomWheelIndices2").getValue());

            Element rotatingPart = doc.createElement("rotatingPart");
            articulatedAxis.appendChild(rotatingPart);
            rotatingPart.setAttribute("node",mapper.getMappedItem("ArticulatedRotatingPartNode").getValue());
            rotatingPart.setAttribute("posRot",mapper.getMappedItem("ArticulatedRotatingPartPosRot").getValue());
            rotatingPart.setAttribute("negRot",mapper.getMappedItem("ArticulatedRotatingPartNegRot").getValue());
            rotatingPart.setAttribute("posRotFactor",mapper.getMappedItem("ArticulatedRotatingPartPosRotFactor").getValue());
            rotatingPart.setAttribute("negRotFactor",mapper.getMappedItem("ArticulatedRotatingPartNegRotFactor").getValue());
            rotatingPart.setAttribute("invertSteeringAngle",mapper.getMappedItem("ArticulatedRotatingPartInvertSteeringAngle").getValue());


        }
    }


}
