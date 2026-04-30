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
        onClick: () -> Unit = {}
    ) {
        FollowButton(
            isFollowed = isFollowed,
            onClick = onClick
        )
    }

    @Test
    fun buttonClickEventReceived() {
        var clicked = false
        compose.setContent {
            TestFollowButton(
                onClick = { clicked = true }
            )
        }

        compose.onNodeWithText("Follow").performClick()
        assert(clicked)
    }

    @Test
    fun buttonDisplaysFollowLabel() {
        compose.setContent {
            TestFollowButton()
        }

        compose.onNodeWithText("Follow").assertIsDisplayed()
    }
}