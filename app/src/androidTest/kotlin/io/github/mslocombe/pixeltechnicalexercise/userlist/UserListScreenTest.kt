package io.github.mslocombe.pixeltechnicalexercise.userlist

import androidx.compose.ui.test.assertIsDisplayed
import androidx.compose.ui.test.junit4.v2.createComposeRule
import androidx.compose.ui.test.onNodeWithText
import io.github.mslocombe.mocking.UserListViewModelMock
import io.github.mslocombe.pixeltechnicalexercise.ui.components.usercard.UserCardState
import org.junit.Rule
import org.junit.Test

class UserListScreenTest {

    @get:Rule
    val compose = createComposeRule()

    @Test
    fun userReputationsAreDisplayed() {
        val viewModel = UserListViewModelMock(
            listOf(
                UserCardState(
                    1, "", "name", 1, false
                )
            )
        )

        compose.setContent {
            UserListScreen(viewModel)
        }

        compose.onNodeWithText("Reputation: 1").assertIsDisplayed()
    }
}