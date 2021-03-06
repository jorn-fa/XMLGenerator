package jorn.hiel.mapper.service;

import jorn.hiel.mapper.service.helpers.NeedToWrite;
import jorn.hiel.mapper.service.interfaces.DocWriter;
import jorn.hiel.mapper.service.interfaces.SingleXmlItem;
import jorn.hiel.mapper.service.writers.specs.*;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.w3c.dom.Document;
import org.w3c.dom.Element;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.TransformerException;
import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@Service
@Slf4j
public class VehicleBuilder implements SingleXmlItem {

    private boolean canWrite;

    @Autowired
    NeedToWrite needToWrite;

    @Autowired
    XmlFileWriter xmlFileWriter;

    @Autowired
    ConfigFileReader configFileReader;

    @Autowired
    StoreDataWriter storedataWriter;

    @Autowired
    WiperWriter wiperWriter;

    @Autowired
    BaseWriter baseWriter;

    @Autowired
    SmallStuffWriter smallStuffWriter;

    @Autowired
    EnterableWriter enterableWriter;

    @Autowired
    jorn.hiel.mapper.service.writers.i3DMapperWriter i3DMapperWriter;

    @Autowired
    AnimationWriter animationWriter;

    @Autowired
    DrivableWriter drivableWriter;

    @Autowired
    DashboardWriter dashboardWriter;

    @Autowired
    FillUnitWriter fillUnitWriter;

    @Autowired
    FillVolumeWriter fillVolumeWriter;

    @Autowired
    FoldableWriter foldableWriter;

    @Autowired
    AiWriter aiWriter;

    @Autowired
    MotorizedWriter motorizedWriter;

    @Autowired
    MaterialWriter materialWriter;

    @Autowired
    WorkAreaWriter workAreaWriter;

    @Autowired
    SprayerWriter sprayerWriter;

    @Autowired
    WheelWriter wheelWriter;

    @Autowired
    LightWriter lightWriter;

    @Autowired
    WoodHarvesterWriter woodHarvesterWriter;

    @Autowired
    TrailerWriter trailerWriter;

    @Autowired
    DischargebleWriter dischargebleWriter;

    @Autowired
    CylindredWriter cylindredWriter;

    @Autowired
    DynaLoadPartsWriter dynaLoadPartsWriter;

    @Autowired
    AttachbleWriter attachbleWriter;

    @Autowired
    PowerTakeOffWriter powerTakeOffWriter;

    @Autowired
    ConnectionHoseWriter connectionHoseWriter;

    @Autowired
    BaleLoaderWriter baleLoaderWriter;

    @Autowired
    CoverWriter coverWriter;

    @Autowired
    SuspensionWriter suspensionWriter;

    @Autowired
    SpeedRotatingPartsWriter speedRotatingPartsWriter;

    @Autowired
    GroundReferenceWriter groundReferenceWriter;

    @Autowired
    CultivatorWriter cultivatorWriter;

    @Autowired
    LicenseWriter licenseWriter;

    @Autowired
    CrabSteeringWriter crabSteeringWriter;

    @Autowired
    ArticulatedAxisWriter articulatedAxisWriter;

    @Autowired
    FillTriggerWriter fillTriggerWriter;



    private Document doc;


    public void setFileLocation(String fileLocation){
        xmlFileWriter.setFileLocation(fileLocation);
        this.canWrite=true;

    }

    public void writeVehicle() throws TransformerException, ParserConfigurationException {

        if(canWrite) {

            needToWrite.init();



            DocumentBuilderFactory docFactory = DocumentBuilderFactory.newInstance();
            DocumentBuilder docBuilder = docFactory.newDocumentBuilder();
            doc = docBuilder.newDocument();

            Element rootElement = doc.createElement("vehicle");
            doc.appendChild(rootElement);

            //rootElement.setAttribute("type",configFileReader.getMappedItem("vehicleType").getValue());
            rootElement.setAttribute("type",needToWrite.getCurrentSpec().getName());


            addSingleXmlItem(doc,rootElement,configFileReader.getMappedItem("annotation"));

            List<DocWriter> eofWriters = List.of(speedRotatingPartsWriter, smallStuffWriter, animationWriter, materialWriter, i3DMapperWriter);

            List<DocWriter> writers = List.of(storedataWriter,baseWriter, licenseWriter, dynaLoadPartsWriter, wheelWriter, lightWriter, wiperWriter,enterableWriter,
                    drivableWriter, dashboardWriter, fillUnitWriter,fillVolumeWriter ,fillTriggerWriter, foldableWriter, aiWriter,motorizedWriter,workAreaWriter,
                    sprayerWriter,woodHarvesterWriter, trailerWriter,dischargebleWriter,cylindredWriter, attachbleWriter,powerTakeOffWriter,connectionHoseWriter,
                    baleLoaderWriter, coverWriter,suspensionWriter,groundReferenceWriter,cultivatorWriter,crabSteeringWriter,articulatedAxisWriter);



            List<DocWriter> combinedList = Stream.of(writers, eofWriters)
                    .flatMap(Collection::stream)
                    .collect(Collectors.toList());
            combinedList.forEach(a-> a.write(doc));


            xmlFileWriter.writeXml(doc);

        }



        }

}
