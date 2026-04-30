package io.github.mslocombe.pixeltechnicalexercise.userlist

import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.test.junit4.v2.createComposeRule
import androidx.compose.ui.test.onNodeWithText
import androidx.compose.ui.test.performClick
import io.github.mslocombe.pixeltechnicalexercise.ui.components.usercard.UserCard
import io.github.mslocombe.pixeltechnicalexercise.ui.components.usercard.UserCardState
import org.junit.Rule
import org.junit.Test

class UserCardTest {

    @get:Rule
    val compose = createComposeRule()

    @Composable
    private fun TestUserCard(
        modifier: Modifier = Modifier,
        state: UserCardState = remember { UserCardState(1, "", "", 0, true) },
        onFollow: () -> Unit = {},
        onUnfollow: () -> Unit = {}
    ) {
        UserCard(
            modifier = modifier,
            state = state,
            onFollow = onFollow,
            onUnfollow = onUnfollow
        )
    }

    @Test
    fun unfollowButtonClickReceived() {
        var clicked = false
        compose.setContent {
            TestUserCard(
                state = remember { UserCardState(1, "", "", 0, true) },
                onUnfollow = { clicked = true }
            )
        }

        compose.onNodeWithText("Unfollow").performClick()
        assert(clicked)
    }

    @Test
    fun followButtonClickReceived() {
        var clicked = false
        compose.setContent {
            TestUserCard(
                state = remember { UserCardState(1, "", "", 0, false) },
                onFollow = {
                    clicked = true
                }
            )
        }

        compose.onNodeWithText("Follow").performClick()
        assert(clicked)
    }
}