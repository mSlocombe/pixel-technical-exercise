package io.github.mslocombe.pixeltechnicalexercise.userlist

import androidx.compose.ui.test.assertIsDisplayed
import androidx.compose.ui.test.junit4.v2.createComposeRule
import androidx.compose.ui.test.onNodeWithText
import io.github.mslocombe.pixeltechnicalexercise.api.StackExchangeApiImpl
import io.ktor.client.engine.mock.MockEngine
import io.ktor.client.engine.mock.respond
import io.ktor.http.HttpHeaders
import io.ktor.http.HttpStatusCode
import io.ktor.http.headersOf
import io.ktor.utils.io.ByteReadChannel
import kotlinx.coroutines.test.runTest
import org.junit.Rule
import org.junit.Test

class UserListViewTest {

    @get:Rule
    val compose = createComposeRule()

    // TODO No content - error

    @Test
    fun listViewDisplaysGivenCards() = runTest {
        // given
        // HttpClient - make request for top users
        val mockEngine = MockEngine { _ ->
            respond(
                content = ByteReadChannel(
                    """
                        {
                           "items":[
                              {
                                 "display_name":"User 1",
                                 "reputation": 0
                              },
                              {
                                 "display_name":"User 2",
                                 "reputation": 0
                              }
                           ]
                        }
                    """.trimIndent()
                ),
                status = HttpStatusCode.OK,
                headers = headersOf(HttpHeaders.ContentType, "application/json")
            )
        }

        // when
        // Repository - translate to StackOverflowUsers
        val stackExchangeApi = StackExchangeApiImpl(mockEngine)

        // viewModel - Translate to UserCardState
        val viewModel = UserListViewModelImpl(stackExchangeApi)

        // view -> display
        compose.setContent {
            UserListScreen(viewModel)
        }

        // then
        compose.onNodeWithText("User 1").assertIsDisplayed()
        compose.onNodeWithText("User 2").assertIsDisplayed()
    }
}