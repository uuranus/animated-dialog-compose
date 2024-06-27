package com.uuranus.animated.compose.dialog

import androidx.compose.animation.core.Ease
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.tween
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.BoxScope
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.calculateEndPadding
import androidx.compose.foundation.layout.calculateStartPadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.unit.LayoutDirection
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Dialog
import androidx.compose.ui.window.DialogProperties
import kotlinx.coroutines.delay

@Composable
fun DropDownDialog(
    onDismissRequest: () -> Unit,
    paddingValues: PaddingValues = PaddingValues(
        top = 0.dp,
        start = 32.dp,
        end = 32.dp,
        bottom = 0.dp
    ),
    dialogProperties: DialogProperties = DialogProperties(
        dismissOnClickOutside = true,
        dismissOnBackPress = true,
        usePlatformDefaultWidth = false
    ),
    containerProperties: ContainerProperties = ContainerProperties(
        color = if (isSystemInDarkTheme()) {
            MaterialTheme.colorScheme.surfaceVariant
        } else {
            MaterialTheme.colorScheme.surface
        },
    ),
    content: @Composable BoxScope.() -> Unit = {},
) {

    var dialogState by remember { mutableStateOf(DialogState.Ready) }

    val configuration = LocalConfiguration.current
    val screenHeightDp = configuration.screenHeightDp.dp
    val screenHeightPx = with(
        LocalDensity.current
    ) {
        screenHeightDp.toPx()
    }
    val screenWidthDp = configuration.screenWidthDp.dp

    val widthDp =
        screenWidthDp - paddingValues.calculateStartPadding(LayoutDirection.Ltr) - paddingValues.calculateEndPadding(
            LayoutDirection.Ltr
        )

    val positionY by animateFloatAsState(
        targetValue = when (dialogState) {
            DialogState.Ready -> -screenHeightPx / 2
            DialogState.Opening -> 0f
            DialogState.Opened -> 0f
            DialogState.Closing -> -screenHeightPx / 2
            DialogState.Closed -> -screenHeightPx / 2
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
                .width(widthDp)
                .wrapContentHeight()
                .graphicsLayer {
                    this.translationY = positionY
                    this.alpha = alpha

                    if (alpha == 1f) {
                        dialogState = DialogState.Opened
                    }
                }
                .background(
                    color = containerProperties.color,
                    shape = containerProperties.shape
                )
                .clickable {
                    dialogState = DialogState.Closing
                }
                .padding(all = containerProperties.padding),
        ) {
            Box(modifier = Modifier.graphicsLayer {
                this.alpha = contentAlpha
            }) {
                content()
            }
        }
    }

}
