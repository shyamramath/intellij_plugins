package com.escuela.plugin

import com.intellij.openapi.ui.DialogWrapper
import com.intellij.uiDesigner.core.AbstractLayout
import com.intellij.util.ui.GridBag
import com.intellij.util.ui.JBUI
import com.intellij.util.ui.UIUtil
import com.java.escuela.SampleDialogWrapper
import java.awt.Font
import java.awt.GridBagConstraints
import java.awt.GridBagLayout
import java.awt.Insets
import javax.swing.*

class MyDialougeWrapper : DialogWrapper(true) {

    val Panel = JPanel(GridBagLayout());
    val txtmode = JTextField();
    val txtUsername = JTextField();
    val txtpassword = JPasswordField();
    val testButton = JButton("helloo meee")

    init {
        init()
        title = "JIRA Automation";
    }

    override fun doOKAction() {
        super.doOKAction()
    }

    override fun createCenterPanel(): JComponent? {
        val gb = GridBag()
                .setDefaultInsets(Insets(0, 0, AbstractLayout.DEFAULT_VGAP, AbstractLayout.DEFAULT_HGAP))
                .setDefaultFill(GridBagConstraints.HORIZONTAL)
                .setDefaultWeightX(1.0);

        Panel.add(label("mode"), gb.nextLine().next().weightx(0.2));
        Panel.add(txtmode, gb.nextLine().next().weightx(0.8));
        Panel.add(label("User Name"), gb.nextLine().next().weightx(0.2));
        Panel.add(txtUsername, gb.nextLine().next().weightx(0.8));
        Panel.add(label("Password "), gb.nextLine().next().weightx(0.2));
        Panel.add(txtpassword, gb.nextLine().next().weightx(0.8));
        Panel.add(testButton);
        return Panel;
    }

    private fun label(text: String): JComponent {
        val label = JLabel(text)
//        label.componen = UIUtil.ComponentStyle.REGULAR
////        label.font = label.font.deriveFont(Font.BOLD)
        return label;
    }
}