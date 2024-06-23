package com.uuranus.animated.compose.dialog

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
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.layout.onGloballyPositioned
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Dialog
import androidx.compose.ui.window.DialogProperties
import kotlinx.coroutines.delay

@Composable
fun VerticalExpandDialog(
    onDismissRequest: () -> Unit,
    content: @Composable BoxScope.() -> Unit = {},
) {

    var dialogState by remember { mutableStateOf(DialogState.Ready) }

    val configuration = LocalConfiguration.current

    val minHeightDp = 100.dp
    val minHeightPx = with(
        LocalDensity.current
    ) {
        minHeightDp.roundToPx()
    }
    val maxHeightDp =
        configuration.screenHeightDp.dp / 2
    val maxHeightPx = with(
        LocalDensity.current
    ) {
        maxHeightDp.roundToPx()
    }

    val scaleY by animateFloatAsState(
        targetValue = when (dialogState) {
            DialogState.Ready -> 0f
            DialogState.Opening -> 1f
            DialogState.Opened -> 1f
            DialogState.Closing -> 0f
            DialogState.Closed -> 0f
        },
        animationSpec = tween(300, easing = Ease), label = "scaleY"
    )

    val alpha by animateFloatAsState(
        targetValue = when (dialogState) {
            DialogState.Ready -> 0f
            DialogState.Opening -> 1f
            DialogState.Opened -> 1f
            DialogState.Closing -> 0f
            DialogState.Closed -> 0f
        },
        animationSpec = tween(300, easing = Ease),
        label = "alpha"
    )

    LaunchedEffect(dialogState) {
        if (dialogState == DialogState.Ready) {
            delay(700)
            dialogState = DialogState.Opening
        } else if (dialogState == DialogState.Closing) {
            delay(500)
            dialogState = DialogState.Closed
        } else if (dialogState == DialogState.Closed) {
            onDismissRequest()
        }
    }

    var contentHeightPx by remember { mutableIntStateOf(0) }
    val contentHeightDp = with(
        LocalDensity.current
    ) {
        contentHeightPx.toDp()
    }

    val dialogHeightDp =
        if (contentHeightPx < minHeightPx) minHeightDp
        else if (contentHeightPx > maxHeightPx) maxHeightDp
        else contentHeightDp

    Dialog(
        onDismissRequest = {
            dialogState = DialogState.Closing
        },
        properties = DialogProperties(
            dismissOnClickOutside = true,
            dismissOnBackPress = true,
            usePlatformDefaultWidth = false
        )
    ) {

        Box(
            modifier = Modifier
                .fillMaxWidth()
                .height(dialogHeightDp)
                .clickable {
                    dialogState = DialogState.Closing
                }
                .onGloballyPositioned { coordinates ->
                    if (contentHeightPx == 0)
                        contentHeightPx = coordinates.size.height
                }
                .graphicsLayer {
                    this.scaleY = scaleY
                    this.alpha = alpha

                    if (scaleY == 1f && alpha == 1f) {
                        dialogState = DialogState.Opened
                    }
                }
                .background(Color.White)
                .padding(16.dp)
        ) {
            AnimatedVisibility(
                visible = dialogState == DialogState.Opened,
                enter = fadeIn(animationSpec = tween(durationMillis = 300)),
                exit = fadeOut(animationSpec = tween(durationMillis = 300)),
                modifier = Modifier.align(Alignment.Center)
            ) {
                content()
            }

        }
    }

}
