package jorn.hiel.mapper.service.repo;

import jorn.hiel.mapper.pojo.MappedItem;

import java.util.HashMap;
import java.util.Map;

public abstract class MapRepository {

    private Map<String, String> mappedItemList = new HashMap();

    public MapRepository(){clearRepo();}


    public void clearRepo() {
        mappedItemList = new HashMap();

    }

    public void add(MappedItem mappedItem) {
        mappedItemList.put(mappedItem.getKey(),mappedItem.getValue());
    }

    public Map<String, String> getItems() {
        return mappedItemList;
    }
}
