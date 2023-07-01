package jorn.hiel.mapper.service.managers;

import jorn.hiel.mapper.pojo.MappedItem;
import jorn.hiel.mapper.pojo.TranslationItem;
import jorn.hiel.mapper.service.*;
import jorn.hiel.mapper.service.helpers.UnknownStringCounter;
import jorn.hiel.mapper.service.writers.ModDescWriterDom;
import lombok.Getter;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;
import org.json.simple.parser.ParseException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.system.ApplicationHome;
import org.springframework.stereotype.Service;
import org.xml.sax.SAXException;

import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.TransformerException;
import java.io.File;
import java.io.FileNotFoundException;
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
    VehicleSpecReader vehicleSpecReader;

    @Autowired
    VehicleBuilder vehicleBuilder;

    @Autowired
    ConfigFileCreator configFileCreator;



    private List<String> args = new ArrayList<>();


    public void setRuntimeArgs(String[] runTimeArgs) {
        Collections.addAll(args, runTimeArgs);
        checkCommandLine();
    }

    public void runMe() {


        if (!hasConfigFilesSet()) {
            log.info("no config/translation/vehicleSpec files set");
        } else {

            results = new ArrayList<>();
            mapper.setFile(fileName.getAbsolutePath());
            mapper.clearRepo();

            try {
                checkConfigFiles();

                mapper.process();
                mapper.repo.getItems().forEach(a -> {
                    for (TranslationItem item : translation.getTranslations()) {
                        if (item.getTranslationItems().contains(a.getNode())) {
                            mapper.addEntry(new MappedItem().setKey(item.getItem()).setValue(a.getNode()));

                        }
                    }

                });

                //add filename to mapper

                mapper.addEntry(new MappedItem().setKey("fileName").setValue(fileName.getAbsolutePath()));


            } catch (ParserConfigurationException | SAXException | IOException e) {
                e.printStackTrace();
            } catch (ParseException h) {
                log.info("problem with json : " + h);

            } catch (NoSuchFieldException e) {
                log.info("wrong item as first item in I3d");
            }


            try {



                String vehicleName = configFileReader.getMappedItem("vehicleFileName").getValue();
                String vehicleFileLocation = directory.getAbsolutePath() + "/" + vehicleName;

                vehicleBuilder.setFileLocation(vehicleFileLocation);
                vehicleBuilder.writeVehicle();

                String modDescName = directory.getAbsolutePath() + "/" + "modDesc.xml";
                modDescWriterDom.setFileLocation(modDescName);
                modDescWriterDom.writeModDesc();




                results.add("i3dMapped items = " + mapper.repo.getItems().size());

                int howMany = unknownCounter.countEntries(Path.of(modDescName));
                results.add("Number of lines with unknown items found in modDesc = " + howMany);
                //todo pathname

                howMany += unknownCounter.countEntries(Path.of(vehicleFileLocation));
                String result = "Number lines with unknown items found :  >"+howMany+"<";
                results.add(result);
                log.info(result);


            } catch (ParserConfigurationException | TransformerException | IOException e) {
                e.printStackTrace();
            }
        }

    }


    private boolean hasConfigFilesSet() {
        log.info("checking location of config files");

        log.info(translation.getFile());
        log.info(configFileReader.getFile());
        log.info(vehicleSpecReader.getFile());

        return !translation.getFile().isEmpty() && !configFileReader.getFile().isEmpty() && !vehicleSpecReader.getFile().isEmpty();

    }

    private void checkConfigFiles() throws FileNotFoundException, ParseException {
        try {


            configFileReader.process();
            translation.process();
            vehicleSpecReader.process();
        } catch (FileNotFoundException e) {

        } catch (ParseException e) {
            e.printStackTrace();
        }

    }



    private void checkCommandLine() {

        ApplicationHome home = new ApplicationHome(MapperManager.class);


        String separator = System.getProperty("file.separator");
        String LOCATION = System.getProperty("user.home") +separator+ "XMLGenerator"+separator;
        //todo this is not correct

        translation.setFile(LOCATION+ "translations.json");
        configFileReader.setFile(LOCATION+"config.json");
        vehicleSpecReader.setFile(LOCATION+"vehicleSpec.json");


        if (args.size() != 0) {
            String logEntry = "COMMANDLINE: ";
            log.info("command line arguments found");

            if (args.contains("-FullWrite:true")) {
                log.info(logEntry + "FULL WRITE ACTIVE");
                configFileReader.getMappedItems().put("writeAll", "true");
            }

            for (String arg : args) {
                String checkMe = "-TranslationFile:";
                if (arg.startsWith(checkMe)) {

                    String sub = arg.substring(checkMe.length()).replace('\\', '/');
                    log.info(logEntry + "translation file set to " + sub);
                    translation.setFile(sub);
                }


                checkMe = "-ConfigFile:";
                if (arg.startsWith(checkMe)) {
                    String sub = arg.substring(checkMe.length()).replace('\\', '/');
                    log.info(logEntry + "configuration file set to " + sub);
                    configFileReader.setFile(sub);

                }

                checkMe = "-VehicleSpecFile:";
                if (arg.startsWith(checkMe)) {
                    String sub = arg.substring(checkMe.length()).replace('\\', '/');
                    log.info(logEntry + "vehicleSpec file set to " + sub);
                    vehicleSpecReader.setFile(sub);

                }

                checkMe = "-SourceFile:";
                if (arg.startsWith(checkMe)) {
                    String sub = arg.substring(checkMe.length()).replace('\\', '/');
                    log.info(logEntry + "source file set to " + sub);
                    fileName = new File(sub);

                }
                checkMe = "-Destination:";
                if (arg.startsWith(checkMe)) {
                    String sub = arg.substring(checkMe.length()).replace('\\', '/');
                    log.info(logEntry + "destination set to " + sub);
                    this.directory = new File(sub);


                }

            }

        }

    }

}