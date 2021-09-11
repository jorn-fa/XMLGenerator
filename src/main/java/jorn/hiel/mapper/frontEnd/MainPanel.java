package jorn.hiel.mapper.frontEnd;

import lombok.Getter;
import org.springframework.stereotype.Component;

import javax.swing.*;
import java.awt.*;
import java.io.Serial;

@Component
@Getter
public class MainPanel extends JPanel implements ReadOptimal{


    @Serial
    private static final long serialVersionUID = 1L;
    private final JTextField directoryField,fileNameField;
    private final JTextPane resultPane;
    private final JButton processButton;
    private final JButton directorySelector,fileSelector;
    private final JLabel copyRightLabel;


    public MainPanel() {
        int margin=5;
        setLayout(null);

        JLabel directoryLabel = new JLabel("To directory :");
        JLabel fileLabel = new JLabel("File :");

        int fileHeight=38;
        int dirHeight=68;

        directoryLabel.setBounds(53, dirHeight+3, getOptimalWidth(directoryLabel), 14);
        fileLabel.setBounds(53, fileHeight+3, getOptimalWidth(directoryLabel), 14);
        add(fileLabel);
        add(directoryLabel);

        directorySelector = new JButton("...");
        directorySelector.setToolTipText("select a directory");
        directorySelector.setBounds(367, dirHeight-1, 48, 23);
        add(directorySelector);

        fileSelector = new JButton("...");
        fileSelector.setToolTipText("select a file");
        fileSelector.setBounds(367, fileHeight-1, 48, 23);
        add(fileSelector);

        String noneSelected="- None selected -";

        directoryField = new JTextField();
        directoryField.setText(noneSelected);
        directoryField.setToolTipText("Please select a directory");
        directoryField.setBounds(153, dirHeight, 175, 20);
        directoryField.setMargin(new Insets(0, margin, 0, margin));
        add(directoryField);
        directoryField.setColumns(10);

        fileNameField = new JTextField();
        fileNameField.setText(noneSelected);
        fileNameField.setToolTipText("Please select a file");
        fileNameField.setBounds(153, fileHeight, 175, 20);
        fileNameField.setMargin(new Insets(0, margin, 0, margin));
        add(fileNameField);
        fileNameField.setColumns(10);

        resultPane = new JTextPane();
        JScrollPane jScrollPane = new JScrollPane(resultPane);
        jScrollPane.setBounds(53, 207, 362, 105);
        resultPane.setMargin(new Insets(margin, margin, margin, margin));
        add(jScrollPane);

        JLabel resultLabel = new JLabel("Results: ");
        resultLabel.setBounds(53, 152, getOptimalWidth(resultLabel), 14);
        add(resultLabel);

        processButton = new JButton("Process");
        processButton.setToolTipText("Start working");
        processButton.setBounds(188, 117, 89, 23);
        add(processButton);

        copyRightLabel = new JLabel("© Hiel Jorn");
        copyRightLabel.setBounds(406, 334, getOptimalWidth(copyRightLabel), 14);
        add(copyRightLabel);


    }


}
