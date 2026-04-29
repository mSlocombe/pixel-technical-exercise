package io.github.mslocombe.pixeltechnicalexercise

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import io.github.mslocombe.pixeltechnicalexercise.ui.theme.PixelTechnicalExerciseTheme
import io.github.mslocombe.pixeltechnicalexercise.userlist.UserListScreen

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()

        setContent {
            PixelTechnicalExerciseTheme {
                UserListScreen()
            }
        }
    }

    override fun onDestroy() {
        super.onDestroy()
    }
}