package jorn.hiel.mapper;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

public class Runner {

    public static void main(String[] args) throws IOException {





        File file = new File("C:/Users/Jorn/XMLGenerator/config.json");

        ObjectMapper mapper = new ObjectMapper();
        TypeReference<HashMap<String, String>> typeRef = new TypeReference<HashMap<String, String>>() {
        };
        Map<String, String> destination = mapper.readValue(file, typeRef);
        System.out.println(destination.size());
        destination.forEach((k,v)-> System.out.println(k+" + "+v));


    }

}
