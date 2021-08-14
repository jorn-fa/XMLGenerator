package jorn.hiel.mapper;

import jorn.hiel.mapper.pojo.TranslationItem;
import jorn.hiel.mapper.service.repo.TranslationsRepo;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.Collections;
import java.util.List;

@Service
@NoArgsConstructor
@Slf4j
public class TranslationFileReader {



    @Getter @Setter
    private String file="";

    @Autowired
    private TranslationsRepo repo;

    @SuppressWarnings("unchecked")
    public void process() throws FileNotFoundException,ParseException {
        log.info("reading file with name -> " + file);
        File sourceFile=new File(file);
        if (sourceFile.exists()) {

            JSONParser parser = new JSONParser();
            try (FileReader reader = new FileReader(file)) {
                //Read JSON file
                Object obj = parser.parse(reader);
                JSONArray translations = (JSONArray) obj;
                //iterate over each entry and parse it
                translations.forEach( a -> parseTranslationObject((JSONObject) a)) ;


            } catch (IOException e) {

                e.printStackTrace();
            }
        }
        else throw new FileNotFoundException(file);

    }

    @SuppressWarnings("unchecked")
    private void parseTranslationObject(JSONObject jsonObject){
        JSONObject object = (JSONObject) jsonObject.get("translation");

        TranslationItem  translation = new TranslationItem();
        translation.setItem((String)object.get("item"));
        JSONArray jsonArray = (JSONArray) object.get("searchFor");
        jsonArray.forEach(x -> translation.addTranslation((String) x));

        log.info("created translation of -> "  + translation);
        repo.add(translation);
    }


    public List<TranslationItem> getTranslations(){
        return Collections.unmodifiableList(repo.getItems());
    }

}
