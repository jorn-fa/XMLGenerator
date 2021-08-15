package jorn.hiel.mapper.service.writers;

import jorn.hiel.mapper.pojo.MappedItem;
import jorn.hiel.mapper.service.ConfigFileReader;
import jorn.hiel.mapper.service.I3DMapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.xml.stream.XMLOutputFactory;
import javax.xml.stream.XMLStreamException;
import javax.xml.stream.XMLStreamWriter;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;

@Service
@Slf4j
public class ModDescWriter {


    @Autowired
    I3DMapper mapper;

    @Autowired
    ConfigFileReader configFileReader;



    private boolean canWrite;

    private XMLOutputFactory output;
    private XMLStreamWriter writer;


    public void setFileLocation(String fileLocation) throws XMLStreamException, FileNotFoundException {
        XMLOutputFactory output = XMLOutputFactory.newInstance();

            writer = output.createXMLStreamWriter(new FileOutputStream(fileLocation));
            this.canWrite=true;

    }

    public void writeModdesc() throws XMLStreamException {



        if(canWrite){
            MappedItem mappedItem = new MappedItem();


            writer.writeStartDocument();
            writer.writeStartElement("modDesc");

            //descVersion Attribute
            mappedItem=configFileReader.getMappedItem("descVersion");
            writer.writeAttribute(mappedItem.getKey(),mappedItem.getValue());
            


            writer.writeEndDocument();
            writer.flush();
            writer.close();

        }


    }

}
