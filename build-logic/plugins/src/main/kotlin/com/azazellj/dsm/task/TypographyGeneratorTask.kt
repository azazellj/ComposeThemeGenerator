package com.azazellj.dsm.task

import com.azazellj.dsm.type.ComposeTextStyle
import org.gradle.api.tasks.Internal
import org.json.JSONObject

internal abstract class TypographyGeneratorTask : TextStyleGeneratorTask() {

    override val generatedFileName: String @Internal get() = "ThemeTypography"
    override val rootDSMKey: String @Internal get() = "typography"

    override fun isTypeToken(field: Any): Boolean = field is JSONObject && field.getJSONObject(field.keySet().first()).has("value")

    override fun convert(field: Any): ComposeTextStyle {
        field as JSONObject

        return ComposeTextStyle(
            fontSize = field.getJSONObject("fontSize").getDouble("value"),
            textDecoration = field.getJSONObject("textDecoration").getString("value"),
            fontFamily = field.getJSONObject("fontFamily").getString("value"),
            fontWeight = field.getJSONObject("fontWeight").getInt("value"),
            fontStyle = field.getJSONObject("fontStyle").getString("value"),
            letterSpacing = field.getJSONObject("letterSpacing").getDouble("value"),
            lineHeight = field.getJSONObject("lineHeight").getDouble("value"),
        )
    }
}
