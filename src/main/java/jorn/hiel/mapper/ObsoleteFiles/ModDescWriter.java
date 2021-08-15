package jorn.hiel.mapper.ObsoleteFiles;

import com.sun.xml.txw2.output.IndentingXMLStreamWriter;
import jorn.hiel.mapper.pojo.MappedItem;
import jorn.hiel.mapper.service.ConfigFileReader;
import jorn.hiel.mapper.service.I3DMapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.xml.stream.XMLOutputFactory;
import javax.xml.stream.XMLStreamException;
import javax.xml.stream.XMLStreamWriter;
import java.io.*;

@Service
@Slf4j
public class ModDescWriter  {


    @Autowired
    I3DMapper mapper;

    @Autowired
    ConfigFileReader configFileReader;



    private boolean canWrite;


    private XMLStreamWriter writer;


    public void setFileLocation(String fileLocation) throws XMLStreamException, FileNotFoundException, UnsupportedEncodingException {

        OutputStream outputStream = new FileOutputStream(new File(fileLocation));


        XMLOutputFactory xmlof = XMLOutputFactory.newInstance();
        writer = new IndentingXMLStreamWriter(xmlof.createXMLStreamWriter(new OutputStreamWriter(outputStream, "utf-8")));
        this.canWrite=true;

    }

    public void writeModdesc() throws XMLStreamException {



        if(canWrite){
            MappedItem mappedItem;
            writer.writeStartDocument();


            writer.writeStartElement("modDesc");

            //descVersion Attribute
            mappedItem=configFileReader.getMappedItem("descVersion");
            writer.writeAttribute(mappedItem.getKey(),mappedItem.getValue());

            //author
            mappedItem=configFileReader.getMappedItem("author");
            //addSingleXmlItem(writer,mappedItem);

            //version
            mappedItem=configFileReader.getMappedItem("version");
            //addSingleXmlItem(writer,mappedItem);

            //multiplayer
            mappedItem=configFileReader.getMappedItem("multiplayer");
            //addSingleItemWithAttribute(writer,mappedItem,"supported");

            //title

            //description

            //icon
            mappedItem=configFileReader.getMappedItem("iconFilename");
            //addSingleXmlItem(writer,mappedItem);


            //storeItem



            writer.writeEndElement();
            writer.writeEndDocument();
            writer.flush();
            writer.close();

        }


    }

}
