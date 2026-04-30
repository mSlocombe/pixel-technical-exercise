package io.github.mslocombe.pixeltechnicalexercise.ui.components.usercard

import androidx.compose.runtime.Composable
import androidx.compose.ui.test.assertIsDisplayed
import androidx.compose.ui.test.junit4.v2.createComposeRule
import androidx.compose.ui.test.onNodeWithText
import androidx.compose.ui.test.performClick
import org.junit.Rule
import org.junit.Test

class FollowButtonTest {

    @get:Rule
    val compose = createComposeRule()

    @Composable
    private fun TestFollowButton(
        isFollowed: Boolean = false,
        onFollow: () -> Unit = {},
        onUnfollow: () -> Unit = {}
    ) {
        FollowButton(
            isFollowed = isFollowed,
            onFollow = onFollow,
            onUnfollow = onUnfollow
        )
    }

    @Test
    fun buttonUnfollowEventReceived() {
        var eventReceived = false
        compose.setContent {
            TestFollowButton(
                isFollowed = true,
                onUnfollow = { eventReceived = true }
            )
        }

        compose.onNodeWithText("Unfollow").performClick()
        assert(eventReceived)
    }

    @Test
    fun buttonFollowEventReceived() {
        var eventReceived = false
        compose.setContent {
            TestFollowButton(
                isFollowed = false,
                onFollow = { eventReceived = true }
            )
        }

        compose.onNodeWithText("Follow").performClick()
        assert(eventReceived)
    }

    @Test
    fun buttonDisplaysUnfollowWhenFollowed() {
        compose.setContent {
            TestFollowButton(
                isFollowed = true
            )
        }

        compose.onNodeWithText("Unfollow").assertIsDisplayed()
    }

    @Test
    fun buttonDisplaysFollowWhenNotFollowed() {
        compose.setContent {
            TestFollowButton(
                isFollowed = false
            )
        }

        compose.onNodeWithText("Follow").assertIsDisplayed()
    }
}