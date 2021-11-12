package jorn.hiel.mapper.service.writers.fs19;

import jorn.hiel.mapper.service.ConfigFileReader;
import jorn.hiel.mapper.service.enums.VehicleSpec;
import jorn.hiel.mapper.service.helpers.NeedToWrite;
import jorn.hiel.mapper.service.interfaces.DocWriter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;

@Component
public class WorkAreaWriter implements DocWriter {

    @Autowired
    ConfigFileReader configFileReader;

    @Autowired
    NeedToWrite needToWrite;



    public void write(Document doc) {

        if (needToWrite.needsToWrite(VehicleSpec.WORKAREAS)) {
            Node rootElement = doc.getElementsByTagName("Vehicle").item(0);

            Element workAreas = doc.createElement("workAreas");


            int needed = Integer.valueOf(configFileReader.getMappedItem("numberOfWorkAreas").getValue());

            for (int x=0;x<needed;x++) {
                Element workArea = doc.createElement("workArea");
                Element area = doc.createElement("area");

                workArea.setAttribute("type",configFileReader.getMappedItem("WorkAreaType").getValue());
                workArea.setAttribute("functionName",configFileReader.getMappedItem("WorkAreaFunctionName").getValue());
                workArea.setAttribute("disableBackwards","true");
                workArea.setAttribute("requiresGroundContact",configFileReader.getMappedItem("WorkAreaGroundContact").getValue());
                workArea.setAttribute("needsSetIsTurnedOn","false");


                area.setAttribute("startNode",configFileReader.getMappedItem("workAreaStartNode").getValue());
                area.setAttribute("widthNode",configFileReader.getMappedItem("workAreaWidthNode").getValue());
                area.setAttribute("heightNode",configFileReader.getMappedItem("workAreaHeightNode").getValue());


                Element folding = doc.createElement("folding");
                folding.setAttribute("minLimit",configFileReader.getMappedItem("workAreaFoldingMinLimit").getValue());
                folding.setAttribute("maxLimit",configFileReader.getMappedItem("workAreaFoldingMaxLimit").getValue());





                workAreas.appendChild(workArea);
                workArea.appendChild(area);
            }


            rootElement.appendChild(workAreas);


        }
    }

    }