package jorn.hiel.mapper.service.repo;

import jorn.hiel.mapper.pojo.TranslationItem;
import jorn.hiel.mapper.service.interfaces.RepositoryInterface;
import lombok.NoArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

@Slf4j
@NoArgsConstructor
@Repository
public class TranslationsRepo implements RepositoryInterface<TranslationItem> {

    private List<TranslationItem> itemList = new ArrayList<>();


    @Override
    public void clearRepo() {
        itemList = new ArrayList<>();
    }

    @Override
    public boolean add(TranslationItem translationItem) {
        if (itemList==null){clearRepo();}

        if (!itemList.contains(translationItem)){
            log.info("adding " + translationItem.toString() + " to repo");
            return itemList.add((translationItem));
        }
        return false;
    }

    @Override
    public List<TranslationItem> getItems() {
        return Collections.unmodifiableList(itemList);
    }

    @Override
    public void printList() {
        itemList.forEach(a -> System.out.println(a));
    }
}
