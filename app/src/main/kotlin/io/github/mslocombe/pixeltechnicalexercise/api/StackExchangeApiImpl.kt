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

    override suspend fun getTopStackOverflowUsers(): StackExchangeApiResult {
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
            StackExchangeApiResult.Success(resultList)
        } catch(jsonException: JSONException) {
            Log.e(TAG, "getTopStackOverflowUsers: $jsonException")
            StackExchangeApiResult.Error
        } catch (unhandledException: Exception) {
            // General catch allows us to create specific handling for unforeseen exceptions
            Log.e(TAG, "getTopStackOverflowUsers unhandled exception: $unhandledException")
            StackExchangeApiResult.Error
        }
    }

    override fun getTopStackOverflowUsersFlow(): Flow<StackExchangeApiResult> = flow {
        emit(getTopStackOverflowUsers())
    }
}