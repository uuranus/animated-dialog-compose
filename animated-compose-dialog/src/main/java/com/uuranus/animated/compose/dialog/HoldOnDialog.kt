package com.uuranus.animated.compose.dialog

import androidx.compose.animation.animateContentSize
import androidx.compose.animation.core.Spring
import androidx.compose.animation.core.animateIntOffsetAsState
import androidx.compose.animation.core.spring
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.size
import androidx.compose.animation.core.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.unit.IntOffset
import androidx.compose.ui.unit.dp
import kotlin.math.roundToInt

internal val colorBlue = Color.Blue

enum class BoxState { Collapsed, Expanded }

@Composable
fun HoldOnDialog(
//    visibleState: Boolean,
    height: Int,
    horizontalPadding: Int,
    onClick: () -> Unit,
) {
    val configuration = LocalConfiguration.current

    var visibleState by remember { mutableStateOf(false) }

    val screenHeight = configuration.screenHeightDp.dp
    val screenWidth = configuration.screenWidthDp.dp

    val pxScreenWidth = with(LocalDensity.current) {
        screenWidth.toPx().roundToInt()
    }
    val pxScreenHeight = with(LocalDensity.current) {
        screenHeight.toPx().roundToInt()
    }

    val pxBottom = with(LocalDensity.current) {
        32.dp.toPx().roundToInt()
    }

    val pxHorizontalPadding = with(LocalDensity.current) {
        horizontalPadding.dp.toPx().roundToInt()
    }

    val pxWidth = with(LocalDensity.current) {
        screenWidth.toPx() - pxHorizontalPadding * 2
    }
    val pxHeight = with(LocalDensity.current) {
        height.dp.toPx().roundToInt()
    }

//    val transition = updateTransition(visibleState, label = "Tab indicator")
    var boxState by remember { mutableStateOf(BoxState.Collapsed) }
    val transition = updateTransition(targetState = boxState, label = "boxState")

//    val boxWidth by transition.animateDp(label = "") { state ->
//        if (state) {
//            screenWidth - horizontalPadding.dp * 2
//        } else {
//            20.dp
//        }
//    }

    val width by transition.animateDp(label = "width") { state ->
        when (state) {
            BoxState.Collapsed -> 1.dp
            BoxState.Expanded -> screenWidth / 2 - horizontalPadding.dp
        }
    }

//    val boxHeight by transition.animateDp(label = "") { state ->
//        if (state) {
//            height.dp
//        } else {
//            20.dp
//        }
//    }

    val leftOffset by transition.animateIntOffset(label = "offset") { state ->
        when (state) {
            BoxState.Collapsed ->
                IntOffset(pxScreenWidth / 2, pxScreenHeight - pxBottom - pxHeight)

            BoxState.Expanded -> IntOffset(
                pxHorizontalPadding,
                pxScreenHeight - pxBottom - pxHeight
            )
        }
    }

    //leftBox
    Box(
        modifier = Modifier
            .offset { leftOffset }
            .animateContentSize(
                animationSpec = spring(
                    dampingRatio = Spring.DampingRatioNoBouncy,
                    stiffness = Spring.StiffnessLow
                )
            )
            .size(width = width, height = 100.dp)
            .background(colorBlue)
            .clickable(
                interactionSource = remember { MutableInteractionSource() },
                indication = null
            ) {
//                onClick()
                boxState = if (boxState == BoxState.Collapsed) {
                    BoxState.Expanded
                } else {
                    BoxState.Collapsed
                }
            }
    )
    //rightBox
    Box(
        modifier = Modifier
            .offset { IntOffset(pxScreenWidth / 2, pxScreenHeight - pxBottom - pxHeight) }
            .animateContentSize(
                animationSpec = spring(
                    dampingRatio = Spring.DampingRatioNoBouncy,
                    stiffness = Spring.StiffnessLow
                )
            )
            .size(width = width, height = 100.dp)
            .background(colorBlue)
            .clickable(
                interactionSource = remember { MutableInteractionSource() },
                indication = null
            ) {
//                onClick()
                boxState = if (boxState == BoxState.Collapsed) {
                    BoxState.Expanded
                } else {
                    BoxState.Collapsed
                }
            }
    )

}