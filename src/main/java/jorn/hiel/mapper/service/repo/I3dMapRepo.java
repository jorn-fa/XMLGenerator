package jorn.hiel.mapper.service.repo;

import jorn.hiel.mapper.pojo.I3dMap;
import jorn.hiel.mapper.service.interfaces.RepositoryInterface;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;

@Slf4j
@Repository
public class I3dMapRepo implements RepositoryInterface<I3dMap> {

    private List<I3dMap> i3dMapList=new ArrayList<>();

    public I3dMapRepo() {
        clearRepo();
    }

    public void clearRepo(){
        i3dMapList = new ArrayList<>();
    }

    @Override
    public boolean add(I3dMap i3dMap) {
        return i3dMapList.add(verify(i3dMap));
    }

    @Override
    public List<I3dMap> getItems() {
        return i3dMapList;
    }

    public void printList() {
        i3dMapList.forEach(t -> log.info("name = " + t.getNode() + "   node= " + t.getId()));
    }

    /**
     * @param i3dMap
     * @return altered name due to existing equal
     */
    private I3dMap verify(I3dMap i3dMap) {
        I3dMap passMe = i3dMap;

        if (doesExist(i3dMap)) {
            if (i3dMap.getNode().contains(".")) {
                String node = i3dMap.getNode();
                int number = Integer.valueOf(node.substring(node.indexOf('.') + 1)) + 1;

                passMe.setNode(node.substring(0, node.indexOf('.') + 1) + String.valueOf(number));

            } else {

                passMe.setNode(passMe.getNode() + ".0");

            }
            return passMe;
        }

        return passMe;
    }

    private boolean doesExist(I3dMap i3dMap) {


        return  i3dMapList.stream().anyMatch(t -> t.getNode().equals(i3dMap.getNode()));

    }


}