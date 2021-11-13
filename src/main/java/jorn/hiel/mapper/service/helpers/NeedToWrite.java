package jorn.hiel.mapper.service.helpers;

import jorn.hiel.mapper.pojo.Specialization;
import jorn.hiel.mapper.service.ConfigFileReader;
import jorn.hiel.mapper.service.I3DMapper;
import jorn.hiel.mapper.service.VehicleSpecReader;
import jorn.hiel.mapper.service.enums.VehicleSpec;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
 @Slf4j
public class NeedToWrite {

     @Autowired
     ConfigFileReader configFileReader;

     @Autowired
     I3DMapper mapper;

     @Autowired
     VehicleSpecReader specReader;


     boolean hasWritten = false;

     Specialization currentSpec;

     public boolean needsToWrite(VehicleSpec filter) {

         setCurrentVehicleType();


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
                         hasWritten = true;
                     }
                     break;
             }


             return true;
         }

         //todo
         //return configFileReader.getMappedItem(filter.toString()).equals("true");
         return true;

     }

     private void setCurrentVehicleType() {

         if (currentSpec == null) {

             //get type from configuration file
             String type = configFileReader.getMappedItem("vehicleType").getValue();
             log.info("Setting vehicle type to -> " + type);

             if (mapper.getEntryRepo().getItems().containsKey("vehicleType")) {
                 type = mapper.getEntryRepo().getItems().get("vehicleType");
                 log.info("Found vehicle type in I3D file -> Setting vehicle type to -> " + type);
             }

             String finalType = type;
             currentSpec = specReader.getRepo().getItems().stream()
                     .filter(a -> a.getName().equals(finalType))
                     .findAny()
                     .orElse(null);


         }
     }


 }




