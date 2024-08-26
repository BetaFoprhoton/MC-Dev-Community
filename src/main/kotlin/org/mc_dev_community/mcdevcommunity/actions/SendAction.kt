package org.mc_dev_community.mcdevcommunity.actions


import com.intellij.openapi.actionSystem.AnAction
import com.intellij.openapi.actionSystem.AnActionEvent
import com.intellij.openapi.components.Service
import com.intellij.openapi.project.Project
import org.apache.commons.lang3.StringUtils
import org.mc_dev_community.mcdevcommunity.panes.ChatMainPanel
import org.mc_dev_community.mcdevcommunity.servermessage.MessageComponent
import org.mc_dev_community.mcdevcommunity.util.CHAT_ACTIVE_CONTENT
import org.slf4j.Logger
import org.slf4j.LoggerFactory

@Service(Service.Level.PROJECT)
class SendAction: AnAction() {
    private val data: String? = null

    companion object {
        private val LOG: Logger = LoggerFactory.getLogger(SendAction::class.java)
    }

    override fun actionPerformed(event: AnActionEvent) {
        val project = event.project
        val mainPanel = project?.getUserData(CHAT_ACTIVE_CONTENT)
        doActionPerformed(mainPanel as ChatMainPanel, data)
    }

    fun doActionPerformed(chatMainPanel: ChatMainPanel, data: String?) {
        // Filter the empty text
        if (StringUtils.isEmpty(data) || data == null) {
            return
        }

        // Reset the question container
        chatMainPanel.searchTextArea.textArea.text = ""
        chatMainPanel.aroundRequest()
        val project: Project = chatMainPanel.myProject
        val contentPanel = chatMainPanel.contentPanel

        // Add the message component to container
        val message = MessageComponent(data, true)
        contentPanel.add(message)
    }

}
