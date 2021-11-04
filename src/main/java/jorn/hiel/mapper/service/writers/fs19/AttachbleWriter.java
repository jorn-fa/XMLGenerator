package jorn.hiel.mapper.service.writers.fs19;

import jorn.hiel.mapper.service.ConfigFileReader;
import jorn.hiel.mapper.service.I3DMapper;
import jorn.hiel.mapper.service.helpers.NeedToWrite;
import jorn.hiel.mapper.service.interfaces.DocWriter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
@Component
public class AttachbleWriter implements DocWriter {

    @Autowired
    I3DMapper mapper;

    @Autowired
    NeedToWrite needToWrite;

    @Autowired
    ConfigFileReader configFileReader;


    @Override
    public void write(Document doc) {

        if (needToWrite.needsToWrite("attachable")) {
            Node rootElement = doc.getElementsByTagName("Vehicle").item(0);
            {

                Element attachable = doc.createElement("attachable");
                rootElement.appendChild(attachable);{

                Element inputAttacherJoints = doc.createElement("inputAttacherJoints");
                attachable.appendChild(inputAttacherJoints);

                int needed = Integer.parseInt(configFileReader.getMappedItem("numberOfInputAttacherJoints").getValue());

                for (int x=0;x<needed;x++) {
                    String item ="inputAttacherJoint";
                    Element inputAttacherJoint = doc.createElement(item);
                    inputAttacherJoints.appendChild(inputAttacherJoint);
                    inputAttacherJoint.setAttribute("node", mapper.getMappedItem(item+x+"Node").getValue());
                    inputAttacherJoint.setAttribute("jointType", mapper.getMappedItem(item+x+"JointType").getValue());
                    inputAttacherJoint.setAttribute("attacherHeight", mapper.getMappedItem(item+x+"Node").getValue());
                }

                String animName=configFileReader.getMappedItem("moveSupport").getValue();
                String unknown = configFileReader.getMappedItem("UnknownEntry").getValue();
                if(animName.equals(unknown)){animName="moveSupport";}
                configFileReader.addAnimation(animName,"");
                Element support = doc.createElement("support");
                attachable.appendChild(support);
                support.setAttribute("animationName",animName);

                Element brakeForce = doc.createElement("brakeForce");
                attachable.appendChild(brakeForce);
                brakeForce.setTextContent("1.2");







}}}}}
