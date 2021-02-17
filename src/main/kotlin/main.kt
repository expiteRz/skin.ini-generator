import androidx.compose.desktop.DesktopMaterialTheme
import androidx.compose.desktop.Window
import androidx.compose.foundation.layout.*
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.ArrowDropDown
import androidx.compose.material.icons.filled.MoreVert
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.IntSize
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import java.awt.FileDialog
import java.awt.Frame
import kotlin.system.exitProcess


fun main() = Window(
    title = "skin.ini Generator",
    size = IntSize(600, 900),
    undecorated = false, // Hopefully it's set to true in the future
) {
    var toggleMenu by remember { mutableStateOf(false) }
    val (showDialog, setShowDialog) = remember { mutableStateOf(false) }
    val menuList = listOf("Open", "Save", "Save as")
    DesktopMaterialTheme {
        Column {
            TopAppBar(title = { Text("skin.ini Generator") }, actions = {
                DropdownMenu({
                    IconButton({ println("Opened!"); toggleMenu = true }) {
                        Icon(Icons.Default.MoreVert, "")
                    }
                }, toggleMenu, onDismissRequest = { toggleMenu = false }) {
                    menuList.forEachIndexed { i, s ->
                        DropdownMenuItem({
                            println(s)
                            if (i == 0) {
                                toggleMenu = false
                                FileDialog(Frame()).also {
                                    it.title = "Select skin.ini..."
                                    it.isVisible = true
                                }
                            }
                        }) {
                            Text(s)
                        }
                    }
                    Divider()
                    DropdownMenuItem({ setShowDialog(true) }) {
                        Text("Exit")
                    }
                }
                IconButton({ println("New!") }) {
                    Icon(Icons.Default.Add, "")
                }
            })
            content()
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
            Text("General", fontSize = 20.sp)
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
                                Icon(Icons.Default.ArrowDropDown, "")
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
            Text("Settings", fontSize = 20.sp)
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
            Row {
                Switch(
                    centeredCursor,
                    { centeredCursor = it },
                    Modifier.padding(6.dp)
                )
                Text(if (centeredCursor) "Center" else "Top-Left", Modifier.padding(start = 8.dp))
            }
            Text("Expand Cursor When Clicked", Modifier.padding(8.dp))
            Switch(
                expandCursor, { expandCursor = it }, Modifier.padding(6.dp)
            )
        }
    }
}

@Composable
fun showDialog(showDialog: Boolean, setShowDialog: (Boolean) -> Unit) {
    val modifier = Modifier.padding(16.dp)
    if (showDialog) {
        AlertDialog(
            onDismissRequest = { setShowDialog(false) },
            title = { Text("Please wait") },
            buttons = {
                Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.Center) {
                    Button({ println("Save") }, modifier) { Text("Save") } // Save
                    Button({ println("Dismiss"); exitProcess(0) }, modifier) { Text("Dismiss") } // Dismiss
                    Button({ setShowDialog(false) }, modifier) { Text("Cancel") } // Cancel
                }
            },
            text = { Text("You still have changes.\nAre you sure to quit the application?") }
        )

    }
}





