package io.github.mslocombe.pixeltechnicalexercise.userlist

import androidx.compose.runtime.remember
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

    @Test
    fun followButtonClickReceived() {
        var clicked = false
        compose.setContent {
            UserCard(
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