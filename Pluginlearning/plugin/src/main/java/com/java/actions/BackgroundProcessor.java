package com.java.actions;

import com.intellij.openapi.progress.ProgressIndicator;
import com.intellij.openapi.progress.Task;
import com.intellij.openapi.project.Project;
import com.intellij.openapi.ui.Messages;

public class BackgroundProcessor extends Task.Backgroundable {
    private final String input;

    public BackgroundProcessor(Project project, String input) {
        super(project, "Processing Input");
        this.input = input;
    }

    @Override
    public void run(ProgressIndicator indicator) {
        // Simulate long-running task
        try {
            Thread.sleep(2000); // Simulate a task taking 2 seconds
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        // Process the input (this is where your logic goes)
        String result = "Processed: " + input;

        // Show the result
        Messages.showMessageDialog(getProject(), result, "Result", Messages.getInformationIcon());
    }
}

