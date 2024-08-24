package org.mc_dev_community.mcdevcommunity.listener

import org.mc_dev_community.mcdevcommunity.actions.SendAction
import org.mc_dev_community.mcdevcommunity.panes.ChatMainPanel
import java.awt.event.ActionEvent
import java.awt.event.ActionListener
import java.awt.event.KeyEvent
import java.awt.event.KeyListener
import java.io.IOException


class SendListener(val chatMainPanel: ChatMainPanel): ActionListener, KeyListener {
    override fun actionPerformed(e: ActionEvent?) {
        try {
            doActionPerformed()
        } catch (ex: IOException) {
            throw RuntimeException(ex)
        }
    }

    @Throws(IOException::class)
    fun doActionPerformed() {
        val text: String = chatMainPanel.searchTextArea.textArea.getText()
        val sendAction: SendAction = chatMainPanel.myProject.getService(SendAction::class.java)
        sendAction.doActionPerformed(chatMainPanel, text)
    }

    override fun keyTyped(e: KeyEvent?) {

    }

    override fun keyPressed(e: KeyEvent) {
        if (e.keyCode == KeyEvent.VK_ENTER && !e.isControlDown && !e.isShiftDown) {
            e.consume()
            chatMainPanel.button.doClick()
        }
    }

    override fun keyReleased(e: KeyEvent?) {
    }
}