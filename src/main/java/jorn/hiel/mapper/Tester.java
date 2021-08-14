package jorn.hiel.mapper;

import jorn.hiel.mapper.pojo.TranslationItem;
import jorn.hiel.mapper.service.ConfigurationReader;
import jorn.hiel.mapper.service.I3DMapper;
import org.json.simple.parser.ParseException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.xml.sax.SAXException;

import javax.xml.parsers.ParserConfigurationException;
import java.io.IOException;
import java.lang.reflect.Field;




@Service
public class Tester {

    @Autowired
    I3DMapper mapper;

    @Autowired
    TranslationFileReader translation;

    private String source = "e:/temp/translations.json";

    public void runMe(){
        mapper.setFile("c:/temp/climber10.i3d");
        translation.setFile(source);

        try {
            mapper.process();
            mapper.printList();
            translation.process();

            mapper.repo.getItems().forEach(a-> {
               for (TranslationItem item  :translation.getTranslations()){
                   if(item.getTranslationItems().contains(a.getNode())){
                       System.out.println("found: " + item.getItem());
                   }
               }
            });


        } catch (ParserConfigurationException e) {
            e.printStackTrace();
        } catch (SAXException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (ParseException e) {
            //todo
            System.out.println("problem with json : " + e.toString());

        }

        translation.getTranslations().forEach(a-> System.out.println(a));
    }




}
