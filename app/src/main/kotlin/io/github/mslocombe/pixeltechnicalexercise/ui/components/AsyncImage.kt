package io.github.mslocombe.pixeltechnicalexercise.ui.components

import android.graphics.BitmapFactory
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.ImageBitmap
import androidx.compose.ui.graphics.asImageBitmap
import androidx.compose.ui.layout.ContentScale
import io.ktor.client.HttpClient
import io.ktor.client.engine.okhttp.OkHttp
import io.ktor.client.request.get
import io.ktor.client.statement.bodyAsBytes

@Composable
fun AsyncImage(
    modifier: Modifier = Modifier, url: String
) {
    // I would normally use Coil for asynchronous image loading / caching
    var localBitmap by remember { mutableStateOf<ImageBitmap?>(null) }

    LaunchedEffect(url) {
        localBitmap = try {
            HttpClient(OkHttp).use { localClient ->
                val bytes = localClient.get(url).bodyAsBytes()
                BitmapFactory.decodeByteArray(bytes, 0, bytes.size).asImageBitmap()
            }
        } catch (_: Exception) {
            null
        }
    }

    Box(
        modifier = modifier, contentAlignment = Alignment.Center
    ) {
        localBitmap?.let { theBitmap ->
            Image(
                modifier = Modifier.fillMaxSize(),
                bitmap = theBitmap,
                contentScale = ContentScale.Fit,
                contentDescription = null
            )
        }
    }
}