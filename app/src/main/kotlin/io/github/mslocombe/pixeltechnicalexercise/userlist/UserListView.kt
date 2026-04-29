package io.github.mslocombe.pixeltechnicalexercise.userlist

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import io.github.mslocombe.pixeltechnicalexercise.ui.components.usercard.UserCard
import io.github.mslocombe.pixeltechnicalexercise.ui.components.usercard.UserCardState
import io.github.mslocombe.pixeltechnicalexercise.ui.theme.PixelTechnicalExerciseTheme

@Composable
fun UserListView() {
    Scaffold(modifier = Modifier.fillMaxSize()) { scaffoldPadding ->
        Box(
            Modifier
                .fillMaxSize()
                .padding(scaffoldPadding)
                .padding(vertical = 8.dp),
        ) {
            UserCard(
                modifier = Modifier.fillMaxWidth(),
                state = UserCardState(
                    profilePictureUrl = "https://www.gravatar.com/avatar/6d8ebb117e8d83d74ea95fbdd0f87e13?s=256&d=identicon&r=PG",
                    name = "Jon Skeet",
                    reputation = 1526592
                )
            )
        }
    }
}

@Preview
@Composable
private fun Preview_UserListView() {
    PixelTechnicalExerciseTheme {
        UserListView()
    }
}