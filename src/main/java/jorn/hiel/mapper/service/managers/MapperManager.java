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

    private List<String> args = new ArrayList<>();


    public void setRuntimeArgs(String[] runTimeArgs){
        Collections.addAll(args,runTimeArgs);
        checkCommandLine();
    }

    public void runMe() {

        //todo cleanup

        results=new ArrayList<>();

        mapper.setFile(fileName.getAbsolutePath());
        mapper.clearRepo();

        try {

            mapper.process();

            translation.process();
            configFileReader.process();




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

            String modDescName = directory.getAbsolutePath()+"/"+"modDesc.xml";


            modDescWriterDom.setFileLocation( modDescName);
            modDescWriterDom.writeModDesc();

            String vehicleName = configFileReader.getMappedItem("vehicleFileName").getValue();
            vehicleBuilder.setFileLocation("e:/temp/" + vehicleName);
            vehicleBuilder.writeVehicle();

            results.add("i3dMapped items = " + mapper.repo.getItems().size());
            results.add("unknown items found in modDesc = " +unknownCounter.countEntries(Path.of(modDescName)));
            results.add("unknown items found in vehicle = " + unknownCounter.countEntries(Path.of("e:/temp/" + vehicleName)));
            //erase history to stop chain spam process button
            this.fileName=null;
            this.directory=null;


        } catch (ParserConfigurationException | TransformerException | IOException e) {
            e.printStackTrace();
        }

    }

    private void checkCommandLine() {
        //-FullWrite:true -TranslationFile:"e:\temp\translations.json" -ConfigFile:"e:\temp\config.json

        if (args.size() != 0 ) {
            String logEntry = "COMMANDLINE: ";
            log.info("command line arguments found");

            if (args.contains("-FullWrite:true")) {
                log.info(logEntry+"FULL WRITE ACTIVE");
                configFileReader.getMappedItems().put("writeAll", "true");
            }

            for(String arg : args) {
                String checkMe="-TranslationFile:";
                if (arg.startsWith(checkMe)){

                    String sub = arg.substring(checkMe.length()).replace('\\','/');
                    log.info(logEntry+"translation file set to "+ sub);
                    translation.setFile(sub);
                }
                checkMe="-ConfigFile:";
                if (arg.startsWith(checkMe)){
                    String sub = arg.substring(checkMe.length()).replace('\\','/');
                    log.info(logEntry+"configuration file set to "+ sub);
                    configFileReader.setFile(sub);

                }
                checkMe="-SourceFile:";
                if (arg.startsWith(checkMe)){
                    String sub = arg.substring(checkMe.length()).replace('\\','/');
                    log.info(logEntry+"source file set to "+ sub);
                    fileName=new File(sub);

                }
                checkMe="-Destination:";
                if (arg.startsWith(checkMe)){
                    String sub = arg.substring(checkMe.length()).replace('\\','/');
                    log.info(logEntry+"destination set to "+ sub);
                    this.directory=new File(sub);



                }

            }

    }
    }
}
