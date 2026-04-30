package io.github.mslocombe.pixeltechnicalexercise.userlist

import io.github.mslocombe.mocking.StackExchangeApiMock
import io.github.mslocombe.mocking.storage.FollowDatastoreMock
import io.github.mslocombe.pixeltechnicalexercise.api.StackExchangeApiResult
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
    fun stackExchangeErrorResultsInErrorState() = runTest(UnconfinedTestDispatcher()) {
        val viewModel = UserListViewModelImpl(
            StackExchangeApiMock().apply {
                getTopStackOverflowUsersReturn = StackExchangeApiResult.Error
            },
            FollowDatastoreMock(),
            backgroundScope
        )

        var collectedState: UserListState? = null
        backgroundScope.launch {
            viewModel.uiState.collect {
                collectedState = it
            }
        }

        assert(collectedState == UserListState.Error)
    }

    @OptIn(ExperimentalCoroutinesApi::class)
    @Test
    fun stackOverflowUsersTranslatedToUserCards() = runTest(UnconfinedTestDispatcher()) {
        val stackExchangeApi = StackExchangeApiMock().apply {
            getTopStackOverflowUsersReturn = StackExchangeApiResult.Success(
                listOf(
                    StackOverflowUser(1, "User 1", 1, ""),
                    StackOverflowUser(2, "User 2", 2, "")
                )
            )
        }

        val viewModel = UserListViewModelImpl(
            stackExchangeApi,
            FollowDatastoreMock(),
            backgroundScope
        )

        var collectedList: UserListState? = null
        backgroundScope.launch {
            viewModel.uiState.collect {
                collectedList = it
            }
        }

        val expected = UserListState.Content(listOf(
            UserCardState(1, "", "User 1", 1, false),
            UserCardState(2, "", "User 2", 2, false)
        ))

        assert(collectedList == expected) {
            collectedList.toString()
        }
    }
}