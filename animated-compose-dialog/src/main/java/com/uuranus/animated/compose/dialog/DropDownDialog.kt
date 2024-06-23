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
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Dialog
import androidx.compose.ui.window.DialogProperties
import kotlinx.coroutines.delay

@Composable
fun DropDownDialog(
    onDismissRequest: () -> Unit,
    horizontalPadding: Dp,
    content: @Composable BoxScope.() -> Unit = {},
) {

    var dialogState by remember { mutableStateOf(DialogState.Ready) }

    val configuration = LocalConfiguration.current
    val minHeight = 100.dp
    val maxHeight = configuration.screenHeightDp.dp / 2
    val screenHeight = configuration.screenHeightDp.dp

    val positionY by animateFloatAsState(
        targetValue = when (dialogState) {
            DialogState.Ready -> -screenHeight.value / 2
            DialogState.Opening -> 0f
            DialogState.Opened -> 0f
            DialogState.Closing -> -screenHeight.value / 2
            DialogState.Closed -> -screenHeight.value / 2
        },
        animationSpec = tween(300, easing = Ease), label = "positionY"
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
            delay(500)
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
        properties = DialogProperties(
            dismissOnClickOutside = true,
            dismissOnBackPress = true,
            usePlatformDefaultWidth = false
        )
    ) {

        Box(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontalPadding)
                .heightIn(min = minHeight, max = maxHeight)
                .graphicsLayer {
                    this.translationY = positionY
                    this.alpha = alpha

                    if (alpha == 1f) {
                        dialogState = DialogState.Opened
                    }
                }
                .background(Color.White, shape = RoundedCornerShape(12.dp))
                .clickable {
                    dialogState = DialogState.Closing
                }
                .padding(all = 24.dp),
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
