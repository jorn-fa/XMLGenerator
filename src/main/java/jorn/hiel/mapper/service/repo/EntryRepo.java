package jorn.hiel.mapper.service.repo;

import jorn.hiel.mapper.pojo.MappedItem;
import jorn.hiel.mapper.service.interfaces.RepositoryInterface;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;

@Slf4j
@Repository
public class EntryRepo implements RepositoryInterface<MappedItem> {


    private List<MappedItem> mappedItemList = new ArrayList<>();
    public EntryRepo(){clearRepo();}

    @Override
    public void clearRepo() {
        mappedItemList = new ArrayList<>();

    }

    @Override
    public boolean add(MappedItem mappedItem) {
        return mappedItemList.add(mappedItem);
    }

    @Override
    public List<MappedItem> getItems() {
        return mappedItemList;
    }

    @Override
    public void printList() {
        mappedItemList.forEach(a-> System.out.println(a));
    }
}
