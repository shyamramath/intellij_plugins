package com.java.actions;

import com.intellij.openapi.progress.ProgressIndicator;
import com.intellij.openapi.progress.Task;
import com.intellij.openapi.project.Project;
import com.intellij.openapi.ui.Messages;
import com.java.escuela.JiraAPIUtilities;
import org.jetbrains.annotations.Nls;

import java.io.File;
import java.io.IOException;

import static com.intellij.ide.SelectInManager.getProject;

public class BackgroundFileProcessor extends Task.Backgroundable {
    private final String filePath;
    private final FileUploadDialog dialog;

    public BackgroundFileProcessor(Project project, String filePath, FileUploadDialog dialog) {
        super(project, "Processing File");
        this.filePath = filePath;
        this.dialog = dialog;
    }

    @Override
    public void run(ProgressIndicator indicator) {
        indicator.setText("Reading the file ...");
        File file = new File(filePath);
        boolean success = file.exists();

        // Dummy check, replace with actual processing logic
        // Show the result
        // Simulate a long-running task (e.g., file upload)
                try {
                    Thread.sleep(3000); // Simulate a task taking 5 seconds
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                //Process the file (this is where your logic goes)

        if (success) {
            try {
                indicator.setText("Creating JIRA stories...");
                JiraAPIUtilities.createStories(filePath);
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
            indicator.setText("Successfully Created all JIRA stories on the board ...");
            try {
                Thread.sleep(3000); // Simulate a task taking 5 seconds
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            Messages.showInfoMessage("File uploaded successfully!", "Success");
        } else {
            Messages.showErrorDialog("Failed to upload file.", "Error");
        }

        // Close the dialog
        dialog.closeDialog();
    }

    void backGroundProcessor(FileUploadDialog dialogue){
        new BackgroundFileProcessor(getProject(), filePath, dialogue).queue();
    }
}
