package jorn.hiel.mapper;

import jorn.hiel.mapper.frontEnd.MainPanel;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.ComponentScan;

import javax.swing.*;

@SpringBootApplication
@ComponentScan
public class AdvancedI3dMapperApplication {





	public static void main(String[] args) {


		for(String arg : args){
			System.out.println(arg);
			//-fullWrite:True
		}


		//SpringApplication.run(AdvancedI3dMapperApplication.class, args);

		System.setProperty("java.awt.headless", "false"); //Disables headless



		ApplicationContext app = SpringApplication.run(AdvancedI3dMapperApplication.class, args);//init

		Tester manager = app.getBean(Tester.class);//get the bean by type

		//MapperManager mapperManager = app.getBean(MapperManager.class);//get the bean by type
		MainPanel panel = app.getBean(MainPanel.class);



		JFrame frame = new JFrame("Xml Skeletor");
		frame.setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
		frame.add(panel);
		frame.setSize(550,400);
		frame.setLocationRelativeTo(null);
		frame.setVisible(true);
		frame.setResizable(false);

		manager.setRuntimeArgs(args);

		//manager.runMe();





	}

}
