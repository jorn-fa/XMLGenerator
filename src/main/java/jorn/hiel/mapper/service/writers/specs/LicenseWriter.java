package jorn.hiel.mapper.service.writers.specs;

import jorn.hiel.mapper.service.ConfigFileReader;
import jorn.hiel.mapper.service.I3DMapper;
import jorn.hiel.mapper.service.enums.VehicleSpec;
import jorn.hiel.mapper.service.helpers.NeedToWrite;
import jorn.hiel.mapper.service.interfaces.DocWriter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;

import java.util.List;


@Service

public class LicenseWriter implements DocWriter {

    @Autowired
    I3DMapper mapper;

    @Autowired
    NeedToWrite needToWrite;

    @Autowired
    ConfigFileReader configFileReader;


    @Override
    public void write(Document doc) {

        if (needToWrite.needsToWrite(VehicleSpec.LICENSEPLATES)) {
            Node rootElement = doc.getElementsByTagName("vehicle").item(0);
            {

                Element licenseElement = doc.createElement("licensePlates");
                rootElement.appendChild(licenseElement);

                Element licenceFront = doc.createElement("licensePlate");
                licenceFront.setAttribute("node",mapper.getMappedItem("licensePlateFront").getValue());
                licenceFront.setAttribute("position","FRONT");
                licenceFront.setAttribute("preferedType",configFileReader.getMappedItem("preferedLicenceFront").getValue());
                licenceFront.setAttribute("placementArea",mapper.getMappedItem("Unknown").getValue());

                Element licenceBack = doc.createElement("licensePlate");
                licenceBack.setAttribute("node",mapper.getMappedItem("licensePlateBack").getValue());
                licenceBack.setAttribute("position","BACK");
                licenceBack.setAttribute("preferedType",configFileReader.getMappedItem("preferedLicenceBack").getValue());
                licenceBack.setAttribute("placementArea",mapper.getMappedItem("Unknown").getValue());

                licenseElement.appendChild(licenceFront);
                licenseElement.appendChild(licenceBack);

                int needed = Integer.parseInt(configFileReader.getMappedItem("numberOfLicensePlates").getValue());

                for (int x=0;x<needed-2;x++) {
                    Element licensePlate = doc.createElement("licensePlate");
                    List<String> sizeList = List.of("node", "position", "preferedType", "placementArea");
                    sizeList.forEach(a -> licensePlate.setAttribute(a, mapper.getMappedItem("unknownKey").getValue()));
                    licenseElement.appendChild(licensePlate);
                }


            }
        }
    }

}
