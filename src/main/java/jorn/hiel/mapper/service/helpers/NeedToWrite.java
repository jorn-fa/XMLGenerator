package jorn.hiel.mapper.service.helpers;

import jorn.hiel.mapper.service.ConfigFileReader;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

 @Service
 @Slf4j
public class NeedToWrite {

     @Autowired
     ConfigFileReader configFileReader;

    public boolean needsToWrite(String filter){



        if (configFileReader.getMappedItem("writeAll").getValue().equals("true")){
            //add a fillunit
            if(filter.equals("fillUnit")){
                log.info("Adding fillUnit trough fullWrite");
                configFileReader.addFillUnit();}
            if(filter.equals("motorized")){
                log.info("Adding consumer trough fullWrite");
                configFileReader.addConsumer();
                log.info("Adding exhaust trough fullWrite");
                configFileReader.addExhaust();
            }
            if(filter.equals("enterable")) {
                log.info("Adding mirror trough fullWrite");
                configFileReader.addMirror();
            }





            return true;



        }


        return configFileReader.getMappedItem(filter).equals("true");


    }



}
