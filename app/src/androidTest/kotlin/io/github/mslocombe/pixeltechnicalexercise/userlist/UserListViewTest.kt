package io.github.mslocombe.pixeltechnicalexercise.userlist

import android.content.Context
import androidx.compose.ui.test.assertIsDisplayed
import androidx.compose.ui.test.junit4.v2.createComposeRule
import androidx.compose.ui.test.onNodeWithText
import androidx.compose.ui.test.performClick
import androidx.test.core.app.ApplicationProvider
import io.github.mslocombe.mocking.StackExchangeApiMock
import io.github.mslocombe.mocking.UserListViewModelMock
import io.github.mslocombe.mocking.storage.FollowDatastoreMock
import io.github.mslocombe.pixeltechnicalexercise.api.StackExchangeApiResult
import io.github.mslocombe.pixeltechnicalexercise.api.StackOverflowUser
import io.github.mslocombe.pixeltechnicalexercise.storage.FollowDatastoreImpl
import io.github.mslocombe.pixeltechnicalexercise.ui.components.usercard.UserCardState
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.test.UnconfinedTestDispatcher
import kotlinx.coroutines.test.runTest
import org.junit.Rule
import org.junit.Test


class UserListViewTest {

    @get:Rule
    val compose = createComposeRule()

    private val singleUserList = listOf(StackOverflowUser(100, "", 0, ""))

    // TODO No content - error

    @Test
    fun userUnfollowed() = runTest {
        val viewModel = UserListViewModelImpl(
            StackExchangeApiMock().apply { getTopStackOverflowUsersReturn = StackExchangeApiResult.Success(singleUserList) },
            FollowDatastoreMock().apply { saveFollow(100) }
        )

        compose.setContent {
            UserListScreen(viewModel)
        }

        compose.onNodeWithText("Unfollow").performClick()
        compose.onNodeWithText("Follow").assertIsDisplayed()
    }

    @OptIn(ExperimentalCoroutinesApi::class)
    @Test
    fun userFollowFromClickToDatastore() = runTest(UnconfinedTestDispatcher()) {
        val context = ApplicationProvider.getApplicationContext<Context>()

        val theDatastore = FollowDatastoreImpl(context, this)

        val viewModel = UserListViewModelImpl(
            stackExchangeApi = StackExchangeApiMock().apply {
                getTopStackOverflowUsersReturn = StackExchangeApiResult.Success(singleUserList)
            },
            theDatastore
        )

        compose.setContent {
            UserListScreen(
                viewModel = viewModel
            )
        }

        compose.onNodeWithText("Follow").performClick()

        val storedData = theDatastore.getFollows().first()
        assert(storedData == setOf("100")) {
            storedData.toString()
        }
    }

    @Test
    fun listViewDisplaysGivenCards() = runTest {
        val viewModel = UserListViewModelMock(
            initialCards = listOf(
                UserCardState(1, "", "Jon Skeet", 0, false)
            )
        )

        // view -> display
        compose.setContent {
            UserListScreen(viewModel)
        }

        // then
        compose.onNodeWithText("Jon Skeet").assertIsDisplayed()
    }
}