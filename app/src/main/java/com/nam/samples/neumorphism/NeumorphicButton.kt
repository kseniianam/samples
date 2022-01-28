package com.nam.samples.neumorphism

import android.view.MotionEvent
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.tween
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.BiasAlignment
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Shape
import androidx.compose.ui.input.pointer.pointerInteropFilter
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp

private enum class ButtonState {
    PRESSED,
    IDLE
}

internal enum class ShadowType {
    INNER,
    OUTER
}

internal data class NeumorphShapeConfig(
    val shape: Shape,
    val width: Dp,
    val height: Dp,
    val shadowType: ShadowType,
    val cornerRadius: Dp = 0.dp
)

internal class NeumorphConfig {

    companion object {
        val lightShadowColor: Color = Color.White
        val darkShadowColor: Color = Color.Gray
        val contentColor: Color = Color.LightGray
        val shadowBlurRadius: Dp = 40.dp
        val alpha: Float = 0.14f
    }
}

@ExperimentalComposeUiApi
@Preview
@Composable
internal fun NeumorphUISample() {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(color = NeumorphConfig.contentColor),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        NeumorphButton(
            shape = NeumorphShapeConfig(
                shape = RoundedCornerShape(32.dp),
                width = 343.dp,
                height = 80.dp,
                shadowType = ShadowType.OUTER,
                cornerRadius = 32.dp
            )
        )
        NeumorphButton(
            shape = NeumorphShapeConfig(
                shape = CircleShape,
                width = 100.dp,
                height = 100.dp,
                shadowType = ShadowType.OUTER,
                cornerRadius = 32.dp
            )
        )
    }
}

@ExperimentalComposeUiApi
@Composable
private fun NeumorphButton(
    modifier: Modifier = Modifier,
    shape: NeumorphShapeConfig
) {
    val buttonState = remember { mutableStateOf(ButtonState.IDLE) }

    val value by animateFloatAsState(
        targetValue = when (buttonState.value) {
            ButtonState.IDLE -> 0f
            ButtonState.PRESSED -> 1f
        },
        animationSpec = tween()
    )

    val bias = remember(key1 = value) {
        (3 - 2f * value) * 0.1f
    }

    NeumorphShape(
        modifier = modifier
            .clickable {}
            .pointerInteropFilter {
                when (it.action) {
                    MotionEvent.ACTION_DOWN -> {
                        buttonState.value = ButtonState.PRESSED
                        true
                    }
                    MotionEvent.ACTION_UP -> {
                        buttonState.value = ButtonState.IDLE
                        true
                    }
                    else -> {
                        true
                    }
                }
            },
        shapeConfig = shape,
        bias = bias
    )
}

@Composable
private fun NeumorphShape(
    modifier: Modifier = Modifier,
    shapeConfig: NeumorphShapeConfig,
    bias: Float = 0f
) {
    Box(modifier = modifier.size(shapeConfig.width + 50.dp, shapeConfig.height + 50.dp)) {
        Box(
            modifier = Modifier
                .size(shapeConfig.width, shapeConfig.height)
                .clip(shape = shapeConfig.shape)
                .shadow(
                    color = NeumorphConfig.lightShadowColor,
                    cornersRadius = 32.dp,
                    shadowBlurRadius = NeumorphConfig.shadowBlurRadius,
                    alpha = NeumorphConfig.alpha
                )
                .align(alignment = BiasAlignment(-bias, -bias))
        )

        Box(
            modifier = Modifier
                .size(shapeConfig.width, shapeConfig.height)
                .clip(shape = shapeConfig.shape)
                .shadow(
                    color = NeumorphConfig.darkShadowColor,
                    cornersRadius = 32.dp,
                    shadowBlurRadius = NeumorphConfig.shadowBlurRadius,
                    alpha = NeumorphConfig.alpha
                )
                .align(alignment = BiasAlignment(bias, bias))
        )

//        Box(
//            modifier = Modifier
//                .size(width, height + 20.dp)
//                .clip(shape = shape)
//                .background(
//                    brush = Brush.radialGradient(
//                        colors = listOf(Color.DarkGray, Color.Transparent),
//                        radius = width.value * 1.5f,
//                        center = Offset(0.7f, 0.7f)
//                    )
//                )
//                .align(alignment = BiasAlignment(bias, bias))
//        )

        Box(
            modifier = Modifier
                .size(shapeConfig.width, shapeConfig.height)
                .clip(shape = shapeConfig.shape)
                .align(alignment = Alignment.Center)
                .background(color = NeumorphConfig.contentColor)
        )
    }
}
