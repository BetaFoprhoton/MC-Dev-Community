package org.mc_dev_community.mcdevcommunity.panes

import com.intellij.find.SearchTextArea
import com.intellij.icons.AllIcons
import com.intellij.ide.ui.laf.darcula.ui.DarculaButtonUI
import com.intellij.openapi.project.Project
import com.intellij.openapi.util.IconLoader.getIcon
import com.intellij.platform.ide.progress.ModalTaskOwner.project
import com.intellij.ui.OnePixelSplitter
import com.intellij.ui.components.JBTextArea
import org.mc_dev_community.mcdevcommunity.listener.SendListener
import org.mc_dev_community.mcdevcommunity.locale.LocaleUtil
import java.awt.BorderLayout
import java.awt.Dimension
import java.util.concurrent.ExecutorService
import javax.swing.JButton
import javax.swing.JPanel
import javax.swing.JProgressBar


class MainPanel {
    val searchTextArea = SearchTextArea(JBTextArea(), true)
    val button = JButton(LocaleUtil.message("MC-Dev-Community.Chat.Send"), getIcon("/icons/send.svg", MainPanel::class.java))
    val contentPanel = MessageGroupComponent(project, isChatGPTModel())
    val splitter = OnePixelSplitter(true, .98f)
    val myProject: Project? = null
    val actionPanel = JPanel(BorderLayout())
    val executorService: ExecutorService? = null
    val requestHolder: Any? = null

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

        splitter.setFirstComponent(contentPanel)
        splitter.setSecondComponent(actionPanel)
    }

}