package com.java.actions;

import com.intellij.openapi.ui.DialogWrapper;
import com.intellij.openapi.ui.Messages;
import com.intellij.openapi.progress.ProgressIndicator;
import com.intellij.openapi.progress.Task;
import com.intellij.openapi.project.Project;
import com.intellij.ui.components.JBScrollPane;
import org.jetbrains.annotations.Nullable;

import javax.swing.*;
import java.awt.*;
import java.io.File;

import static com.intellij.ide.SelectInManager.getProject;

public class FileUploadDialog extends DialogWrapper {
    private JTextField filePathField;
    private JButton browseButton;
    private JButton uploadButton;
    private JButton closeButton;

    Project project;
    JTextArea logArea= new JTextArea(10, 40);

    protected FileUploadDialog(@Nullable Project project) {
        super(project);
        this.project=project;
        setTitle("JIRA Integration");
        init();
    }

    @Nullable
    @Override
    protected JComponent createCenterPanel() {

        JPanel panel    = new JPanel(new BorderLayout());
        filePathField   = new JTextField();
        browseButton    = new JButton("Browse");
        uploadButton    = new JButton("Create Stories");
        closeButton    = new JButton("Close");

        JBScrollPane scrollPane = new JBScrollPane(logArea);
        browseButton.addActionListener(e -> {
            JFileChooser fileChooser = new JFileChooser();
            int result = fileChooser.showOpenDialog(panel);
            if (result == JFileChooser.APPROVE_OPTION) {
                File selectedFile = fileChooser.getSelectedFile();
                filePathField.setText(selectedFile.getAbsolutePath());
            }
        });

        uploadButton.addActionListener(e -> {
            String filePath = filePathField.getText();
            if (filePath.isEmpty()) {
                Messages.showErrorDialog("Please select a file to upload.", "Error");
                return;
            }
            logArea.append("Uploading file: " + filePath + "\n");
            new BackgroundFileProcessor(project, filePath, this).queue();
        });

        closeButton.addActionListener(e -> {
            super.doOKAction();
        });

        JPanel filePanel = new JPanel(new BorderLayout());
        filePanel.add(filePathField, BorderLayout.CENTER);

        filePanel.add(browseButton, BorderLayout.EAST);
        panel.add(filePanel, BorderLayout.NORTH);

        panel.add(uploadButton, BorderLayout.SOUTH);
        panel.add(scrollPane,BorderLayout.CENTER);


//
//       panel.add(closeButton, BorderLayout.SOUTH);
//       panel.add(scrollPane,BorderLayout.CENTER);

        return panel;
    }

//    @Override
//    protected void doOKAction() {
//        // Prevent closing the dialog with the OK button
//    }

//    public void closeDialog() {
//        super.doOKAction();
//    }
}

