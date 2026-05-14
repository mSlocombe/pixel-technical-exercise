package io.github.mslocombe.pixeltechnicalexercise.userlist

import io.github.mslocombe.mocking.StackExchangeApiMock
import io.github.mslocombe.mocking.storage.FollowDatastoreMock
import io.github.mslocombe.pixeltechnicalexercise.api.StackExchangeApiResult
import io.github.mslocombe.pixeltechnicalexercise.api.StackOverflowUser
import io.github.mslocombe.pixeltechnicalexercise.ui.components.usercard.UserCardState
import io.github.mslocombe.pixeltechnicalexercise.ui.userlist.UserListState
import io.github.mslocombe.pixeltechnicalexercise.ui.userlist.UserListViewModelImpl
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.launch
import kotlinx.coroutines.test.UnconfinedTestDispatcher
import kotlinx.coroutines.test.resetMain
import kotlinx.coroutines.test.runTest
import kotlinx.coroutines.test.setMain
import org.junit.After
import org.junit.Before
import org.junit.Test

@OptIn(ExperimentalCoroutinesApi::class)
class UserListViewModelTest {

    @Before
    fun setup() {
        Dispatchers.setMain(UnconfinedTestDispatcher())
    }


    @After
    fun cleanup() {
        Dispatchers.resetMain()
    }

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
            backgroundScope,
            defaultDispatcher = Dispatchers.Main
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