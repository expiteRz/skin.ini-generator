import androidx.compose.animation.animateContentSize
import androidx.compose.desktop.DesktopMaterialTheme
import androidx.compose.desktop.Window
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.MoreVert
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.IntSize
import androidx.compose.ui.unit.TextUnit


fun main() = Window(title = "skin.ini Generator", size = IntSize(600, 900)) {
    DesktopMaterialTheme {
        Column {
            TopAppBar(title = { Text("skin.ini Generator") })
            content()
        }
    }
}

@Composable
fun content() = Column(modifier = Modifier.padding(Dp(16f))) {
    var name by remember { mutableStateOf("") }
    var author by remember { mutableStateOf(System.getProperty("user.name") as String) }
    var versionList = listOf("1.0", "2.0", "2.1", "2.2", "2.3", "2.4", "2.5", "latest")
    var versionSelected by remember { mutableStateOf(7) }
    var showVerMenu by remember { mutableStateOf(false) }

    // General
    Card {
        Column(
            Modifier.padding(Dp(16f))
        ) {
            Text("General", fontSize = TextUnit.Sp(20))
            Text("Name of skin")
            TextField(name, onValueChange = { name = it })
            Text("Author")
            TextField(author, onValueChange = { author = it })
            Text("Version")
            DropdownMenu(
                expanded = showVerMenu,
                onDismissRequest = { showVerMenu = false },
                toggle = @Composable {
                    OutlinedButton(onClick = {
                        showVerMenu = true
                        println(versionSelected)
                    }) {
                        Text(versionList[versionSelected])
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

    // Some Settings
    Card {
        Column(
            Modifier.padding(Dp(16f))
        ) {
            Text("Settings", fontSize = TextUnit.Sp(20))
            Text("")
        }
    }
}

