package jorn.hiel.mapper.service;

import jorn.hiel.mapper.pojo.MappedItem;
import jorn.hiel.mapper.service.repo.implementations.ConfigRepo;
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
import java.util.Map;

@Service
@NoArgsConstructor
@Slf4j
public class ConfigFileReader {



    @Getter @Setter
    private String file="";

    @Autowired
    private ConfigRepo repo;

    @SuppressWarnings("unchecked")
    public void process() throws FileNotFoundException,ParseException {
        log.info("reading file with name -> " + file);
        File sourceFile=new File(file);
        if (sourceFile.exists()) {

            JSONParser parser = new JSONParser();
            try (FileReader reader = new FileReader(file)) {
                //Read JSON file
                Object obj = parser.parse(reader);
                JSONArray configurations = (JSONArray) obj;
                //iterate over each entry and parse it
                configurations.forEach( a -> parseConfiguration((JSONObject) a)) ;


            } catch (IOException e) {

                e.printStackTrace();
            }
        }
        else throw new FileNotFoundException(file);

    }

    @SuppressWarnings("unchecked")
    private void parseConfiguration(JSONObject jsonObject){
        JSONObject object = (JSONObject) jsonObject.get("config");


        MappedItem mappedItem = new MappedItem();
        mappedItem.setKey((String)object.get("key"));
        mappedItem.setValue((String)object.get("user"));

        log.info("created translation of -> "  + mappedItem);
        repo.add(mappedItem);
    }


    public Map<String, String> getMappedItems(){
        return repo.getItems();
    }


    /**
     *
     * @param key
     * @return mappedItem
     *
     * Will return a mapped item if found, if not found will return<br>
     * the user's UnknownEntry value
     */

    public MappedItem getMappedItem(String key){
        String value = repo.getItems().get(key);

        if (value==null){
            value=repo.getItems().get("UnknownEntry");
        }

        return new MappedItem().setKey(key).setValue(value);
    }

}