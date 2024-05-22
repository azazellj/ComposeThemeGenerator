package com.azazellj.dsm.task

import com.azazellj.dsm.type.ComposeTextStyle
import com.azazellj.dsm.type.fontFamilyFormatted
import com.squareup.kotlinpoet.ClassName
import com.squareup.kotlinpoet.CodeBlock
import com.squareup.kotlinpoet.MemberName
import org.gradle.api.tasks.Internal
import org.gradle.configurationcache.extensions.capitalized
import org.json.JSONObject

internal abstract class TextStyleGeneratorTask : AbstractGeneratorTask<ComposeTextStyle>() {

    companion object {
        private val TEXT_STYLE = ClassName("androidx.compose.ui.text", "TextStyle")
        private val FONT_STYLE = ClassName("androidx.compose.ui.text.font", "FontStyle")
        private val FONT_WEIGHT = ClassName("androidx.compose.ui.text.font", "FontWeight")
        private val FONT_FAMILY = ClassName("androidx.compose.ui.text.font", "FontFamily")
        private val TEXT_DECORATION = ClassName("androidx.compose.ui.text.style", "TextDecoration")

        private val SP = MemberName("androidx.compose.ui.unit", "sp")
    }

    override val componentClassName: ClassName @Internal get() = TEXT_STYLE
    override val generatedFileName: String @Internal get() = "ThemeTextStyles"
    override val rootDSMKey: String @Internal get() = "font" // naming issues...

    override fun isTypeToken(field: Any): Boolean = field is JSONObject && field.has("value")

    override fun convert(field: Any): ComposeTextStyle {
        field as JSONObject

        val value = field.getJSONObject("value")

        return ComposeTextStyle(
            fontSize = value.getDouble("fontSize"),
            textDecoration = value.getString("textDecoration"),
            fontFamily = value.getString("fontFamily"),
            fontWeight = value.getInt("fontWeight"),
            fontStyle = value.getString("fontStyle"),
            letterSpacing = value.getDouble("letterSpacing"),
            lineHeight = value.getDouble("lineHeight"),
        )
    }

    override fun writeTypeToken(key: String, type: ComposeTextStyle): CodeBlock {
        return CodeBlock.builder()
            .add("%T(", componentClassName)
            .add("\nfontSize = ${type.fontSize}.%M,", SP)
            .add("\nfontWeight = %T(${type.fontWeight}),", FONT_WEIGHT)
            .add("\nfontStyle = %T.${type.fontStyle.capitalized()},", FONT_STYLE)
            .add("\nfontFamily = %T.${type.fontFamilyFormatted},", FONT_FAMILY)
            .add("\nletterSpacing = ${type.letterSpacing}.%M,", SP)
            .add("\ntextDecoration = %T.${type.textDecoration.capitalized()},", TEXT_DECORATION)
            .add("\nlineHeight = ${type.lineHeight}.%M,", SP)
            .add("\n)")
            .build()

    }
}
