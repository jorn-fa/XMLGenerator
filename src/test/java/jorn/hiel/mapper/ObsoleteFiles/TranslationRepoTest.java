package jorn.hiel.mapper.ObsoleteFiles;

import jorn.hiel.mapper.pojo.TranslationItem;
import jorn.hiel.mapper.service.repo.TranslationsRepo;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;


public class TranslationRepoTest {

    private TranslationsRepo repo;

    @BeforeEach
    void setup(){
        repo = new TranslationsRepo();
        repo.clearRepo();

    }

    @Test
    void  doesExist(){
        assertNotEquals(null,repo);
    }

    @Test
    void shouldBeNull(){
        assertEquals(0,repo.getItems().size());
    }

    @Test
    void shouldBeMoreThen0(){
        assertEquals(0,repo.getItems().size());
        repo.add(new TranslationItem());
        assertEquals(1,repo.getItems().size());
    }

    @Test
    void canOnlyAddOneTime(){
        TranslationItem item= new TranslationItem();
        assertEquals(0,repo.getItems().size());
        repo.add(item);
        assertEquals(1,repo.getItems().size());
        repo.add(item);
        assertEquals(1,repo.getItems().size());

    }



}
