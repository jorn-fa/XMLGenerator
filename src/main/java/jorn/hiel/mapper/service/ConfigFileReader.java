package jorn.hiel.mapper.service;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import jorn.hiel.mapper.pojo.MappedItem;
import jorn.hiel.mapper.service.repo.implementations.ConfigRepo;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;
import org.json.simple.parser.ParseException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

@Service
@NoArgsConstructor
@Slf4j
public class ConfigFileReader {

    @Autowired
    ConfigFileCreator configFileCreator;



    @Getter @Setter
    private String file="";

    @Autowired
    private ConfigRepo repo;

    @Getter
    HashMap<String,String> animations = new HashMap<>();

    @Getter
    HashMap<String,String> speedRotatingParts = new HashMap<>();




    public void process() throws FileNotFoundException,ParseException {
        configFileCreator.runMe();
        log.info("reading file with name -> " + file);

        if (new File(file).exists()) {

            try  {

                ObjectMapper mapper = new ObjectMapper();
                TypeReference<HashMap<String, String>> typeRef= new TypeReference<>() {
                };
                Map<String,String>configuration=mapper.readValue(new File(file),typeRef);
                log.info("found item count -> "+configuration.size());
                configuration.forEach(this::parseConfiguration);


            } catch (IOException e) {

                e.printStackTrace();
            }
        }
        else throw new FileNotFoundException(file);

    }

    private void parseConfiguration(String key,String value){

        MappedItem mappedItem = new MappedItem();
        mappedItem.setKey(key);
        mappedItem.setValue(value);

        log.info("created configuration of -> "  + mappedItem);
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

    public void update(MappedItem mappedItem){
        if (repo.getItems().containsKey(mappedItem.getKey())  ){
            repo.getItems().remove(mappedItem.getKey());
            repo.getItems().put(mappedItem.getKey(), mappedItem.getValue());
        }
    }

    /**
     *
     * @param animName name of the wanted animation
     * @param itemName the object that needs to be animated
     */
    public void addAnimation(String animName, String itemName) {
        animations.put(animName, itemName);
    }

    public void addSpeedRotatingPart(String speedRotatingPart, String itemName) {
        speedRotatingParts.put(speedRotatingPart, itemName);
    }


    public void addFillUnit(){
        MappedItem item = getMappedItem("numberOfFillUnits");

        int howMany=Integer.parseInt(item.getValue());
        item.setValue(String.valueOf(++howMany));

        update(item);

    }

    public void addConsumer(){
        MappedItem item = getMappedItem("numberOfConsumers");

        int howMany=Integer.parseInt(item.getValue());
        item.setValue(String.valueOf(++howMany));

        update(item);

    }

    public void addExhaust(){
        MappedItem item = getMappedItem("numberOfExhaust");

        int howMany=Integer.parseInt(item.getValue());
        item.setValue(String.valueOf(++howMany));

        update(item);

    }

    public void addMirror(){
        MappedItem item = getMappedItem("numberOfMirrors");

        int howMany=Integer.parseInt(item.getValue());
        item.setValue(String.valueOf(++howMany));

        update(item);

    }


}
