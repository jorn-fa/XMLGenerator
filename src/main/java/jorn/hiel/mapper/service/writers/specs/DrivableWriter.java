package jorn.hiel.mapper.service.writers.specs;

import jorn.hiel.mapper.service.ConfigFileReader;
import jorn.hiel.mapper.service.enums.VehicleSpec;
import jorn.hiel.mapper.service.helpers.NeedToWrite;
import jorn.hiel.mapper.service.interfaces.DocWriter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class DrivableWriter implements DocWriter {

    @Autowired
    ConfigFileReader configFileReader;

    @Autowired
    NeedToWrite needToWrite;

    Document doc;


    @Override
    public void write(Document doc) {

        if (needToWrite.needsToWrite(VehicleSpec.DRIVABLE)) {

            this.doc=doc;

            Node rootElement = doc.getElementsByTagName("vehicle").item(0);



            Element drivable = doc.createElement("drivable");
            Element sounds = doc.createElement("sounds");
            Element dashboards = doc.createElement("dashboards");

            //steeringWheel
            Element steeringWheel=doc.createElement("steeringWheel");
            needToWrite.decide("steeringWheel",steeringWheel,"node");
            needToWrite.decide("steeringWheelIndoorRotation",steeringWheel,"indoorRotation");
            needToWrite.decide("steeringWheelOutdoorRotation",steeringWheel,"outdoorRotation");


            //sounds
            Element waterSplash = doc.createElement("waterSplash");
            waterSplash.setAttribute("template","WATER_SPLASH_01");
            waterSplash.setAttribute("linkNode",configFileReader.getMappedItem("waterSplash").getValue());
            sounds.appendChild(waterSplash);

            //dashboards

            List<String> dashboardTypes = List.of("gasPedal","brakePedal");

            dashboardTypes.forEach(a -> createDashboard(a,dashboards));





            rootElement.appendChild(drivable);
            drivable.appendChild(sounds);
            drivable.appendChild(dashboards);
            drivable.appendChild(steeringWheel);


        }
    }

    private void createDashboard(String filter, Element parent) {
        Map<String,String> items = new HashMap<>();
        items.put("displayType","rot");
        items.put("valueType","accelerationAxis");



        items.put("minRot","0 0 0");
        items.put("maxRot","0 0 0");
        items.put("doInterpolation","true");
        items.put("groups","MOTOR_ACTIVE");

       //add dashboard when found or when full write is active
       if( configFileReader.getMappedItem("writeAll").getValue().equals("true") ||
               !configFileReader.getMappedItem("waterSplash").getValue().equals(configFileReader.getMappedItem("UnknownEntry").getValue())

       )  {
           if (filter.equals("brakePedal")){
               items.put("valueType","decelerationAxis");

           }
           if (filter.equals("gasPedal")){
               items.put("valueType","accelerationAxis");

           }


           Element element = doc.createElement("dashboard");
           items.forEach((key, value) -> element.setAttribute(key, value));
           needToWrite.decide(filter,element,"node");
           parent.appendChild(element);
       }
    }
}