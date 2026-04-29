package io.github.mslocombe.pixeltechnicalexercise

import android.graphics.BitmapFactory
import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.ImageBitmap
import androidx.compose.ui.graphics.asImageBitmap
import androidx.compose.ui.tooling.preview.Preview
import io.github.mslocombe.pixeltechnicalexercise.ui.theme.PixelTechnicalExerciseTheme
import io.ktor.client.HttpClient
import io.ktor.client.engine.okhttp.OkHttp
import io.ktor.client.request.get
import io.ktor.client.statement.bodyAsBytes
import io.ktor.client.statement.bodyAsText
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class MainActivity : ComponentActivity() {
    companion object {
        private const val TAG = "MainActivity"
    }

    val client = HttpClient(OkHttp)
    val scope = CoroutineScope(Dispatchers.IO)

    val path =
        "http://api.stackexchange.com/2.2/users?page=1&pagesize=20&order=desc&sort=reputation&site=stackoverflow"

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()

        setContent {
            PixelTechnicalExerciseTheme {
                var lastResponse by remember { mutableStateOf<String?>(null) }
                var imageBitmap by remember { mutableStateOf<ImageBitmap?>(null) }
                Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
                    Column {

                        // AsyncImage prototyping
                        // I would normally use Coil for asynchronous image loading / caching
                        LaunchedEffect(true) {
                            val newBitmap = HttpClient(OkHttp).use { localClient ->
                                val bytes =
                                    localClient.get("https://www.gravatar.com/avatar/6d8ebb117e8d83d74ea95fbdd0f87e13?s=256&d=identicon&r=PG")
                                        .bodyAsBytes()
                                BitmapFactory.decodeByteArray(bytes, 0, bytes.size).asImageBitmap()
                            }
                            imageBitmap = newBitmap
                        }

                        imageBitmap?.let { bitmap ->
                            Image(
                                bitmap = bitmap,
                                contentDescription = null
                            )
                        }
                        Greeting(
                            name = "Android", modifier = Modifier.padding(innerPadding)
                        )

                        // Client prototyping / response template retrieval
                        Button(
                            onClick = {
                                scope.launch {
                                    val res = client.get(path)
                                    Log.d(TAG, "onCreate status: ${res.status}")
                                    lastResponse = res.bodyAsText()
                                    Log.d(TAG, "onCreate response: $lastResponse")
                                }
                            }) {
                            Text("Request")
                        }
                        lastResponse?.let {
                            Text(it)
                        }
                    }
                }
            }
        }
    }

    override fun onDestroy() {
        client.close()
        super.onDestroy()
    }
}

@Composable
fun Greeting(name: String, modifier: Modifier = Modifier) {
    Text(
        text = "Hello $name!", modifier = modifier
    )
}

@Preview(showBackground = true)
@Composable
fun GreetingPreview() {
    PixelTechnicalExerciseTheme {
        Greeting("Android")
    }
}