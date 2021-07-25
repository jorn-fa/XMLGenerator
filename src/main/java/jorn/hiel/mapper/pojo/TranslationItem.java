package jorn.hiel.mapper.pojo;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.util.ArrayList;
import java.util.List;

@Getter @Setter
@ToString
public class TranslationItem {

    private String item;
    private List<String> translationItems;


    public void addTranslation(String item) {
        if(translationItems==null){translationItems=new ArrayList<>();
        }
        translationItems.add(item);
    }
}
