package jorn.hiel.mapper;

import jorn.hiel.mapper.pojo.MappedItem;
import jorn.hiel.mapper.pojo.TranslationItem;
import jorn.hiel.mapper.service.I3DMapper;
import org.json.simple.parser.ParseException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.xml.sax.SAXException;

import javax.xml.parsers.ParserConfigurationException;
import java.io.IOException;


@Service
public class Tester {

    @Autowired
    I3DMapper mapper;

    @Autowired
    TranslationFileReader translation;


    private final String source = "e:/temp/translations.json";

    public void runMe() {
        mapper.setFile("c:/temp/climber10.i3d");
        mapper.clearRepo();
        translation.setFile(source);

        try {
            mapper.process();
            mapper.printList();
            translation.process();

            mapper.repo.getItems().forEach(a -> {
                for (TranslationItem item : translation.getTranslations()) {
                    if (item.getTranslationItems().contains(a.getNode())) {
                        mapper.addEntry(new MappedItem().setKey(item.getItem()).setValue(a.getNode()));

                    }
                }

            });

            System.out.println("results:");
            System.out.println(mapper.getMappedItems().size() + " Items");
            mapper.getMappedItems().forEach(b -> {
                System.out.println(b);

            });

            System.out.println();



        } catch (ParserConfigurationException | SAXException | IOException e ) {
            e.printStackTrace();
        } catch (ParseException h) {
            System.out.println("problem with json : " + h);

        }

        translation.getTranslations().forEach(a -> System.out.println(a));
    }


}
