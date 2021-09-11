package jorn.hiel.mapper.frontEnd.controllers;

import jorn.hiel.mapper.frontEnd.MainPanel;
import jorn.hiel.mapper.frontEnd.MainPanelUpdate;
import jorn.hiel.mapper.service.managers.MapperManager;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import javax.swing.*;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.io.File;


@Service
@Slf4j
public class MainPanelController implements PropertyChangeListener {
    private final MainPanel panel;
    private final MapperManager manager;

    private final String starter = "No results known yet.";

    public MainPanelController(MainPanel panel, MapperManager manager) {

        this.panel = panel;
        this.manager = manager;
        manager.getPcs().addPropertyChangeListener(this);
        init();
    }

    private void init() {
        log.info("creating ******************");
        clearFields();
        //panel.getProcessButton().addActionListener(a -> manager.startWorking(new MainPanelDto().setDirectory(panel.getDirectoryField().getText().trim())));
        panel.getDirectorySelector().addActionListener(a -> chooseDirectory());

    }


    @Override
    public void propertyChange(PropertyChangeEvent evt) {

        MainPanelUpdate recieving = MainPanelUpdate.valueOf(evt.getPropertyName());
        switch (recieving) {
            //case DIRECTORY -> setDirectory(manager.getDirectory());
            case CLEAR -> clearFields();
            //case RESULT -> panel.getResultPane().setText(manager.getResults());
            default -> throw new IllegalArgumentException("Unexpected value: " + recieving);

        }

    }

    public void clearFields() {
        panel.getDirectoryField().setText("- None Selected -");
        panel.getResultPane().setText(starter);

    }


    private void chooseDirectory(){
        chooseFileOrDirectory(JFileChooser.DIRECTORIES_ONLY,"Select folder");
        panel.getResultPane().setText("Directory set = " + manager.getDirectory());
        panel.getDirectoryField().setText(manager.getDirectory().toString());
    }

    private void chooseFile(){
        chooseFileOrDirectory(JFileChooser.DIRECTORIES_ONLY,"Select folder");
        panel.getResultPane().setText("source set = " + manager.getFileName().getName());
        panel.getFileNameField().setText(manager.getDirectory().toString());
    }

    private void chooseFileOrDirectory(int option, String title) {
        JFileChooser fileChooser = new JFileChooser() {

            private static final long serialVersionUID = 1L;

            @Override
            public void approveSelection() {
                File f = getSelectedFile();
                if (!f.exists()) {
                    JOptionPane.showMessageDialog(null, "Folder does not exist");
                } else {
                    super.approveSelection();
                }
            }

        };

        fileChooser.setFileSelectionMode(option);
        fileChooser.setDialogTitle(title);

        int verify = fileChooser.showOpenDialog(panel);



            if (verify == JFileChooser.APPROVE_OPTION) {
                if(option==JFileChooser.DIRECTORIES_ONLY){
                manager.setDirectory(fileChooser.getSelectedFile());}
                else{
                    manager.setFileName(fileChooser.getSelectedFile());
                }

            } else {
                log.info("cancel button was pressed");



        }
    }
}