package com.uuranus.animated.compose.dialog

import androidx.compose.animation.core.Ease
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.tween
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.BoxScope
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Dialog
import androidx.compose.ui.window.DialogProperties
import kotlinx.coroutines.delay

@Composable
fun VerticalExpandDialog(
    onDismissRequest: () -> Unit,
    dialogProperties: DialogProperties = DialogProperties(
        dismissOnClickOutside = true,
        dismissOnBackPress = true,
        usePlatformDefaultWidth = false
    ),
    content: @Composable BoxScope.() -> Unit = {},
) {

    var dialogState by remember { mutableStateOf(DialogState.Ready) }

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

    val contentAlpha by animateFloatAsState(
        targetValue = when (dialogState) {
            DialogState.Ready -> 0f
            DialogState.Opening -> 0f
            DialogState.Opened -> 1f
            DialogState.Closing -> 0f
            DialogState.Closed -> 0f
        },
        animationSpec = tween(300, easing = Ease),
        label = "contentAlpha"
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

    Dialog(
        onDismissRequest = {
            dialogState = DialogState.Closing
        },
        properties = dialogProperties
    ) {

        Box(
            modifier = Modifier
                .fillMaxWidth()
                .wrapContentHeight()
                .clickable {
                    dialogState = DialogState.Closing
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
            Box(modifier = Modifier.graphicsLayer {
                this.alpha = contentAlpha

            }) {
                content()
            }

        }
    }

}
