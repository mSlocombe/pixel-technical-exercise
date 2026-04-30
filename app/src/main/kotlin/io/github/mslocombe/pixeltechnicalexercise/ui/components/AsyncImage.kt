package io.github.mslocombe.pixeltechnicalexercise.ui.components

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
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
import androidx.compose.ui.layout.ContentScale
import io.github.mslocombe.pixeltechnicalexercise.utils.AsyncImageLoader

@Composable
fun AsyncImage(
    modifier: Modifier = Modifier, url: String
) {
    // I would normally use Coil for asynchronous image loading / caching
    var localBitmap by remember { mutableStateOf<ImageBitmap?>(null) }

    LaunchedEffect(url) {
        localBitmap = AsyncImageLoader.loadImage(url)
    }

    Box(
        modifier = modifier, contentAlignment = Alignment.Center
    ) {
        AnimatedVisibility(
            localBitmap != null,
            enter = fadeIn(),
            exit = fadeOut()
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
}