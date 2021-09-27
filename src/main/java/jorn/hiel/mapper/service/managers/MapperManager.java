package jorn.hiel.mapper.service.managers;

import jorn.hiel.mapper.pojo.MappedItem;
import jorn.hiel.mapper.pojo.TranslationItem;
import jorn.hiel.mapper.service.ConfigFileReader;
import jorn.hiel.mapper.service.I3DMapper;
import jorn.hiel.mapper.service.TranslationFileReader;
import jorn.hiel.mapper.service.VehicleBuilder;
import jorn.hiel.mapper.service.helpers.UnknownStringCounter;
import jorn.hiel.mapper.service.writers.ModDescWriterDom;
import lombok.Getter;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;
import org.json.simple.parser.ParseException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.xml.sax.SAXException;

import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.TransformerException;
import java.io.File;
import java.io.IOException;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

@Service
@Getter @Setter
@Slf4j
public class MapperManager extends BasicManager {


    private File directory;
    private File fileName;
    private List<String> results;

    @Autowired
    I3DMapper mapper;

    @Autowired
    TranslationFileReader translation;

    @Autowired
    UnknownStringCounter unknownCounter;

    @Autowired
    ModDescWriterDom modDescWriterDom;

    @Autowired
    ConfigFileReader configFileReader;

    @Autowired
    VehicleBuilder vehicleBuilder;

    private final String source = "e:/temp/translations.json";
    private final String configFile = "e:/temp/config.json";
    private List<String> args = new ArrayList<>();


    public void setRuntimeArgs(String[] runTimeArgs){
        Collections.addAll(args,runTimeArgs);
    }

    public void runMe() {

        results=new ArrayList<>();

        mapper.setFile(fileName.getAbsolutePath());
        mapper.clearRepo();
        translation.setFile(source);
        configFileReader.setFile(configFile);


        try {
            mapper.process();

            translation.process();
            configFileReader.process();

            if (args.size() != 0 && args.contains("-FullWrite:true")) {
                log.info("FULL WRITE ACTIVE");

                configFileReader.getMappedItems().put("writeAll", "true");
            }


            mapper.repo.getItems().forEach(a -> {
                for (TranslationItem item : translation.getTranslations()) {
                    if (item.getTranslationItems().contains(a.getNode())) {
                        mapper.addEntry(new MappedItem().setKey(item.getItem()).setValue(a.getNode()));

                    }
                }

            });

            //add filename to mapper

            mapper.addEntry(new MappedItem().setKey("fileName").setValue(fileName.getAbsolutePath()));


            System.out.println();


        } catch (ParserConfigurationException | SAXException | IOException e) {
            e.printStackTrace();
        } catch (ParseException h) {
            System.out.println("problem with json : " + h);

        } catch (NoSuchFieldException e) {
            System.out.println("wrong item as first item in I3d");
        }


        try {

            String moddescName = directory.getAbsolutePath()+"/"+"modDesc.xml";


            modDescWriterDom.setFileLocation( moddescName);
            modDescWriterDom.writeModDesc();

            String vehicleName = configFileReader.getMappedItem("vehicleFileName").getValue();
            vehicleBuilder.setFileLocation("e:/temp/" + vehicleName);
            vehicleBuilder.writeVehicle();

            results.add("i3dmapped items = " + mapper.repo.getItems().size());
            results.add("unknown items found in moddesc = " +unknownCounter.countEntries(Path.of(moddescName)));
            results.add("unknown items found in vehicle = " + unknownCounter.countEntries(Path.of("e:/temp/" + vehicleName)));


        } catch (ParserConfigurationException | TransformerException | IOException e) {
            e.printStackTrace();
        }

    }
}
