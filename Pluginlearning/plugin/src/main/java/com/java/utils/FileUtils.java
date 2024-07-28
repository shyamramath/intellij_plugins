package com.java.utils;

import com.java.constants.AppConstants;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;

/**
 *
 */
public class FileUtils {

   final static String LOG_FILE_PATH = AppConstants.JIRA_AI_LOG_PATH+"/jira_automation.logs";
   static Path filePath = Paths.get(LOG_FILE_PATH );


    public static void log(String log) {
        try {
            String formatted = String.format("%s", log);
            Files.writeString(Path.of(AppConstants.JIRA_AI_LOG_PATH, "/jira_automation.logs"),
                    log+System.lineSeparator(),StandardOpenOption.CREATE, StandardOpenOption.APPEND);

        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
