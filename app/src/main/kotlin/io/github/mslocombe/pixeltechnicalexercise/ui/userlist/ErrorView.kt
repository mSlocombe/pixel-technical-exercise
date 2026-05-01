package io.github.mslocombe.pixeltechnicalexercise.ui.userlist

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import io.github.mslocombe.pixeltechnicalexercise.R
import io.github.mslocombe.pixeltechnicalexercise.ui.theme.PixelTechnicalExerciseTheme

@Composable
fun ErrorView(modifier: Modifier = Modifier) {
    Box(modifier.testTag("UserListError"), contentAlignment = Alignment.Center) {
        Column(horizontalAlignment = Alignment.CenterHorizontally) {
            Image(
                painter = painterResource(R.drawable.ic_load_error),
                contentDescription = null
            )
            Text(
                text = stringResource(R.string.request_error),
                style = MaterialTheme.typography.labelLarge
            )
        }
    }
}

@Preview
@Composable
private fun Preview_ErrorView() {
    PixelTechnicalExerciseTheme {
        ErrorView(Modifier.fillMaxSize())
    }
}