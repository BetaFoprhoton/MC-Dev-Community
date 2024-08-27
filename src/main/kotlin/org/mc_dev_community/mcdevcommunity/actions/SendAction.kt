package org.mc_dev_community.mcdevcommunity.actions


import com.intellij.openapi.actionSystem.AnAction
import com.intellij.openapi.actionSystem.AnActionEvent
import com.intellij.openapi.components.Service
import com.intellij.openapi.project.Project
import org.apache.commons.lang3.StringUtils
import org.mc_dev_community.mcdevcommunity.multidevice.MultiDeviceClient
import org.mc_dev_community.mcdevcommunity.panes.ChatMainPanel
import org.mc_dev_community.mcdevcommunity.servermessage.MessageComponent
import org.mc_dev_community.mcdevcommunity.util.CHAT_ACTIVE_CONTENT
import org.slf4j.Logger
import org.slf4j.LoggerFactory
import java.net.InetAddress

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

        if (data[0] == '$') {
            data.substring(1)
                .split(" ")
                .let {
                    when (it[0]) {
                        "clear" -> {
                            for (component in contentPanel.components) {
                                if (component is MessageComponent) {
                                    contentPanel.remove(component)
                                }
                            }
                            contentPanel.revalidate()
                            contentPanel.repaint()
                        }
                        "debug" -> {
                            when (it[1]) {
                                "client" -> {
                                    when (it[2]) {
                                        "start" -> {
                                            it[3].split(':').let { ipAndPort ->
                                                MultiDeviceClient.runClient(ipAndPort[0], ipAndPort[1].toInt())
                                                contentPanel.add(MessageComponent("Client started on ${ipAndPort[0]}${ipAndPort[1]}", false))
                                            }
                                        }
                                        "stop" -> {
                                            MultiDeviceClient.stopClient()
                                        }
                                        "print_message" ->
                                            MultiDeviceClient.messageQueue.forEach { message ->
                                                contentPanel.add(MessageComponent("Message:$message", false))
                                            }
                                    }
                                }
                            }
                        }
                        "help" -> {
                            contentPanel.add(MessageComponent(
                                "\$clear \t clear the chat panel\n" +
                                    "\$debug client start <ip>:<port> \t start the client\n" +
                                    "\$debug client stop \t to stop the client\n" +
                                    "\$debug client print_message \t print the message queue", false
                                )
                            )
                        }
                    }
                }
        }
        else {
            val message = MessageComponent(data, true)
            contentPanel.add(message)
        }

    }

}
