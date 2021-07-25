package jorn.hiel.mapper;

import lombok.SneakyThrows;
import org.json.simple.parser.ParseException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.context.support.AnnotationConfigContextLoader;

import java.io.FileNotFoundException;

import static org.junit.jupiter.api.Assertions.*;

@ContextConfiguration( loader = AnnotationConfigContextLoader.class)
@ExtendWith(SpringExtension.class)
@SpringBootTest
public class TranslationFileReaderTest {

    private String source = "e:/temp/translations.json";


    @Autowired
    private TranslationFileReader reader;


    @Test
    void doesExist(){
        assertNotEquals(null,reader);
    }

    @Test
    void hasRepo()
    {
        assertNotEquals(null,reader.getTranslations());
    }


    @Test
    void canSetFile(){
        //reset to 0 in case of random testing order
        reader.setFile("");
        assertEquals("",reader.getFile());
        reader.setFile(source);
        assertEquals(source,reader.getFile());
    }

    @SneakyThrows
    @Test
    void canSetFileWithUnknownName() {
        assertEquals("",reader.getFile());
        reader.setFile(source+"test");
        assertThrows(FileNotFoundException.class  , () -> reader.process());
    }

    @Test
    void canProcessFiles() throws FileNotFoundException, ParseException {
        reader.setFile(source);
        reader.process();
        assertNotEquals(0,reader.getTranslations().size());
    }





}
