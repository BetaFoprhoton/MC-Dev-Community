package org.mc_dev_community.mcdevcommunity.panes

import com.intellij.ui.components.JBScrollPane
import com.intellij.util.ui.JBUI
import java.awt.Component


class MyScrollPane(view: Component?, vsbPolicy: Int, hsbPolicy: Int) :
    JBScrollPane(view, vsbPolicy, hsbPolicy) {
    override fun updateUI() {
        border = JBUI.Borders.empty()
        super.updateUI()
    }

    override fun setCorner(key: String?, corner: Component?) {
        border = JBUI.Borders.empty()
        super.setCorner(key, corner)
    }
}