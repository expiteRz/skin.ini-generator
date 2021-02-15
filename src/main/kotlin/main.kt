import androidx.compose.animation.animateContentSize
import androidx.compose.desktop.AppManager
import androidx.compose.desktop.DesktopMaterialTheme
import androidx.compose.desktop.Window
import androidx.compose.desktop.WindowEvents
import androidx.compose.foundation.ScrollableColumn
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.gestures.draggable
import androidx.compose.foundation.gestures.forEachGesture
import androidx.compose.foundation.layout.*
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowDropDown
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.filled.MoreVert
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.gesture.ExperimentalPointerInput
import androidx.compose.ui.gesture.WindowTouchSlop
import androidx.compose.ui.gesture.scrollorientationlocking.Orientation
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.input.pointer.PointerEvent
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.layout.HorizontalAlignmentLine
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.IntSize
import androidx.compose.ui.unit.TextUnit
import androidx.compose.ui.unit.dp
import kotlin.system.exitProcess


fun main() = Window(
    title = "skin.ini Generator",
    size = IntSize(600, 900),
    undecorated = false, // Hopefully it's set to true in the future
) {
    var toggleMenu by remember { mutableStateOf(false) }
    var (showDialog, setShowDialog) = remember { mutableStateOf(false) }
    var menuList = listOf("Open", "Save", "Save as")
    DesktopMaterialTheme {
        Column {
            TopAppBar(title = { Text("skin.ini Generator") }, actions = {
                DropdownMenu({
                    IconButton({ println("Opened!"); toggleMenu = true }) {
                        Icon(Icons.Default.MoreVert)
                    }
                }, toggleMenu, onDismissRequest = { toggleMenu = false }) {
                    menuList.forEachIndexed { _, s ->
                        DropdownMenuItem({ println(s) }) {
                            Text(s)
                        }
                    }
                    Divider()
                    DropdownMenuItem({ setShowDialog(true) }) {
                        Text("Exit")
                    }
                }
                IconButton({ setShowDialog(true) }) {
                    Icon(Icons.Default.Close)
                }
            })
            ScrollableColumn {
                content()
            }
            showDialog(showDialog, setShowDialog)
        }
    }
}

@Composable
fun content() = Column(modifier = Modifier.padding(Dp(16f))) {
    var name by remember { mutableStateOf("My Skin") }
    var author by remember { mutableStateOf(System.getProperty("user.name") as String) }
    val versionList = listOf("1.0", "2.0", "2.1", "2.2", "2.3", "2.4", "2.5", "latest")
    var showVerMenu by remember { mutableStateOf(false) }
    var versionSelected by remember { mutableStateOf(7) }
    var framerate by remember { mutableStateOf("60") }
    var sliderBallTint by remember { mutableStateOf(false) }
    var rndComboBurst by remember { mutableStateOf(false) }
    var centeredCursor by remember { mutableStateOf(true) }
    var expandCursor by remember { mutableStateOf(true) }

    // General
    Card(Modifier.padding(vertical = 8.dp)) {
        Column(
            Modifier.padding(Dp(16f))
        ) {
            Text("General", fontSize = TextUnit.Sp(20))
            OutlinedTextField(
                value = name,
                onValueChange = { name = it },
                label = { Text("Name of skin") },
                modifier = Modifier.padding(8.dp)
            )
            OutlinedTextField(
                value = author,
                onValueChange = { author = it },
                label = { Text("Author") },
                modifier = Modifier.padding(8.dp)
            )
            Column(Modifier.padding(8.dp)) {
                Text("Version", Modifier.padding(bottom = 4.dp))
                DropdownMenu(
                    expanded = showVerMenu,
                    onDismissRequest = { showVerMenu = false },
                    toggle = @Composable {
                        OutlinedButton(
                            onClick = {
                                showVerMenu = true
                                println(versionSelected)
                            }
                        ) {
                            Row(horizontalArrangement = Arrangement.Start) {
                                Text(versionList[versionSelected], textAlign = TextAlign.Start)
                            }
                            Row(horizontalArrangement = Arrangement.End) {
                                Icon(Icons.Default.ArrowDropDown)
                            }
                        }
                    }
                ) {
                    versionList.forEachIndexed { index, s ->
                        DropdownMenuItem(onClick = {
                            showVerMenu = false
                            versionSelected = index
                        }) {
                            Text(s)
                        }
                    }
                }
            }
        }
    }

    // Some Settings
    Card(Modifier.padding(vertical = 8.dp)) {
        Column(
            Modifier.padding(Dp(16f))
        ) {
            Text("Settings", fontSize = TextUnit.Sp(20))
            OutlinedTextField(
                value = framerate,
                onValueChange = { framerate = it },
                label = { Text("Animation Framerate") },
                modifier = Modifier.padding(8.dp)
            )
            Text("Slider Ball Tint", Modifier.padding(8.dp))
            Switch(
                sliderBallTint,
                onCheckedChange = { sliderBallTint = it },
                modifier = Modifier.padding(6.dp)
            )
            Text("Random Comboburst", Modifier.padding(8.dp))
            Switch(
                rndComboBurst,
                { rndComboBurst = it },
                Modifier.padding(6.dp)
            )
            Text("Center Cursor", Modifier.padding(8.dp))
            Switch(
                centeredCursor,
                { centeredCursor = it },
                Modifier.padding(6.dp)
            )
            Text("Expand Cursor", Modifier.padding(8.dp))
            Switch(
                expandCursor, { expandCursor = it }, Modifier.padding(6.dp)
            )
        }
    }
}

@Composable
fun showDialog(showDialog: Boolean, setShowDialog: (Boolean) -> Unit) {
    if (showDialog) {
        AlertDialog(
            onDismissRequest = {},
            title = { Text("Please wait") },
            buttons = {
                Row(horizontalArrangement = Arrangement.End) {
                    Button({ println("Save") }) { Text("Save") } // Save
                    Button({ println("Dismiss") }) { Text("Dismiss") } // Dismiss
                    Button({ setShowDialog(false) }) { Text("Cancel") } // Cancel
                }
            },
            text = { Text("You still have changes.\nAre you sure to quit the application?") }
        )

    }
}





