package jorn.hiel.mapper.pojo;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@ToString
public class Specialization {

    private String name;
    private String version;
    private List<String> specializations;

    public void addSpecialization(String item){
        if(specializations==null){specializations=new ArrayList<>();
        }
        specializations.add(item);
    }
}
