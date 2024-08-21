package org.mc_dev_community.mcdevcommunity.toolwindow

import com.intellij.openapi.project.Project
import com.intellij.openapi.wm.ToolWindow
import com.intellij.openapi.wm.ToolWindowFactory
import com.intellij.ui.components.JBList
import com.intellij.ui.components.JBPanel
import com.intellij.ui.components.JBTextField
import it.unimi.dsi.fastutil.ints.J
import org.mc_dev_community.mcdevcommunity.locale.LocaleUtil
import org.mc_dev_community.mcdevcommunity.servermessage.ServerMessageList
import java.awt.BorderLayout
import javax.swing.JEditorPane
import javax.swing.JPanel

class MainToolWindow: ToolWindowFactory {

    override fun createToolWindowContent(project: Project, toolWindow: ToolWindow) {
        toolWindow.contentManager.addContent(
            toolWindow.contentManager.factory.createContent(
                JEditorPane(), LocaleUtil.message("MC-Dev-Community.Configuration"), false
            )
        )
        val messages = JPanel()
        messages.add(JBTextField()
            .also { it.text = "Hello" }
            .also { it.isEditable = false }
        )

        messages .add(JBTextField()
            .also { it.text = "World" }
            .also { it.isEditable = false }
        )

        toolWindow.contentManager.addContent(
            toolWindow.contentManager.factory.createContent(
                , LocaleUtil.message("MC-Dev-Community.Chat"), false
            )
        )

    }
}