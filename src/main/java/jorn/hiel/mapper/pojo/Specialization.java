package jorn.hiel.mapper.pojo;

import jorn.hiel.mapper.service.enums.GameVersion;
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
    private GameVersion gameVersion;
    private List<VehicleSpec> specs;
    private boolean isCustom;
    private String parent;



    public void addSpecialization(VehicleSpec spec){
        if(specs==null){specs=new ArrayList<>();
        }
        specs.add(spec);

    }
}
