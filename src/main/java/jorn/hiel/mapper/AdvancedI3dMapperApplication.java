package jorn.hiel.mapper;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ApplicationContext;

@SpringBootApplication
public class AdvancedI3dMapperApplication {





	public static void main(String[] args) {


		//SpringApplication.run(AdvancedI3dMapperApplication.class, args);


		ApplicationContext app = SpringApplication.run(AdvancedI3dMapperApplication.class, args);//init

		Tester manager = app.getBean(Tester.class);//get the bean by type

		manager.runMe();





	}

}
