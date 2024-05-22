package com.azazellj.dsm.task

import com.squareup.kotlinpoet.ClassName
import com.squareup.kotlinpoet.CodeBlock
import com.azazellj.dsm.type.ComposeColor
import com.azazellj.dsm.type.valueFormatted
import org.gradle.api.tasks.Internal
import org.json.JSONObject

internal abstract class ColorGeneratorTask : AbstractGeneratorTask<ComposeColor>() {

    companion object {
        private val COLOR = ClassName("androidx.compose.ui.graphics", "Color")
    }

    override val componentClassName: ClassName @Internal get() = COLOR
    override val generatedFileName: String @Internal get() = "ThemeColors"
    override val rootDSMKey: String @Internal get() = "color"

    override fun isTypeToken(field: Any): Boolean = field is JSONObject && field.has("value")

    override fun convert(field: Any): ComposeColor {
        field as JSONObject

        return ComposeColor(value = field.getString("value"))
    }

    override fun writeTypeToken(key: String, type: ComposeColor): CodeBlock {
        return CodeBlock.of("%T(${type.valueFormatted})", componentClassName)
    }
}
