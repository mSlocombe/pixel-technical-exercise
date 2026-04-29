package io.github.mslocombe.pixeltechnicalexercise.userlist

import io.github.mslocombe.mocking.StackExchangeApiMock
import io.github.mslocombe.pixeltechnicalexercise.api.StackOverflowUser
import io.github.mslocombe.pixeltechnicalexercise.ui.components.usercard.UserCardState
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.launch
import kotlinx.coroutines.test.UnconfinedTestDispatcher
import kotlinx.coroutines.test.runTest
import org.junit.Test

class UserListViewModelTest {

    @OptIn(ExperimentalCoroutinesApi::class)
    @Test
    fun stackOverflowUsersTranslatedToUserCards() = runTest {
        val stackExchangeApi = StackExchangeApiMock().apply {
            getTopStackOverflowUsersReturn = listOf(
                StackOverflowUser("User 1"), StackOverflowUser("User 2")
            )
        }

        val viewModel = UserListViewModelImpl(stackExchangeApi)

        var collectedList: List<UserCardState>? = null
        backgroundScope.launch(UnconfinedTestDispatcher(testScheduler)) {
            viewModel.cards.collect {
                collectedList = it
            }
        }

        val expected = listOf(
            UserCardState("", "User 1", 0), UserCardState("", "User 2", 0)
        )

        assert(collectedList == expected)
    }
}