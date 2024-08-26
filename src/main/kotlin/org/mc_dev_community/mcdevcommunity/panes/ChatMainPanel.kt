package org.mc_dev_community.mcdevcommunity.panes

import com.intellij.find.SearchTextArea
import com.intellij.ide.ui.laf.darcula.ui.DarculaButtonUI
import com.intellij.openapi.project.Project
import com.intellij.openapi.util.IconLoader.getIcon
import com.intellij.ui.OnePixelSplitter
import com.intellij.ui.components.JBTextArea
import org.mc_dev_community.mcdevcommunity.listener.SendListener
import org.mc_dev_community.mcdevcommunity.locale.MDCLocaleUtil
import org.mc_dev_community.mcdevcommunity.servermessage.MessageComponent
import org.mc_dev_community.mcdevcommunity.servermessage.MessageGroupComponent
import java.awt.BorderLayout
import java.awt.Dimension
import javax.swing.JButton
import javax.swing.JPanel


class ChatMainPanel(val myProject: Project) {
    val searchTextArea = SearchTextArea(JBTextArea(), true)
    val button = JButton(MDCLocaleUtil.message("MC-Dev-Community.Chat.Send"), getIcon("/icons/send.svg", ChatMainPanel::class.java))
    val contentPanel = MessageGroupComponent()
    val splitter = OnePixelSplitter(true, .98f)
    val actionPanel = JPanel(BorderLayout())

    init {
        val listener = SendListener(this)
        splitter.dividerWidth = 2

        searchTextArea.textArea.addKeyListener(listener)
        searchTextArea.minimumSize = Dimension(searchTextArea.width, 500)
        searchTextArea.setMultilineEnabled(false)

        button.addActionListener(listener)
        button.setUI(DarculaButtonUI())

        actionPanel.add(searchTextArea, BorderLayout.CENTER)
        actionPanel.add(button, BorderLayout.EAST)

        contentPanel.add(MessageComponent("Hello World!", false))
        splitter.firstComponent = contentPanel
        splitter.secondComponent = actionPanel
    }

    fun init(): JPanel {
        return splitter
    }

    fun aroundRequest() {
        actionPanel.updateUI()
    }

}