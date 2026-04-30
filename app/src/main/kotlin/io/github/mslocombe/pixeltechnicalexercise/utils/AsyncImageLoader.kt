package io.github.mslocombe.pixeltechnicalexercise.utils

import android.graphics.BitmapFactory
import androidx.compose.ui.graphics.ImageBitmap
import androidx.compose.ui.graphics.asImageBitmap
import io.ktor.client.HttpClient
import io.ktor.client.engine.HttpClientEngine
import io.ktor.client.engine.okhttp.OkHttp
import io.ktor.client.request.get
import io.ktor.client.statement.bodyAsBytes

//I would normally use Coil for async image loading and caching
object AsyncImageLoader {

    val cache = mutableMapOf<String, ImageBitmap>()

    suspend fun loadImage(
        url: String,
        engine: HttpClientEngine = OkHttp.create()
    ): ImageBitmap? {
        try {
            val cachedBitmap = cache[url]
            if (cachedBitmap != null) return cachedBitmap

            val bitmap = HttpClient(engine).use { localClient ->
                val bytes = localClient.get(url).bodyAsBytes()
                BitmapFactory.decodeByteArray(bytes, 0, bytes.size).asImageBitmap()
            }

            cache[url] = bitmap
            return bitmap
        } catch (_: Exception) {
            return null
        }
    }
}
