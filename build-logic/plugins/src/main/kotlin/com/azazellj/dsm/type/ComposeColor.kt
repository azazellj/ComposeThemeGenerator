package com.azazellj.dsm.type

import com.azazellj.dsm.task.GeneratedType

internal data class ComposeColor(val value: String) : GeneratedType

// replace color from #ffffffff to 0xffffffff
internal val ComposeColor.valueFormatted: String get() = value.replace("#", "0x")
