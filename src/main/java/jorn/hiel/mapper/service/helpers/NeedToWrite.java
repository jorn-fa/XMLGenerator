package jorn.hiel.mapper.service.helpers;

import jorn.hiel.mapper.service.ConfigFileReader;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

 @Service
public class NeedToWrite {

     @Autowired
     ConfigFileReader configFileReader;

    public boolean needsToWrite(String filter){

        if (configFileReader.getMappedItem("writeAll").getValue().equals("true")){
            return true;
        }

        return configFileReader.getMappedItem(filter).equals("true");


    }

}
