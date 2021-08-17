package jorn.hiel.mapper.service.helpers;

import jorn.hiel.mapper.service.repo.implementations.ConfigRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.stream.Stream;


@Service
public class UnknownStringCounter {



    @Autowired
    private ConfigRepo repo;


    /**
     *
     * @param path
     * @return The number of unknown entries found in the given file.
     * @throws IOException
     * <p>
     *
     *     The search phrase is taken from the configuration file.
     * </p>
     */
    public int countEntries(Path path) throws IOException {
        Stream<String> lines = Files.lines(path);
        return  (int) lines.filter(a -> a.contains(repo.getItems().get("UnknownEntry"))).count();
    }

}
