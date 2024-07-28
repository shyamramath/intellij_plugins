package com.escuela.plugin

import com.intellij.openapi.actionSystem.AnAction
import com.intellij.openapi.actionSystem.AnActionEvent
import com.intellij.openapi.fileChooser.FileChooser
import com.intellij.openapi.fileChooser.FileChooserDescriptor
import com.intellij.openapi.ui.Messages
import com.intellij.util.Consumer
import com.java.utils.JiraAPIUtilities


/**
 *
 */
class HelloAction : AnAction() {

    override fun actionPerformed(e: AnActionEvent) {
//        Messages.showMessageDialog(e.project, "Hello ", "MyIdeaDemo", Messages.getInformationIcon());
//        val name= Messages.showInputDialog(e.project, "Enter your Name", "MyIdeaDemo", Messages.getQuestionIcon());
//        Messages.showMessageDialog(e.project, "Your  Name is  $name", "MyIdeaDemo", Messages.getInformationIcon());
        Messages.showMessageDialog(e.project, showFileDialog(e), "JIRA Integration", Messages.getInformationIcon());

//        if(MyDialougeWrapper().showAndGet()){
//            println(" Ok Clicked ");
//            showFileDialog(e);
//        }
    }

   // private fun showFileDialog(e: AnActionEvent) {
//   override fun actionPerformed(e: AnActionEvent) {
//
////        val message = WebserviceCall.sayHello();
////        print(message);
//        val fileChooserDescriptor = FileChooserDescriptor(
//            true,
//            true,
//            false,
//            false,
//            false,
//            false
//        );
//
//        fileChooserDescriptor.title = "File Chooser";
//        fileChooserDescriptor.description = "My File Chooser Demo";
//
//        FileChooser.chooseFile(fileChooserDescriptor,e.project,null, Consumer {
//            //val path=Messages.getInformationIcon();
//            val message = WebserviceCall.sayHello(it.path);
//            //val finalMessage  = "$path-$message"
//            Messages.showMessageDialog(e.project,it.path +" - "+message,"Path",Messages.getInformationIcon())}
//        );
//
//    }

    private fun showFileDialog(e: AnActionEvent) :String  {

        lateinit var returnMessage: String
        val fileChooserDescriptor = FileChooserDescriptor(
            true,
            true,
            false,
            false,
            false,
            false
        );
        fileChooserDescriptor.title = "File Chooser";
        fileChooserDescriptor.description = "JIRA File chooser";
            FileChooser.chooseFile(fileChooserDescriptor,e.project,null, Consumer {
                val message = JiraAPIUtilities.createStories(it.path,true,null);
                returnMessage = message;
               // Messages.showMessageDialog(e.project,it.path +" - "+message,"Path",Messages.getInformationIcon());
            }
        );
        return returnMessage;
    }

    private fun createMessage(){
    }

}