package org.mc_dev_community.mcdevcommunity.panes

import com.intellij.openapi.project.Project
import com.intellij.ui.components.JBTextField
import org.mc_dev_community.mcdevcommunity.locale.MDCLocaleUtil
import org.mc_dev_community.mcdevcommunity.multidevice.MultiDeviceServer
import java.awt.*
import java.net.InetAddress
import javax.swing.*

class ConfigurationMainPanel(val myProject: Project) {
    val mainPanel = JPanel()
    val multiDevicePanel = JPanel()
    val addressTextField = JBTextField(InetAddress.getLocalHost().hostAddress)
    val portTextField = JBTextField("7700")
    val connectButton = JButton(MDCLocaleUtil.message("MC-Dev-Community.Config.MultiDevice.ServerStart"))
    val disconnectButton = JButton(MDCLocaleUtil.message("MC-Dev-Community.Config.MultiDevice.ServerStop"))

    init {
        val ipPanel = JPanel()
        ipPanel.add(addressTextField, BorderLayout.WEST)
        ipPanel.add(JLabel(":"), BorderLayout.CENTER)
        ipPanel.add(portTextField, BorderLayout.EAST)
        connectButton.addActionListener {
            val address = addressTextField.text ?: return@addActionListener
            val strPort = portTextField.text ?: return@addActionListener
            val port = strPort.toIntOrNull() ?: return@addActionListener
            if (port in 1..65535) {
                MultiDeviceServer.runServer(address, port)
            }
        }

        disconnectButton.addActionListener {
            MultiDeviceServer.stopServer()
        }
        multiDevicePanel.add(ipPanel, BorderLayout.WEST)
        multiDevicePanel.add(connectButton, BorderLayout.CENTER)
        multiDevicePanel.add(disconnectButton, BorderLayout.EAST)
        mainPanel.add(multiDevicePanel, BorderLayout.NORTH)
    }

    fun init(): JPanel {
        return mainPanel
    }
}