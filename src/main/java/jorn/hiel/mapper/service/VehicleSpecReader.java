package jorn.hiel.mapper.service;

import jorn.hiel.mapper.pojo.Specialization;
import jorn.hiel.mapper.service.enums.VehicleSpec;
import jorn.hiel.mapper.service.repo.VehicleSpecRepo;
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
import java.util.Locale;

@Service
@NoArgsConstructor
@Slf4j
public class VehicleSpecReader {

    @Getter
    @Setter
    private String file="";

    @Autowired @Getter
    VehicleSpecRepo repo;

    @SuppressWarnings("unchecked")
    public void process() throws FileNotFoundException, ParseException {
        log.info("reading file with name -> " + file);
        File sourceFile=new File(file);
        if (sourceFile.exists()) {

            JSONParser parser = new JSONParser();
            try (FileReader reader = new FileReader(file)) {
                //Read JSON file
                Object obj = parser.parse(reader);
                JSONArray translations = (JSONArray) obj;
                //iterate over each entry and parse it
                translations.forEach( a -> parseSpecialization((JSONObject) a)) ;


            } catch (IOException e) {

                e.printStackTrace();
            }
        }
        else throw new FileNotFoundException(file);

    }

    @SuppressWarnings("unchecked")
    private void parseSpecialization(JSONObject jsonObject) {
        JSONObject object = (JSONObject) jsonObject.get("spec");

        Specialization specialization = new Specialization();

        specialization.setName((String)object.get("name"));
        specialization.setVersion((String)object.get("version"));


        JSONArray jsonArray = (JSONArray) object.get("needs");
        jsonArray.forEach(x -> specialization.addSpecialization(VehicleSpec.valueOf(x.toString().toUpperCase(Locale.ROOT))));



        log.info("created specialization of -> "  + specialization);

        repo.add(specialization);
    }


}
