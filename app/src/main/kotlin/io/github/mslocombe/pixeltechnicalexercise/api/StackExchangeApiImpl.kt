package io.github.mslocombe.pixeltechnicalexercise.api

import io.ktor.client.HttpClient
import io.ktor.client.engine.HttpClientEngine
import io.ktor.client.request.get
import io.ktor.client.statement.bodyAsText
import org.json.JSONObject

class StackExchangeApiImpl(
    engine: HttpClientEngine
) : StackExchangeApi {

    private val httpClient = HttpClient(engine)

    override suspend fun getTopStackOverflowUsers(): List<StackOverflowUser> {
        return try {
            val response =
                httpClient.get("https://api.stackexchange.com/2.2/users?page=1&pagesize=20&order=desc&sort=reputation&site=stackoverflow")
                    .bodyAsText()
            val json = JSONObject(response)
            val items = json.getJSONArray("items")

            val resultList = mutableListOf<StackOverflowUser>()
            for (index in 0..<items.length()) {
                val thisItem = items.getJSONObject(index)
                resultList.add(
                    StackOverflowUser(
                        name = thisItem.getString("display_name")
                    )
                )
            }
            resultList
        } catch (_: Exception) { // TODO specify exception types
            emptyList()
        }
    }
}