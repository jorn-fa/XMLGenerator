package jorn.hiel.mapper;

import jorn.hiel.mapper.pojo.MappedItem;
import jorn.hiel.mapper.pojo.TranslationItem;
import jorn.hiel.mapper.service.ConfigFileReader;
import jorn.hiel.mapper.service.I3DMapper;
import jorn.hiel.mapper.service.TranslationFileReader;
import jorn.hiel.mapper.service.helpers.UnknownStringCounter;
import jorn.hiel.mapper.service.writers.ModDescWriterDom;
import org.json.simple.parser.ParseException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.xml.sax.SAXException;

import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.TransformerException;
import java.io.IOException;
import java.nio.file.Path;


@Service
public class Tester {

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


    private final String source = "e:/temp/translations.json";
    private final String configFile = "e:/temp/config.json";

    public void runMe() {

        String file="c:/temp/climber10.i3d";







        mapper.setFile(file);
        mapper.clearRepo();
        translation.setFile(source);
        configFileReader.setFile(configFile);

        try {
            mapper.process();
            mapper.printList();
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

            mapper.addEntry(new MappedItem().setKey("fileName").setValue(Path.of(file).getFileName().toString()));

            System.out.println("results:");
            System.out.println(mapper.getMappedItems().size() + " Items");


            mapper.getMappedItems().forEach((k,v) -> System.out.println("key " + k + "  //  value = " +v));


            System.out.println();



        } catch (ParserConfigurationException | SAXException | IOException e ) {
            e.printStackTrace();
        } catch (ParseException h) {
            System.out.println("problem with json : " + h);

        }

        translation.getTranslations().forEach(a -> System.out.println(a));


        System.out.println("-----------------");

        System.out.println("config found items ->");
        configFileReader.getMappedItems().forEach((k,v) -> System.out.println("key " + k + "  //  value = " +v));



        try {

            modDescWriterDom.setFileLocation("e:/temp/moddesc.xml");
            modDescWriterDom.writeModDesc();

            System.out.println("*********");


            System.out.println("unknown found = " + unknownCounter.countEntries(Path.of("e:/temp/moddesc.xml")));



        } catch ( ParserConfigurationException | TransformerException|IOException e) {
            e.printStackTrace();
        }

    }



}
