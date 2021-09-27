package jorn.hiel.mapper.frontEnd.controllers;

import jorn.hiel.mapper.frontEnd.MainPanel;
import jorn.hiel.mapper.frontEnd.MainPanelUpdate;
import jorn.hiel.mapper.service.managers.MapperManager;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import javax.swing.*;
import javax.swing.filechooser.FileNameExtensionFilter;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.io.File;
import java.io.Serial;


@Service
@Slf4j
public class MainPanelController implements PropertyChangeListener {

    private final MainPanel panel;


    private final MapperManager manager;



    public MainPanelController(MainPanel panel, MapperManager manager) {

        this.panel = panel;
        this.manager = manager;
        init();
    }

    private void init() {

        clearFields();
        panel.getProcessButton().addActionListener(a-> process());
        panel.getDirectorySelector().addActionListener(a -> chooseDirectory());
        panel.getFileSelector().addActionListener(a -> chooseFile());
        panel.getFileNameField().setEditable(false);
        panel.getDirectoryField().setEditable(false);


    }


    @Override
    public void propertyChange(PropertyChangeEvent evt) {

        System.out.println("333333333333333333333");
        System.out.println(evt);
        System.out.println("333333333333333333333");

        MainPanelUpdate receiving = MainPanelUpdate.valueOf(evt.getPropertyName());
        switch (receiving) {
            //case DIRECTORY -> setDirectory(manager.getDirectory());
            case CLEAR -> clearFields();
            case RESULT -> getFinalResult();
            default -> throw new IllegalArgumentException("Unexpected value: " + receiving);

        }

    }

    public void clearFields() {
        String starter = "No results known yet.";
        panel.getDirectoryField().setText("- None Selected -");
        panel.getResultPane().setText(starter);

    }




    void process(){
        manager.runMe();
        getFinalResult();

    }

    /**
     *
     * @param result
     *
     * appends result to the output of the resultpane's text
     */
    private void addResult(String result){
        log.info(result);
        panel.getResultPane().setText(panel.getResultPane().getText()+"\n"+result);
    }


    private void chooseDirectory(){
        chooseFileOrDirectory(JFileChooser.DIRECTORIES_ONLY,"Select folder");
        if (manager.getDirectory()!=null) {
            addResult("Directory set = " + manager.getDirectory());
            panel.getDirectoryField().setText(manager.getDirectory().toString());
        }
    }

    private void chooseFile(){
        try {
            chooseFileOrDirectory(JFileChooser.FILES_ONLY, "Select file");
        } catch (UnsupportedOperationException e) {
            addResult("wrong file format specified");
        }

            System.out.println(manager.getFileName());

            if (manager.getFileName() != null)  {
                addResult("source set = " + manager.getFileName().getName());
                panel.getFileNameField().setText(manager.getFileName().toString());
            }

    }

    /**
     * logs+adds results to result list
     */

    private void getFinalResult(){

        for(String result: manager.getResults()){
            addResult(result);
            log.info(result);
        }


    }

    /**
     *
     * @param option sets chooser to directory or file
     * @param title window title
     * @throws UnsupportedOperationException when filetype is not valid
     */
    private void chooseFileOrDirectory(int option, String title) {
        JFileChooser fileChooser = new JFileChooser() {

            @Serial
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
        if (option==JFileChooser.FILES_ONLY) {
            FileNameExtensionFilter i3dFilter = new FileNameExtensionFilter("i3d files", "i3d");
            fileChooser.addChoosableFileFilter(i3dFilter);
            fileChooser.setFileFilter(i3dFilter);
        }

        fileChooser.setDialogTitle(title);
        int verify = fileChooser.showOpenDialog(panel);
            if (verify == JFileChooser.APPROVE_OPTION) {
                if(option==JFileChooser.DIRECTORIES_ONLY){
                manager.setDirectory(fileChooser.getSelectedFile());}
                else{
                    if(!fileChooser.getSelectedFile().getName().endsWith("i3d"))throw new UnsupportedOperationException();

                    manager.setFileName(fileChooser.getSelectedFile());
                }
            } else {
                log.info("cancel button was pressed");
        }
    }
}
