package io.github.mslocombe.pixeltechnicalexercise.api

import io.ktor.client.engine.mock.MockEngine
import io.ktor.client.engine.mock.respond
import io.ktor.http.HttpHeaders
import io.ktor.http.HttpStatusCode
import io.ktor.http.headersOf
import io.ktor.utils.io.ByteReadChannel
import kotlinx.coroutines.runBlocking
import org.junit.Test

class StackExchangeApiTest {

    @Test
    fun apiReturnsNoResults() = runBlocking {
        val mockEngine = MockEngine { _ ->
            respond(
                content = ByteReadChannel(
                    """
                        {
                           "items":[]
                        }
                    """.trimIndent()
                ),
                status = HttpStatusCode.OK,
                headers = headersOf(HttpHeaders.ContentType, "application/json")
            )
        }

        val api = StackExchangeApiImpl(mockEngine)

        val users = api.getTopStackOverflowUsers()

        assert(users == emptyList<StackOverflowUser>())
    }

    @Test
    fun apiReturnsSingleUserRecordMatchingResponse() = runBlocking {
        val mockEngine = MockEngine { _ ->
            respond(
                content = ByteReadChannel(
                    """
                        {
                           "items":[
                              {
                                 "badge_counts":{
                                    "bronze":9255,
                                    "silver":9202,
                                    "gold":877
                                 },
                                 "account_id":11683,
                                 "is_employee":false,
                                 "last_modified_date":1711287919,
                                 "last_access_date":1711355649,
                                 "reputation_change_year":13860,
                                 "reputation_change_quarter":13860,
                                 "reputation_change_month":3856,
                                 "reputation_change_week":118,
                                 "reputation_change_day":30,
                                 "reputation":1454978,
                                 "creation_date":1222430705,
                                 "user_type":"registered",
                                 "user_id":22656,
                                 "accept_rate":86,
                                 "location":"Reading, United Kingdom",
                                 "website_url":"http://csharpindepth.com",
                                 "link":"https://stackoverflow.com/users/22656/jon-skeet",
                                 "profile_image":"https://www.gravatar.com/avatar/6d8ebb117e8d83d74ea95fbdd0f87e13?s=256&d=identicon&r=PG",
                                 "display_name":"Jon Skeet"
                              }
                           ]
                        }
                    """.trimIndent()
                ),
                status = HttpStatusCode.OK,
                headers = headersOf(HttpHeaders.ContentType, "application/json")
            )
        }

        val api = StackExchangeApiImpl(mockEngine)

        val users = api.getTopStackOverflowUsers()

        val expected = listOf(
            StackOverflowUser(
                userId = 22656,
                name = "Jon Skeet",
                reputation = 1454978,
                profilePicture = "https://www.gravatar.com/avatar/6d8ebb117e8d83d74ea95fbdd0f87e13?s=256&d=identicon&r=PG"
            )
        )

        assert(
            users == expected
        ) {
            """
                expected:   $expected
                actual:     $users
            """.trimIndent()
        }
    }

    @Test
    fun apiReturnsManyUserRecords() = runBlocking {
        val mockEngine = MockEngine.Companion { _ ->
            respond(
                content = ByteReadChannel(
                    """
                        {
                           "items":[
                              {
                                 "badge_counts":{
                                    "bronze":9355,
                                    "silver":9316,
                                    "gold":895
                                 },
                                 "account_id":11683,
                                 "is_employee":false,
                                 "last_modified_date":1776779403,
                                 "last_access_date":1777394138,
                                 "reputation_change_year":4918,
                                 "reputation_change_quarter":1489,
                                 "reputation_change_month":1489,
                                 "reputation_change_week":38,
                                 "reputation_change_day":18,
                                 "reputation":1526592,
                                 "creation_date":1222430705,
                                 "user_type":"registered",
                                 "user_id":22656,
                                 "accept_rate":86,
                                 "location":"Reading, United Kingdom",
                                 "website_url":"http://csharpindepth.com",
                                 "link":"https://stackoverflow.com/users/22656/jon-skeet",
                                 "profile_image":"https://www.gravatar.com/avatar/6d8ebb117e8d83d74ea95fbdd0f87e13?s=256&d=identicon&r=PG",
                                 "display_name":"Jon Skeet"
                              },
                              {
                                 "badge_counts":{
                                    "bronze":5757,
                                    "silver":4807,
                                    "gold":569
                                 },
                                 "account_id":4243,
                                 "is_employee":false,
                                 "last_modified_date":1777327508,
                                 "last_access_date":1777384513,
                                 "reputation_change_year":7129,
                                 "reputation_change_quarter":1400,
                                 "reputation_change_month":1400,
                                 "reputation_change_week":80,
                                 "reputation_change_day":30,
                                 "reputation":1369486,
                                 "creation_date":1221344553,
                                 "user_type":"registered",
                                 "user_id":6309,
                                 "accept_rate":100,
                                 "location":"France",
                                 "website_url":"https://devstory.fyi/vonc",
                                 "link":"https://stackoverflow.com/users/6309/vonc",
                                 "profile_image":"https://i.sstatic.net/I4fiW.jpg?s=256",
                                 "display_name":"VonC"
                              }
                           ]
                        }
                    """.trimIndent()
                ),
                status = HttpStatusCode.OK,
                headers = headersOf(HttpHeaders.ContentType, "application/json")
            )
        }

        val api = StackExchangeApiImpl(mockEngine)

        val users = api.getTopStackOverflowUsers()

        val expected = listOf(
            StackOverflowUser(
                userId = 22656,
                name = "Jon Skeet",
                reputation = 1526592,
                profilePicture = "https://www.gravatar.com/avatar/6d8ebb117e8d83d74ea95fbdd0f87e13?s=256&d=identicon&r=PG"
            ), StackOverflowUser(
                userId = 6309,
                name = "VonC",
                reputation = 1369486,
                profilePicture = "https://i.sstatic.net/I4fiW.jpg?s=256"
            )
        )

        assert(users == expected)
    }

    @Test
    fun apiReturnsEmptyListForError() = runBlocking {
        val mockEngine = MockEngine { _ ->
            respond(
                content = ByteReadChannel(""),
                status = HttpStatusCode.NotFound,
                headers = headersOf(HttpHeaders.ContentType, "application/json")
            )
        }

        val api = StackExchangeApiImpl(mockEngine)

        val users = api.getTopStackOverflowUsers()

        assert(users == emptyList<StackOverflowUser>())
    }
}