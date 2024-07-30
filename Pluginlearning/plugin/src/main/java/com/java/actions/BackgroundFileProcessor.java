package com.java.actions;

import com.intellij.openapi.application.ApplicationManager;
import com.intellij.openapi.diagnostic.Logger;
import com.intellij.openapi.progress.ProgressIndicator;
import com.intellij.openapi.progress.Task;
import com.intellij.openapi.project.Project;
import com.intellij.openapi.ui.Messages;
import com.java.constants.AppConstants;
import com.java.utils.EnvUtils;
import com.java.utils.FileUtils;
import com.java.utils.JiraAPIUtilities;

import java.awt.*;
import java.io.File;
import java.io.IOException;

public class BackgroundFileProcessor extends Task.Backgroundable {
    private final String filePath;
    private final FileUploadDialog dialog;
    static Logger logger= Logger.getInstance(BackgroundFileProcessor.class);

    public BackgroundFileProcessor(Project project, String filePath, FileUploadDialog dialog) {
        super(project, "Processing File");
        this.filePath = filePath;
        this.dialog = dialog;
    }

    @Override
    public void run(ProgressIndicator indicator) {
        logger.info (AppConstants.PLUGIN_NAME +" Plugin initialized ");
        System.out.println(System.getProperty("JIRA_API_TOKEN"));
        indicator.setText("Reading the file ...");
        logger.info(" Checking the env values -- "+System.getenv());
        File file = new File(filePath);
        boolean success = file.exists();
        boolean envflag = EnvUtils.validateAllEnvVariables(indicator);
        try {
           Thread.sleep(3000); // Simulate a task taking 5 seconds
        } catch (InterruptedException e) {
            logger.error(e);
        }
        //Process the file (this is where your logic goes)
        if (success && envflag) {
            try {
                indicator.setText("Creating JIRA stories...");
                String response = JiraAPIUtilities.createStories(filePath,Boolean.getBoolean(System.getenv("IS_SECURED_CHATGPT_MODE")),indicator);
                indicator.setText(response);
                this.dialog.logArea.setBackground(Color.DARK_GRAY);
                this.dialog.logArea.append(response);
            } catch (IOException e) {
                indicator.setText("Error : Failed to create JIRA stories. Reason >> "+e.getMessage());
                logger.error(e);
                throw new RuntimeException(e);
            }
            Messages.showInfoMessage("File uploaded successfully!", "Success");
        } else {
            Messages.showErrorDialog("Failed to upload file.", "Error");
        }
        // After the background work is done, show a dialog
        ApplicationManager.getApplication().invokeLater(() -> {
            Messages.showMessageDialog(
                    myProject,"This is a message from a background task",
                    "Information",
                    Messages.getInformationIcon()
            );
        });
        // Close the dialog
        dialog.closeDialog();
    }

    void backGroundProcessor(FileUploadDialog dialogue){
        new BackgroundFileProcessor(getProject(), filePath, dialogue).queue();
    }
}
