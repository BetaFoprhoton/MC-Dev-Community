package org.mc_dev_community.mcdevcommunity.toolwindow

import com.intellij.openapi.project.Project
import com.intellij.openapi.wm.ToolWindow
import com.intellij.openapi.wm.ToolWindowFactory
import org.mc_dev_community.mcdevcommunity.locale.MDCLocaleUtil
import org.mc_dev_community.mcdevcommunity.panes.ChatMainPanel
import org.mc_dev_community.mcdevcommunity.panes.ConfigurationMainPanel
import org.mc_dev_community.mcdevcommunity.util.*


class MainToolWindow: ToolWindowFactory {
    var myProject: Project? = null

    override fun createToolWindowContent(project: Project, toolWindow: ToolWindow) {
        myProject = project
        val contentFactory = toolWindow.contentManager.factory
        val chatMainPanel = ChatMainPanel(project)

        val chatContent = contentFactory.createContent(chatMainPanel.init(), MDCLocaleUtil.message("MC-Dev-Community.Chat"), false)

        val configMainPanel = ConfigurationMainPanel(project)
        val configContent = contentFactory.createContent(configMainPanel.init(), MDCLocaleUtil.message("MC-Dev-Community.Configuration"), false)

        toolWindow.contentManager.addContent(chatContent)
        toolWindow.contentManager.addContent(configContent)
        project.putUserData(CHAT_ACTIVE_CONTENT, chatMainPanel)
        project.putUserData(CONFIG_ACTIVE_CONTENT, configMainPanel)
    }
}
