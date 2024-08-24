package org.mc_dev_community.mcdevcommunity.servermessage

import com.intellij.notification.impl.ui.NotificationsUtil
import com.intellij.openapi.util.text.StringUtil
import com.intellij.ui.JBColor
import com.intellij.ui.components.JBPanel
import com.intellij.ui.components.panels.VerticalLayout
import com.intellij.util.ui.JBUI
import java.awt.BorderLayout
import java.awt.Component
import javax.accessibility.AccessibleContext
import javax.swing.JEditorPane
import javax.swing.JPanel


class MessageComponent(private val originMessage: String, me: Boolean):
    JBPanel<MessageComponent>() {
    init {
        isDoubleBuffered = true
        isOpaque = true
        background = if (me) JBColor(0xEAEEF7, 0x45494A) else JBColor(0xE0EEF7, 0x2d2f30 /*2d2f30*/)
        border = JBUI.Borders.empty(10, 10, 10, 0)
        layout = BorderLayout(JBUI.scale(7), 0)

        val iconPanel = JPanel(BorderLayout())
        iconPanel.isOpaque = false
        /*
        var imageIcon: Image
        try {
            val url: String = OpenAISettingsState.getInstance().imageUrl
            imageIcon = if (me) ImgUtils.getImage(URL(url)) else ImgUtils.iconToImage(ChatGPTIcons.OPEN_AI)
        } catch (e: Exception) {
            imageIcon = if (me) ImgUtils.iconToImage(ChatGPTIcons.ME) else ImgUtils.iconToImage(ChatGPTIcons.AI)
        }
        val scale: Image = ImgUtil.scale(imageIcon, 30, 30)
        iconPanel.add(JBLabel(ImageIcon(scale)), BorderLayout.NORTH)
        add(iconPanel, BorderLayout.WEST)
        */
        val centerPanel = JPanel(VerticalLayout(JBUI.scale(8)))
        centerPanel.isOpaque = false
        centerPanel.border = JBUI.Borders.emptyRight(10)
        centerPanel.add(createContentComponent(originMessage))
        add(centerPanel, BorderLayout.CENTER)

        val actionPanel = JPanel(BorderLayout())
        actionPanel.isOpaque = false
        actionPanel.border = JBUI.Borders.emptyRight(10)
        add(actionPanel, BorderLayout.EAST)
    }

    fun createContentComponent(content: String?): Component {
        val component = JEditorPane()
        component.isEditable = false
        component.putClientProperty(JEditorPane.HONOR_DISPLAY_PROPERTIES, true)
        component.setContentType("text/html; charset=UTF-8")
        component.setOpaque(false)
        component.setBorder(null)

        NotificationsUtil.configureHtmlEditorKit(component, false)
        component.putClientProperty(
            AccessibleContext.ACCESSIBLE_NAME_PROPERTY,
            content?.let { StringUtil.stripHtml(it, " ") }?.let { StringUtil.unescapeXmlEntities(it) }
        )

        component.text = content

        component.isEditable = false
        if (component.caret != null) {
            component.setCaretPosition(0)
        }

        component.revalidate()
        component.repaint()

        return component
    }

}