package org.mc_dev_community.mcdevcommunity.servermessage

import com.intellij.ui.components.JBList
import javax.swing.JTextPane
import javax.swing.text.JTextComponent

class ServerMessageList: JBList<MessageComponent>() {
    init {
        //this.add(MessageComponent("Welcome to the MCDev Community server!"))
        //this.add(MessageComponent("Please read the rules before chatting."))
        //this.add(MessageComponent("Have fun!"))
        this.add(JTextPane().also { it.text = "hello" })
        this.add(JTextPane().also { it.text = "^_^" })
        this.updateUI()
    }
}