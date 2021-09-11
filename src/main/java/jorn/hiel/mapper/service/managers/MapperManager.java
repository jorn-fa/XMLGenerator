package jorn.hiel.mapper.service.managers;

import lombok.Getter;
import lombok.Setter;
import org.springframework.stereotype.Service;

import java.io.File;

@Service
@Getter @Setter
public class MapperManager extends BasicManager{



    private File directory;
    private File fileName;







}
