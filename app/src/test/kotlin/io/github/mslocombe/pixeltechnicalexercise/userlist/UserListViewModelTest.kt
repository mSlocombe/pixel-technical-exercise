package io.github.mslocombe.pixeltechnicalexercise.userlist

import io.github.mslocombe.mocking.StackExchangeApiMock
import io.github.mslocombe.mocking.storage.FollowDatastoreMock
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
    fun stackOverflowUsersTranslatedToUserCards() = runTest(UnconfinedTestDispatcher()) {
        val stackExchangeApi = StackExchangeApiMock().apply {
            getTopStackOverflowUsersReturn = listOf(
                StackOverflowUser(1, "User 1", 1, ""),
                StackOverflowUser(2, "User 2", 2, "")
            )
        }

        val viewModel = UserListViewModelImpl(
            stackExchangeApi,
            FollowDatastoreMock(),
            backgroundScope
        )

        var collectedList: List<UserCardState>? = null
        backgroundScope.launch {
            viewModel.cards.collect {
                collectedList = it
            }
        }

        val expected = listOf(
            UserCardState(1, "", "User 1", 1, false),
            UserCardState(2, "", "User 2", 2, false)
        )

        assert(collectedList == expected) {
            collectedList.toString()
        }
    }
}