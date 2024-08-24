package org.mc_dev_community.mcdevcommunity.servermessage

import com.google.gson.JsonArray
import com.intellij.openapi.ui.NullableComponent
import com.intellij.ui.Gray
import com.intellij.ui.JBColor
import com.intellij.ui.components.JBLabel
import com.intellij.ui.components.JBPanel
import com.intellij.ui.components.panels.VerticalLayout
import com.intellij.util.ui.JBFont
import com.intellij.util.ui.JBUI
import com.intellij.util.ui.UIUtil
import org.mc_dev_community.mcdevcommunity.panes.MyScrollPane
import java.awt.BorderLayout
import java.awt.Graphics
import java.awt.event.AdjustmentEvent
import java.awt.event.AdjustmentListener
import javax.swing.JPanel
import javax.swing.JScrollBar
import javax.swing.ScrollPaneConstants


class MessageGroupComponent: JBPanel<MessageGroupComponent>(), NullableComponent {
    private val myList: JPanel = JPanel(VerticalLayout(JBUI.scale(10)))
    private val myScrollPane: MyScrollPane = MyScrollPane(
        myList, ScrollPaneConstants.VERTICAL_SCROLLBAR_AS_NEEDED,
        ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER
    )
    private val scrollListener = AdjustmentListener { e ->
        val source = e.source as JScrollBar
        if (!source.valueIsAdjusting) {
            source.value = source.maximum
        }
    }
    private var myScrollValue = 0
    private var messages: JsonArray = JsonArray()

    init {
        border = JBUI.Borders.empty(10, 10, 10, 0)
        layout = BorderLayout(JBUI.scale(7), 0)
        background = UIUtil.getListBackground()

        val mainPanel = JPanel(BorderLayout(0, JBUI.scale(8)))
        mainPanel.isOpaque = false
        mainPanel.border = JBUI.Borders.emptyLeft(8)

        add(mainPanel, BorderLayout.CENTER)

        val myTitle = JBLabel("Conversation")
        myTitle.setForeground(JBColor.namedColor("Label.infoForeground", JBColor(Gray.x80, Gray.x8C)))
        myTitle.setFont(JBFont.label())

        val panel = JPanel(BorderLayout())
        panel.isOpaque = false
        panel.border = JBUI.Borders.empty(0, 10, 10, 0)

        panel.add(myTitle, BorderLayout.WEST)
        mainPanel.add(panel, BorderLayout.NORTH)

        myList.isOpaque = true
        myList.background = UIUtil.getListBackground()
        myList.border = JBUI.Borders.emptyRight(10)

        myScrollPane.setBorder(JBUI.Borders.empty())
        mainPanel.add(myScrollPane)
        myScrollPane.verticalScrollBar.setAutoscrolls(true)
        myScrollPane.verticalScrollBar.addAdjustmentListener {
            val value: Int = it.value
            if (myScrollValue == 0 && value > 0 || myScrollValue > 0 && value == 0) {
                myScrollValue = value
                repaint()
            } else {
                myScrollValue = value
            }
        }
    }

    fun add(messageComponent: MessageComponent?) {
        myList.add(messageComponent)
        updateLayout()
        scrollToBottom()
        updateUI()
    }

    fun scrollToBottom() {
        val verticalScrollBar: JScrollBar = myScrollPane.verticalScrollBar
        verticalScrollBar.value = verticalScrollBar.maximum
    }

    fun updateLayout() {
        val layout = myList.layout
        val componentCount = myList.componentCount
        for (i in 0 until componentCount) {
            layout.removeLayoutComponent(myList.getComponent(i))
            layout.addLayoutComponent(null, myList.getComponent(i))
        }
    }

    override fun paintComponent(g: Graphics) {
        super.paintComponent(g)
        if (myScrollValue > 0) {
            g.color = JBColor.border()
            val y: Int = myScrollPane.y - 1
            g.drawLine(0, y, width, y)
        }
    }


    override fun isVisible(): Boolean {
        if (super.isVisible()) {
            val count = myList.componentCount
            for (i in 0 until count) {
                if (myList.getComponent(i).isVisible) {
                    return true
                }
            }
        }
        return false
    }

    override fun isNull(): Boolean {
        return !isVisible
    }

    fun addScrollListener() {
        myScrollPane.verticalScrollBar.addAdjustmentListener(scrollListener)
    }

    fun removeScrollListener() {
        myScrollPane.verticalScrollBar.removeAdjustmentListener(scrollListener)
    }

    fun getMessages(): JsonArray {
        return messages
    }
}