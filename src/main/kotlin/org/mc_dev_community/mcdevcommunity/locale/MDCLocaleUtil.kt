package org.mc_dev_community.mcdevcommunity.locale

import com.intellij.DynamicBundle
import org.jetbrains.annotations.PropertyKey

private const val BUNDLE = "messages.LanguageBundle"

object MDCLocaleUtil: DynamicBundle(BUNDLE) {
    fun message(@PropertyKey(resourceBundle = BUNDLE) key: String, vararg params: Any)
        = "${getMessage(key)} ${params.joinToString(" ")}"
}
