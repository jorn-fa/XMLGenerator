package jorn.hiel.mapper.service.writers;

import jorn.hiel.mapper.service.ConfigFileReader;
import jorn.hiel.mapper.service.helpers.NeedToWrite;
import jorn.hiel.mapper.service.interfaces.DocWriter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;

@Service
public class DashboardWriter implements DocWriter {

    @Autowired
    ConfigFileReader configFileReader;

    @Autowired
    NeedToWrite needToWrite;


    @Override
    public void write(Document doc) {

        Node rootElement = doc.getElementsByTagName("Vehicle").item(0);

            if (needToWrite.needsToWrite("dashboard")) {

                Element dashboard = doc.createElement("dashboard");
                Element groups = doc.createElement("groups");
                Element defaultElement = doc.createElement("default");

                Element group1 = doc.createElement("group");
                group1.setAttribute("name","MOTOR_STARTING");
                group1.setAttribute("isMotorStarting","true");
                Element group2 = doc.createElement("group");
                group2.setAttribute("name","MOTOR_ACTIVE");
                group2.setAttribute("isMotorStarting","true");
                group2.setAttribute("isMotorRunning","true");


                Element dashboardDef = doc.createElement("dashboard");
                defaultElement.appendChild(dashboardDef);

                dashboardDef.setAttribute("displayType","EMITTER");
                dashboardDef.setAttribute("node",configFileReader.getMappedItem("dashboard").getValue());
                dashboardDef.setAttribute("idleValue","-1");
                dashboardDef.setAttribute("intensity","0.3");
                dashboardDef.setAttribute("groups","MOTOR_ACTIVE");




                rootElement.appendChild(dashboard);
                dashboard.appendChild(groups);
                dashboard.appendChild(defaultElement);
                groups.appendChild(group1);
                groups.appendChild(group2);



            }


        }



}
