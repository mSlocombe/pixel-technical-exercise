package io.github.mslocombe.pixeltechnicalexercise.api

import android.util.Log
import io.ktor.client.HttpClient
import io.ktor.client.engine.HttpClientEngine
import io.ktor.client.request.get
import io.ktor.client.statement.bodyAsText
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import org.json.JSONException
import org.json.JSONObject

class StackExchangeApiImpl(
    engine: HttpClientEngine
) : StackExchangeApi {
    companion object {
        private const val TAG = "StackExchangeApiImpl"
    }

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
                        userId = thisItem.getInt("user_id"),
                        name = thisItem.getString("display_name"),
                        reputation = thisItem.getInt("reputation"),
                        profilePicture = thisItem.getString("profile_image")
                    )
                )
            }
            resultList
        } catch(jsonException: JSONException) {
            Log.w(TAG, "getTopStackOverflowUsers: $jsonException")
            emptyList()
        } catch (unhandledException: Exception) {
            // General catch allows us to create specific handling for unforeseen exceptions
            Log.w(TAG, "getTopStackOverflowUsers unhandled exception: $unhandledException")
            emptyList()
        }
    }

    override fun getTopStackOverflowUsersFlow(): Flow<List<StackOverflowUser>> = flow {
        emit(getTopStackOverflowUsers())
    }
}