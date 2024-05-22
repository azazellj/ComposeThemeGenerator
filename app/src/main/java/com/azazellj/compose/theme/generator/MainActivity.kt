package com.azazellj.compose.theme.generator

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import com.azazellj.compose.theme.generator.ui.theme.ComposeThemeGeneratorTheme
import com.azazellj.compose.theme.generator.ui.theme.ThemeTextStyles

class MainActivity : ComponentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        enableEdgeToEdge()

        setContent {
            ComposeThemeGeneratorTheme {
                Scaffold(
                    modifier = Modifier
                        .fillMaxSize(),
                ) { innerPadding ->
                    Greeting(
                        name = "Android",
                        modifier = Modifier
                            .padding(innerPadding),
                    )
                }
            }
        }
    }
}

@Composable
fun Greeting(
    name: String,
    modifier:
    Modifier = Modifier,
) {
    Text(
        text = "Hello $name!",
        modifier = modifier,
        style = ThemeTextStyles.fontM3BodyLarge,
    )
}

@Preview(showBackground = true)
@Composable
fun GreetingPreview() {
    ComposeThemeGeneratorTheme {
        Greeting("Android")
    }
}
