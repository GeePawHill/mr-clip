import androidx.compose.desktop.ui.tooling.preview.Preview
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.gestures.rememberTransformableState
import androidx.compose.foundation.layout.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.input.pointer.PointerEventType
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.layout.onSizeChanged
import androidx.compose.ui.unit.DpSize
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.*

@Composable
@Preview
fun App() {
    FlowGraph()
}

val TOTAL_ELEMENT_HEIGHT = 800
val TALL_ELEMENT_HEIGHT = TOTAL_ELEMENT_HEIGHT
val TALL_ELEMENT_OFFSET = TOTAL_ELEMENT_HEIGHT - TALL_ELEMENT_HEIGHT
val SHORT_ELEMENT_HEIGHT = TOTAL_ELEMENT_HEIGHT / 4
val SHORT_ELEMENT_OFFSET = TOTAL_ELEMENT_HEIGHT - SHORT_ELEMENT_HEIGHT


@Composable
fun FlowGraph() {
    var scale by remember { mutableStateOf(1f) }
    var rotation by remember { mutableStateOf(0f) }
    var offset by remember { mutableStateOf(Offset.Zero) }
    val state = rememberTransformableState { zoomChange, offsetChange, rotationChange ->
        scale *= zoomChange
        rotation += rotationChange
        offset += offsetChange
    }
    Box(
        Modifier
            .fillMaxSize()
            .background(color = Color.LightGray)
            .border(3.dp, Color.Green)
            .pointerInput(PointerEventType.Scroll) {
                awaitPointerEventScope {
                    while (true) {
                        val event = awaitPointerEvent()
                        val direction = event.changes.first().scrollDelta.y
                        if (direction == 1.0f) {
                            scale = scale * 0.9f
                        }
                        if (direction == -1.0f) {
                            scale = scale * 1.1f
                        }
                    }
                }
            }
            .graphicsLayer(
                scaleX = scale,
                scaleY = scale,
                rotationZ = rotation,
                translationX = offset.x,
                translationY = offset.y
            )
    ) {
        Box(
            Modifier
                .height(SHORT_ELEMENT_HEIGHT.dp)
                .width(40.dp)
                .offset(0.dp, SHORT_ELEMENT_OFFSET.dp)
                .background(Color.Blue)
                .border(1.dp, Color.Black)
                .onSizeChanged { size ->
                    println("Blue: ${SHORT_ELEMENT_HEIGHT} ${size.height}")
                }
        )
        Box(
            Modifier
                .height(TALL_ELEMENT_HEIGHT.dp)
                .width(40.dp)
                .offset(40.dp, TALL_ELEMENT_OFFSET.dp)
                .background(Color.Red)
                .border(1.dp, Color.Black)
                .onSizeChanged { size ->
                    println("Red:  ${TALL_ELEMENT_HEIGHT} ${size.height}")
                }
        )
    }
}


fun main() = application {
    val windowState = remember { WindowState(size = DpSize(1600.dp, 700.dp)) }
    Window(
        state = windowState,
        onCloseRequest = ::exitApplication
    ) {
        App()
    }
}
