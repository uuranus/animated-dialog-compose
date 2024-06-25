package com.uuranus.animated.compose.dialog

import androidx.compose.animation.core.Ease
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.tween
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.BoxScope
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.layout.layout
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Dialog
import androidx.compose.ui.window.DialogProperties
import kotlinx.coroutines.delay

@Composable
fun PopUpDialog(
    onDismissRequest: () -> Unit,
    horizontalPadding: Dp,
    dialogProperties: DialogProperties = DialogProperties(
        dismissOnClickOutside = true,
        dismissOnBackPress = true,
        usePlatformDefaultWidth = false
    ),
    content: @Composable BoxScope.() -> Unit = {},
) {

    var dialogState by remember { mutableStateOf(DialogState.Ready) }

    val configuration = LocalConfiguration.current
    val density = LocalDensity.current

    val screenWidthDp = configuration.screenWidthDp.dp

    val width = screenWidthDp - horizontalPadding * 2


    val scaleX by animateFloatAsState(
        targetValue = when (dialogState) {
            DialogState.Ready -> 0f
            DialogState.Opening -> 1f
            DialogState.Opened -> 1f
            DialogState.Closing -> 0f
            DialogState.Closed -> 0f
        },
        animationSpec = tween(300, easing = Ease), label = "scaleX"
    )

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
        properties = dialogProperties
    ) {

        Box(
            modifier = Modifier
                .width(width)
                .wrapContentHeight()
                .graphicsLayer {
                    this.scaleX = scaleX
                    this.scaleY = scaleY
                    this.alpha = alpha

                    if (scaleX == 1f) {
                        dialogState = DialogState.Opened
                    }
                }
                .background(Color.White, shape = RoundedCornerShape(12.dp))
                .clickable {
                    dialogState = DialogState.Closing
                }
                .padding(all = 24.dp),
        ) {
            Box(modifier = Modifier.graphicsLayer {
                this.alpha = contentAlpha

            }) {
                content()
            }
        }
    }

}

@Composable
fun CustomSizedBox(content: @Composable () -> Unit) {
    Dialog(
        onDismissRequest = {
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
                .layout { measurable, constraints ->
                    val placeable = measurable.measure(constraints)
                    // Custom logic to determine size
                    layout(placeable.width, placeable.height) {
                        placeable.placeRelative(0, 0)
                    }
                }
        ) {
            content()
        }
    }
}

@Composable
fun ExampleUsage() {
    CustomSizedBox {
        Column(
            modifier = Modifier
                .padding(16.dp)
                .background(Color.LightGray)
        ) {
            Text("Content 1")
            Text("Content 2")
            Text("Content 3")
        }
    }
}