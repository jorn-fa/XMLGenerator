package jorn.hiel.mapper.service.writers;

import jorn.hiel.mapper.service.ConfigFileReader;
import jorn.hiel.mapper.service.interfaces.DocWriter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;

import java.util.List;
@Service
public class AnimationWriter implements DocWriter {


    @Autowired
    ConfigFileReader configFileReader;

    public void write(Document doc){

        if (configFileReader.getAnimations().size()>0){

            Node rootElement = doc.getElementsByTagName("Vehicle").item(0);

            Node animations = doc.getElementsByTagName("animations").item(0);
            //create when not found
            if (animations==null){animations=doc.createElement("animations");}


            Node finalAnimations = animations;
            configFileReader.getAnimations().forEach((name,item)-> {


            Element animation = doc.createElement("animation");
            animation.setAttribute("name",name);

            Element part = doc.createElement("part");
            part.setAttribute("node",item.equals("")?configFileReader.getMappedItem("UnknownEntry").getValue():item);


            //animition attributes
            List<String> names = List.of("startTime","endTime","startRot","endRot");
            names.forEach(b-> part.setAttribute(b, configFileReader.getMappedItem("UnknownEntry").getValue()));



            animation.appendChild(part);
            finalAnimations.appendChild(animation);
        }
            );

            rootElement.appendChild(animations);


        }


    }

}
