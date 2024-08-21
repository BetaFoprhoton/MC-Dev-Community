package org.mc_dev_community.mcdevcommunity.listener

import org.mc_dev_community.mcdevcommunity.panes.MainPanel
import java.awt.event.ActionEvent
import java.awt.event.ActionListener
import java.awt.event.KeyEvent
import java.awt.event.KeyListener
import java.io.IOException


class SendListener(val mainPanel: MainPanel): ActionListener, KeyListener {
    override fun actionPerformed(e: ActionEvent?) {
        try {
            doActionPerformed()
        } catch (ex: IOException) {
            throw RuntimeException(ex)
        }
    }

    @Throws(IOException::class)
    fun doActionPerformed() {
        val text: String = mainPanel.searchTextArea.textArea.getText()
        val sendAction: SendAction = mainPanel.getProject().getService(SendAction::class.java)
        sendAction.doActionPerformed(mainPanel, text)
    }

    override fun keyTyped(e: KeyEvent?) {
        TODO("Not yet implemented")
    }

    override fun keyPressed(e: KeyEvent) {
        if (e.keyCode == KeyEvent.VK_ENTER && !e.isControlDown && !e.isShiftDown) {
            e.consume()
            mainPanel.getButton().doClick()
        }
    }

    override fun keyReleased(e: KeyEvent?) {
        TODO("Not yet implemented")
    }
}