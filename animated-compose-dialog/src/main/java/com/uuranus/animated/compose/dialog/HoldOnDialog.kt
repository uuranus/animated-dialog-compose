package com.uuranus.animated.compose.dialog

import android.view.Gravity
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.core.Ease
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.tween
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.BoxScope
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.platform.LocalView
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Dialog
import androidx.compose.ui.window.DialogProperties
import androidx.compose.ui.window.DialogWindowProvider
import kotlinx.coroutines.delay

internal enum class BoxState { Ready, Opening, Opened, Closing, Closed }

@Composable
fun HoldOnDialog(
    onDismissRequest: () -> Unit,
    horizontalPadding: Dp,
    content: @Composable BoxScope.() -> Unit = {},
) {

    var boxState by remember { mutableStateOf(BoxState.Ready) }

    val configuration = LocalConfiguration.current
    val maxHeight = configuration.screenHeightDp.dp / 2

    val scaleX by animateFloatAsState(
        targetValue = when (boxState) {
            BoxState.Ready -> 0f
            BoxState.Opening -> 1f
            BoxState.Opened -> 1f
            BoxState.Closing -> 0f
            BoxState.Closed -> 0f
        },
        animationSpec = tween(300, easing = Ease), label = "scaleX"
    )
    val alpha by animateFloatAsState(
        targetValue = when (boxState) {
            BoxState.Ready -> 0f
            BoxState.Opening -> 1f
            BoxState.Opened -> 1f
            BoxState.Closing -> 0f
            BoxState.Closed -> 0f
        },
        animationSpec = tween(300, easing = Ease),
        label = "alpha"
    )

    LaunchedEffect(boxState) {
        if (boxState == BoxState.Ready) {
            delay(500)
            boxState = BoxState.Opening
        } else if (boxState == BoxState.Closing) {
            delay(500)
            boxState = BoxState.Closed
        } else if (boxState == BoxState.Closed) {
            onDismissRequest()
        }
    }

    Dialog(
        onDismissRequest = {
            boxState = BoxState.Closing
        },
        properties = DialogProperties(
            dismissOnClickOutside = true,
            dismissOnBackPress = true,
            usePlatformDefaultWidth = false
        )
    ) {

        val dialogWindowProvider = LocalView.current.parent as DialogWindowProvider
        dialogWindowProvider.window.setGravity(Gravity.BOTTOM)

        Box(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontalPadding)
                .padding(bottom = 100.dp)
                .heightIn(min = 100.dp, max = maxHeight)
                .graphicsLayer {
                    this.scaleX = scaleX
                    this.alpha = alpha

                    if (scaleX == 1f && alpha == 1f) {
                        boxState = BoxState.Opened
                    }
                }
                .background(Color.White, shape = RoundedCornerShape(12.dp))
                .clickable {
                    boxState = BoxState.Closing
                }
                .padding(all = 24.dp),
        ) {
            AnimatedVisibility(
                visible = boxState == BoxState.Opened,
                enter = fadeIn(animationSpec = tween(durationMillis = 300)),
                exit = fadeOut(animationSpec = tween(durationMillis = 300)),
                modifier = Modifier.align(Alignment.Center)
            ) {
                content()
            }
        }
    }

}
