package io.github.mslocombe.pixeltechnicalexercise.userlist

import android.content.Context
import androidx.compose.ui.test.assertIsDisplayed
import androidx.compose.ui.test.junit4.v2.createComposeRule
import androidx.compose.ui.test.onNodeWithText
import androidx.compose.ui.test.performClick
import androidx.test.core.app.ApplicationProvider
import io.github.mslocombe.mocking.StackExchangeApiMock
import io.github.mslocombe.mocking.storage.FollowDatastoreMock
import io.github.mslocombe.pixeltechnicalexercise.api.StackExchangeApiImpl
import io.github.mslocombe.pixeltechnicalexercise.api.StackOverflowUser
import io.github.mslocombe.pixeltechnicalexercise.api.mockStackExchangeResponse
import io.github.mslocombe.pixeltechnicalexercise.storage.FollowDatastoreImpl
import io.ktor.client.engine.mock.MockEngine
import io.ktor.client.engine.mock.respond
import io.ktor.http.HttpHeaders
import io.ktor.http.HttpStatusCode
import io.ktor.http.headersOf
import io.ktor.utils.io.ByteReadChannel
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.test.UnconfinedTestDispatcher
import kotlinx.coroutines.test.runTest
import org.junit.Rule
import org.junit.Test


class UserListViewTest {

    @get:Rule
    val compose = createComposeRule()

    // TODO No content - error

    @OptIn(ExperimentalCoroutinesApi::class)
    @Test
    fun userFollowFromClickToDatastore() = runTest(UnconfinedTestDispatcher()) {
        val context = ApplicationProvider.getApplicationContext<Context>()

        val theDatastore = FollowDatastoreImpl(context, this)

        val viewModel = UserListViewModelImpl(
            stackExchangeApi = StackExchangeApiMock().apply {
                getTopStackOverflowUsersReturn = listOf(StackOverflowUser(100, "", 0, ""))
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
        assert(storedData == setOf("100"))
    }

    @Test
    fun listViewDisplaysGivenCards() = runTest {
        // given
        // HttpClient - make request for top users
        val mockEngine = MockEngine { _ ->
            respond(
                content = ByteReadChannel(mockStackExchangeResponse),
                status = HttpStatusCode.OK,
                headers = headersOf(HttpHeaders.ContentType, "application/json")
            )
        }

        // when
        // Repository - translate to StackOverflowUsers
        val stackExchangeApi = StackExchangeApiImpl(mockEngine)

        // viewModel - Translate to UserCardState
        val viewModel = UserListViewModelImpl(stackExchangeApi, FollowDatastoreMock()) // Provide mock datastore

        // view -> display
        compose.setContent {
            UserListScreen(viewModel)
        }

        // then
        compose.onNodeWithText("Jon Skeet").assertIsDisplayed()
    }
}