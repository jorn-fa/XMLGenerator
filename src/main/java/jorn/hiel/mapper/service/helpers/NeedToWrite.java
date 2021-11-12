package jorn.hiel.mapper.service.helpers;

import jorn.hiel.mapper.service.ConfigFileReader;
import jorn.hiel.mapper.service.enums.VehicleSpec;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

 @Service
 @Slf4j
public class NeedToWrite {

     @Autowired
     ConfigFileReader configFileReader;

     boolean hasWritten = false;

     public boolean needsToWrite(VehicleSpec filter) {


         if (configFileReader.getMappedItem("writeAll").getValue().equals("true")) {

             switch (filter) {
                 case FILLUNIT:
                     log.info("Adding fillUnit trough fullWrite");
                     configFileReader.addFillUnit();
                     break;

                 case MOTORIZED:
                     log.info("Adding consumer trough fullWrite");
                     configFileReader.addConsumer();
                     log.info("Adding exhaust trough fullWrite");
                     configFileReader.addExhaust();
                     break;

                 case ENTERABLE:
                     log.info("Adding mirror trough fullWrite");
                     configFileReader.addMirror();
                     break;

                 default:

                 if (!hasWritten) {
                     configFileReader.addSpeedRotatingPart("fullWrite", "from fullWrite");
                     log.info("Adding 1 speedRotatingPart trough fullWrite");
                 }
                 break;
             }


                 return true;
             }






             //todo
          //return configFileReader.getMappedItem(filter.toString()).equals("true");
         return true;


         }



}

