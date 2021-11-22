package jorn.hiel.mapper;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class KeyGenerator {


    public static void main(String[] args) throws IOException {

        List<String> results  = new ArrayList<>();


        String where = System.getProperty("user.dir").replace('\\','/') +  "/src/main/java/jorn/hiel/mapper/service/writers";

        //System.out.println(where);

        List<Path> files = Files.walk(Path.of(where))
                .filter(Files::isRegularFile)
                .collect(Collectors.toList());

        for (Path file : files) {


            Files.readAllLines(file).stream()
                    .filter(a -> a.contains(".getMappedItem("))
                    .filter(a -> !a.contains(".forEach"))
                    .filter(a -> !a.contains("Integer."))
                    .filter(a -> !a.contains("getValue().equals"))

                    .forEach(a -> {
                        if (a.contains("getValue")) {
                            String cut = "Item(";
                            int start = a.indexOf(cut) + cut.length() + 1;
                            a = a.substring(start).trim();
                            int end = a.indexOf(".getV");
                            a = a.substring(0, end - 2);
                            results.add(a);
                        }
                    });
        }


        System.out.println("results: " + results.size());
        results.stream().distinct().forEach(System.out::println);


    }

}
