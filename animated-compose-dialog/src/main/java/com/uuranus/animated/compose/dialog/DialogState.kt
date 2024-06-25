package com.uuranus.animated.compose.dialog

internal enum class DialogState { Ready, Opening, Opened, Closing, Closed }

internal data class DialogSize(
    val minHeightDp: Int = 0,
    val minHeightPx: Int = 0,
    val maxHeightPx: Int = 0,
    val mexHeightDp: Int = 0,
    val minWidthDp: Int = 0,
    val minWidthPx: Int = 0,
    val maxWidthDp: Int = 0,
    val maxWidthPx: Int = 0,
)