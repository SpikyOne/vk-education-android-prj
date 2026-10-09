package ru.grigorev.vkeducationprj


import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp


class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            MaterialTheme {
                Surface(modifier = Modifier.fillMaxSize()) {
                    IntentsScreen()
                }
            }
        }
    }
}

@Composable
fun IntentsScreen() {

    val context = LocalContext.current

    var inputText by remember { mutableStateOf("") }
    var isError by remember { mutableStateOf(false) }
    var errorMessage by remember { mutableStateOf("") }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {

        OutlinedTextField(
            value = inputText,
            onValueChange = {
                inputText = it
                isError = false
            },
            label = { Text("Введите текст или номер телефона") },
            isError = isError,
            supportingText = { if (isError) Text(errorMessage) },
            modifier = Modifier.fillMaxWidth()
        )

        Spacer(modifier = Modifier.height(16.dp))

        // Явный Intent
        Button(
            onClick = {
                if (inputText.isBlank()) {
                    isError = true
                    errorMessage = "Поле не должно быть пустым!"
                } else {
                    val explicitIntent = Intent(context, SecondActivity::class.java).apply {
                        putExtra("USER_TEXT", inputText)
                    }
                    context.startActivity(explicitIntent)
                }
            },
            modifier = Modifier.fillMaxWidth()
        ) {
            Text("Открыть вторую Activity")
        }

        Spacer(modifier = Modifier.height(8.dp))

        // Неявный Intent
        Button(
            onClick = {
                if (inputText.isBlank() || !inputText.matches(Regex("^[+]?[0-9\\s()\\-]+$"))) {
                    isError = true
                    errorMessage = "Для звонка введите корректный номер!"
                    Toast.makeText(context, "Неверный формат номера", Toast.LENGTH_SHORT).show()
                } else {
                    val implicitIntent = Intent(Intent.ACTION_DIAL).apply {
                        data = Uri.parse("tel:$inputText")
                    }
                    context.startActivity(implicitIntent)
                }
            },
            modifier = Modifier.fillMaxWidth()
        ) {
            Text("Позвонить другу")
        }

        Spacer(modifier = Modifier.height(8.dp))

        // Системный Intent
        Button(
            onClick = {
                if (inputText.isBlank()) {
                    isError = true
                    errorMessage = "Нельзя поделиться пустым текстом!"
                } else {
                    val sendIntent = Intent(Intent.ACTION_SEND).apply {
                        type = "text/plain"
                        putExtra(Intent.EXTRA_TEXT, inputText)
                    }
                    val chooser = Intent.createChooser(sendIntent, "Поделиться через...")
                    context.startActivity(chooser)
                }
            },
            modifier = Modifier.fillMaxWidth()
        ) {
            Text("Поделиться текстом")
        }

    }

}
