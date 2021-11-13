package jorn.hiel.mapper.pojo;

import jorn.hiel.mapper.service.enums.VehicleSpec;
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
    private List<VehicleSpec> specs;



    public void addSpecialization(VehicleSpec spec){
        if(specs==null){specs=new ArrayList<>();
        }
        specs.add(spec);

    }
}
