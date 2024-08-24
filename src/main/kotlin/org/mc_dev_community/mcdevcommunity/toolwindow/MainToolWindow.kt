package org.mc_dev_community.mcdevcommunity.toolwindow

import com.intellij.openapi.project.Project
import com.intellij.openapi.util.Key
import com.intellij.openapi.wm.ToolWindow
import com.intellij.openapi.wm.ToolWindowFactory
import com.intellij.ui.OnePixelSplitter
import com.intellij.ui.content.ContentFactory
import org.mc_dev_community.mcdevcommunity.locale.LocaleUtil
import org.mc_dev_community.mcdevcommunity.panes.ChatMainPanel
import org.mc_dev_community.mcdevcommunity.util.ACTIVE_CONTENT
import java.awt.BorderLayout
import javax.swing.JButton
import javax.swing.JEditorPane
import javax.swing.JLabel
import javax.swing.JPanel


class MainToolWindow: ToolWindowFactory {
    var myProject: Project? = null

    override fun createToolWindowContent(project: Project, toolWindow: ToolWindow) {
        myProject = project
        val chatMainPanel = ChatMainPanel(project)
        val contentFactory = toolWindow.contentManager.factory

        val content = contentFactory.createContent(chatMainPanel.init(), LocaleUtil.message("MC-Dev-Community.Chat"), false)

        toolWindow.contentManager.addContent(content)
        project.putUserData(ACTIVE_CONTENT, chatMainPanel)
    }
}
