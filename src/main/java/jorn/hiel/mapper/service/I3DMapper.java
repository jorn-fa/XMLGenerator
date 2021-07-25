package jorn.hiel.mapper.service;

import jorn.hiel.mapper.pojo.I3dMap;
import jorn.hiel.mapper.service.repo.I3dMapRepo;
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

@Slf4j
@Service
@NoArgsConstructor
public class I3DMapper {


    @Autowired
    public I3dMapRepo repo;

    @Autowired
    private DocBuildFactory dbFactory;

    private File inputFile;


    public void clearRepo() {
        repo.clearRepo();
    }

    public void setFile(String file) {

        File test = new File(file);
        log.debug(test.getAbsolutePath());
        this.inputFile = test;

    }

    /**
     * Processes the set i3d file:
     *
     * @throws ParserConfigurationException
     * @throws SAXException
     * @throws IOException
     */
    public void process() throws ParserConfigurationException, SAXException, IOException {

        DocumentBuilder builder = dbFactory.getSecureDbf().newDocumentBuilder();

        if (!repo.getItems().isEmpty()) {
            repo.clearRepo();
        }

        Document document = builder.parse(inputFile.getAbsolutePath());
        document.getDocumentElement().normalize();
        NodeList nList = document.getElementsByTagName("Scene");
        NodeList temp = nList.item(0).getChildNodes();

        int counter = 0;
        for (int a = 0; a < temp.getLength(); a++) {
            if (temp.item(a).getNodeName().equals("Shape")) {
                mapChildren(temp.item(a).getChildNodes(), String.valueOf(counter + ">"));

                String id = String.valueOf(counter + ">");
                String name = temp.item(a).getAttributes().getNamedItem("name").getNodeValue();

                I3dMap i3dMap = new I3dMap().setNode(name).setId(id);

                repo.add(i3dMap);
                counter++;
            }
        }
    }

    private void mapChildren(NodeList nodeList, String index) {
        int counter = 0;
        for (int a = 0; a < nodeList.getLength(); a++) {
            if (nodeList.item(a).hasAttributes()) {

                String id = index + String.valueOf(counter);
                String name = nodeList.item(a).getAttributes().getNamedItem("name").getNodeValue();

                I3dMap i3dMap = new I3dMap().setNode(name).setId(id);
                repo.add(i3dMap);

                if (nodeList.item(a).hasChildNodes()) {
                    mapChildren(nodeList.item(a).getChildNodes(), index + counter + "|");
                }

                counter++;
            }

        }

    }

    /**
     * Generate a logged representation of the mapped content
     */
    public void printList() {
        repo.printList();

    }


}
