package io.github.mslocombe.pixeltechnicalexercise.userlist

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.lifecycle.viewmodel.compose.viewModel
import io.github.mslocombe.pixeltechnicalexercise.ui.components.usercard.UserCard
import io.github.mslocombe.pixeltechnicalexercise.ui.components.usercard.UserCardState
import io.github.mslocombe.pixeltechnicalexercise.ui.theme.PixelTechnicalExerciseTheme
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow

@Composable
fun UserListScreen(
    viewModel: UserListViewModel = viewModel<UserListViewModelImpl>(factory = UserListViewModelImpl.Factory)
) {

    val cards by viewModel.cards.collectAsStateWithLifecycle()

    Scaffold(modifier = Modifier.fillMaxSize()) { scaffoldPadding ->
        Box(
            Modifier
                .fillMaxSize()
                .padding(scaffoldPadding)
                .padding(vertical = 8.dp),
        ) {
            LazyColumn {
                items(cards) { thisCard ->
                    UserCard(
                        modifier = Modifier.fillMaxWidth(),
                        state = UserCardState(
                            profilePictureUrl = "https://www.gravatar.com/avatar/6d8ebb117e8d83d74ea95fbdd0f87e13?s=256&d=identicon&r=PG",
                            name = thisCard.name,
                            reputation = thisCard.reputation
                        )
                    )
                }
            }
        }
    }
}

@Preview
@Composable
private fun Preview_UserListScreen() {
    val viewModel = remember {
        object : UserListViewModel {
            override val cards: StateFlow<List<UserCardState>>
                get() = MutableStateFlow(
                    listOf(
                        UserCardState("", "User 1", 0)
                    )
                )
        }
    }

    PixelTechnicalExerciseTheme {
        UserListScreen(
            viewModel
        )
    }
}