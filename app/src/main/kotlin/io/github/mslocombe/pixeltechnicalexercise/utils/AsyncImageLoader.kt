package io.github.mslocombe.pixeltechnicalexercise.utils

import android.graphics.BitmapFactory
import androidx.compose.ui.graphics.ImageBitmap
import androidx.compose.ui.graphics.asImageBitmap
import io.ktor.client.HttpClient
import io.ktor.client.engine.HttpClientEngine
import io.ktor.client.engine.okhttp.OkHttp
import io.ktor.client.request.get
import io.ktor.client.statement.bodyAsBytes
import kotlinx.coroutines.DelicateCoroutinesApi
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.currentCoroutineContext
import kotlinx.coroutines.ensureActive
import kotlinx.coroutines.newSingleThreadContext
import kotlinx.coroutines.withContext

//I would normally use Coil for async image loading and caching
object AsyncImageLoader {

    @OptIn(DelicateCoroutinesApi::class, ExperimentalCoroutinesApi::class)
    val singleThreadContext = newSingleThreadContext("AsyncImageLoaderContext")

    private val cache = mutableMapOf<String, ImageBitmap>()

    @OptIn(ExperimentalCoroutinesApi::class)
    suspend fun loadImage(
        url: String,
        engine: HttpClientEngine = OkHttp.create()
    ): ImageBitmap? = withContext(singleThreadContext) {
        try {
            val cachedBitmap = cache[url]
            if (cachedBitmap != null) return@withContext cachedBitmap

            val bitmap = HttpClient(engine).use { localClient ->
                val bytes = localClient.get(url).bodyAsBytes()
                BitmapFactory.decodeByteArray(bytes, 0, bytes.size).asImageBitmap()
            }

            cache[url] = bitmap
            bitmap
        } catch (_: Throwable) {
            currentCoroutineContext().ensureActive()
            null
        }
    }
}
