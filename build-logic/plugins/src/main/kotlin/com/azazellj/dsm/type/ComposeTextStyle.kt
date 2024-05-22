package com.azazellj.dsm.type

import com.azazellj.dsm.task.GeneratedType
import org.gradle.configurationcache.extensions.capitalized

internal data class ComposeTextStyle(
    val fontSize: Double,
    val textDecoration: String,
    val fontFamily: String,
    val fontWeight: Int,
    val fontStyle: String,
    val letterSpacing: Double,
    val lineHeight: Double,
) : GeneratedType

internal val ComposeTextStyle.fontFamilyFormatted: String
    get() = fontFamily.capitalized().replace("Roboto", "Default")
