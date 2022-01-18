package jorn.hiel.mapper.service.helpers;

import jorn.hiel.mapper.pojo.Specialization;
import jorn.hiel.mapper.service.ConfigFileReader;
import jorn.hiel.mapper.service.I3DMapper;
import jorn.hiel.mapper.service.VehicleSpecReader;
import jorn.hiel.mapper.service.enums.GameVersion;
import jorn.hiel.mapper.service.enums.VehicleSpec;
import lombok.Getter;
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

     @Getter
     GameVersion gameVersion;

     @Getter
     private boolean fullWrite=false;

     public void init(){
         setGameVersion();
         setCurrentVehicleType();
         if(configFileReader.getMappedItem("writeAll").getValue().equals("true")){
             fullWrite=true;
         }
     }

     public boolean needsToWrite(VehicleSpec filter) {



         if ((fullWrite)) {

             //fullWrite=true;

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
         return currentSpec.getSpecs().contains(filter);


     }

     public void setCurrentVehicleType() {

         if (currentSpec == null) {

             //get type from configuration file
             String type = configFileReader.getMappedItem("vehicleType").getValue();
             log.info("Setting (configuration file) vehicle type to -> " + type);

             if (mapper.getEntryRepo().getItems().containsKey("vehicleType")) {
                 type = mapper.getEntryRepo().getItems().get("vehicleType");
                 log.info("Found vehicle type in I3D file -> Setting vehicle type to -> " + type);
             }



             String finalType = type;
             currentSpec = specReader.getRepo().getItems().stream()
                     .filter(a -> a.getName().equals(finalType))
                     .filter(b -> b.getGameVersion().equals(gameVersion))
                     .findAny()
                     .orElse(null);


         }
     }


     public void setGameVersion(){

         int version;

         if (gameVersion==null){
             //get type from configuration file

             version = Integer.parseInt(configFileReader.getMappedItem("modDescVersion").getValue());

             if (mapper.getEntryRepo().getItems().containsKey("modDescVersion")) {
                 version = Integer.parseInt(mapper.getEntryRepo().getItems().get("modDescVersion"));
                 log.info("Found game version "+version+" in I3D file");
             }

             if (version<=54) { gameVersion=GameVersion.FS19 ;}
             if (version>=61) { gameVersion=GameVersion.FS22 ;}



             log.info("Setting modDescVersion to -> " + gameVersion);


         }
     }


 }




