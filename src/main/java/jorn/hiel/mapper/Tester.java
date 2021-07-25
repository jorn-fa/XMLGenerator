package jorn.hiel.mapper;

import jorn.hiel.mapper.service.I3DMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.xml.sax.SAXException;

import javax.xml.parsers.ParserConfigurationException;
import java.io.IOException;

@Service
public class Tester {

    @Autowired
    I3DMapper mapper;

    public void runMe(){
        System.out.println("yues");
        mapper.setFile("c:/temp/climber10.i3d");


        try {
            mapper.process();
            mapper.printList();
        } catch (ParserConfigurationException e) {
            e.printStackTrace();
        } catch (SAXException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();

        }
    }


}
