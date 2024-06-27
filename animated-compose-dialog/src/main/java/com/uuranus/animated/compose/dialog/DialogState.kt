package com.uuranus.animated.compose.dialog

import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Shape
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp

internal enum class DialogState { Ready, Opening, Opened, Closing, Closed }

data class ContainerProperties(
    val color: Color = Color.White,
    val shape: Shape = RoundedCornerShape(12.dp),
    val padding: Dp = 24.dp,
)