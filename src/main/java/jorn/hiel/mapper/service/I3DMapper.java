package jorn.hiel.mapper.service;

import jorn.hiel.mapper.pojo.I3dMap;
import jorn.hiel.mapper.pojo.MappedItem;
import jorn.hiel.mapper.service.repo.I3dMapRepo;
import jorn.hiel.mapper.service.repo.implementations.ConfigRepo;
import jorn.hiel.mapper.service.repo.implementations.EntryRepo;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.w3c.dom.Document;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.ParserConfigurationException;
import java.io.File;
import java.io.IOException;
import java.util.Locale;
import java.util.Map;

@Slf4j
@Service
@NoArgsConstructor
public class I3DMapper {


    @Autowired
    public I3dMapRepo repo;

    @Autowired
    private DocBuildFactory dbFactory;

    @Autowired
    private EntryRepo entryRepo;

    @Autowired
    ConfigRepo configRepo;

    @Getter
    String fileName;


    private File inputFile;



    /**
     * Resets internal repositories.<br>
     * Repo's reset are =<br>
     * I3DMap <br>
     * Entry
     */
    public void clearRepo() {
        repo.clearRepo();
        entryRepo.clearRepo();
    }

    public void setFile(String file) {

        File test = new File(file);
        log.debug(test.getAbsolutePath());
        this.inputFile = test;
        log.info("setting file with name ->" + file);
        this.fileName=test.getName();

    }

    /**
     * Processes the set i3d file:
     *
     * @throws ParserConfigurationException
     * @throws SAXException
     * @throws IOException
     */
    public void process() throws ParserConfigurationException, SAXException, IOException, NoSuchFieldException {

        DocumentBuilder builder = dbFactory.getSecureDbf().newDocumentBuilder();

        if (!repo.getItems().isEmpty()) {
            repo.clearRepo();
        }

        Document document = builder.parse(inputFile.getAbsolutePath());
        document.getDocumentElement().normalize();
        NodeList nList = document.getElementsByTagName("Scene");
        NodeList temp = nList.item(0).getChildNodes();
        //counter for non binairy save
        if (nList.getLength()==2){temp = nList.item(1).getChildNodes();}

        System.out.println( nList.getLength());

        int counter = 0;
        for (int a = 0; a < temp.getLength(); a++) {
            if (temp.item(a).getNodeName().equals("Shape")) {
                mapChildren(temp.item(a).getChildNodes(), counter + ">");

                String id = counter + ">";
                String name = temp.item(a).getAttributes().getNamedItem("name").getNodeValue();

                I3dMap i3dMap = new I3dMap().setNode(name).setId(id);

                System.out.println("**--**--**");
                System.out.println(i3dMap);

                repo.add(i3dMap);
                counter++;
            }
        }
    }

    private void mapChildren(NodeList nodeList, String index) {
        int counter = 0;
        for (int a = 0; a < nodeList.getLength(); a++) {
            if (nodeList.item(a).hasAttributes()) {

                String id = index + counter;
                String name = nodeList.item(a).getAttributes().getNamedItem("name").getNodeValue();

                //todo might need to alter to accept multiple entries
                if(name.startsWith("XMLData")){
                    processName(name);
                }

                else {
                    I3dMap i3dMap = new I3dMap().setNode(name).setId(id);
                    repo.add(i3dMap);
                }

                if (nodeList.item(a).hasChildNodes()) {
                    mapChildren(nodeList.item(a).getChildNodes(), index + counter + "|");
                }

                counter++;
            }

        }

    }


    /**
     * Splices a given string in to multiple entries and adds it to the correct repo
     * @param name  A string containing 3 entries
     *              <br>  example =   StoreData:Speed:50
     *
     */
    private void processName(String name) {

        String[] splice = name.split(":");
        //QOL ->  brand to uppercase
        if(splice[1].equals("brand")){splice[2]=splice[2].toUpperCase(Locale.ROOT);}
        entryRepo.add(new MappedItem().setKey(splice[1]).setValue(splice[2]));

    }

    /**
     * Generate a logged representation of the mapped content
     */
    public void printList() {
        repo.printList();

    }


    public void addEntry(MappedItem mappedItem) {

        entryRepo.add(mappedItem);

    }

    public Map<String, String> getMappedItems() {
        return entryRepo.getItems();
    }

    public MappedItem getMappedItem(String key) {
        if (entryRepo.getItems().get(key)==null){
            return new MappedItem().setKey(key).setValue(configRepo.getItems().get("UnknownEntry"));
        }

        return new MappedItem().setKey(key).setValue(entryRepo.getItems().get(key));

    }

}
