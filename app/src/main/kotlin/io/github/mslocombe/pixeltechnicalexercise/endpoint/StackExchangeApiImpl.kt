package io.github.mslocombe.pixeltechnicalexercise.endpoint

import io.ktor.client.HttpClient
import io.ktor.client.engine.HttpClientEngine
import io.ktor.client.request.get
import io.ktor.client.statement.bodyAsText
import org.json.JSONObject

class StackExchangeApiImpl(
    engine: HttpClientEngine
) : StackExchangeApi {

    private val httpClient = HttpClient(engine)

    override suspend fun getTopUsers(site: String): List<StackOverflowUser> {
        return try {
            val response = httpClient.get(site).bodyAsText()
            val json = JSONObject(response)
            val items = json.getJSONArray("items")

            val resultList = mutableListOf<StackOverflowUser>()
            for (index in 0..<items.length()) {
                val thisItem = items.getJSONObject(index)
                resultList.add(
                    StackOverflowUser(
                        id = thisItem.getInt("account_id")
                    )
                )
            }
            resultList
        } catch (_: Exception) { // TODO specify exception types
            emptyList()
        }
    }
}