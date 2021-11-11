package jorn.hiel.mapper.service.repo;

import jorn.hiel.mapper.pojo.Specialization;
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
public class VehicleSpecRepo implements RepositoryInterface<Specialization> {

    private List<Specialization> specializationList = new ArrayList<>();



    @Override
    public void clearRepo() {
        specializationList = new ArrayList<>();
    }

    @Override
    public boolean add(Specialization specialization) {
        if (specializationList==null){clearRepo();}
        if (!specializationList.contains(specialization)){
            log.info("adding " + specialization.toString() + " to repo");

            return specializationList.add((specialization));
        }
        return false;

    }

    @Override
    public List<Specialization> getItems() {
        return Collections.unmodifiableList(specializationList);
    }

    @Override
    public void printList() {
        specializationList.forEach(System.out::println);
    }



}
