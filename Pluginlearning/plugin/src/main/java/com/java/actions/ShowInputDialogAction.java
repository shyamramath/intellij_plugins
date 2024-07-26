package com.java.actions;

import com.intellij.openapi.actionSystem.AnAction;
import com.intellij.openapi.actionSystem.AnActionEvent;
import com.intellij.openapi.ui.Messages;

public class ShowInputDialogAction extends AnAction {
    @Override
    public void actionPerformed(AnActionEvent e) {
        // Show input dialog
        String input = Messages.showInputDialog(
                "Enter a value:",
                "Input Dialog",
                Messages.getQuestionIcon()
        );

        if (input != null && !input.isEmpty()) {
            // Process the input in the background
            new BackgroundProcessor(e.getProject(), input).queue();
        }
    }
}
