package io.github.mslocombe.pixeltechnicalexercise.api

import kotlinx.coroutines.flow.Flow

interface StackExchangeApi {

    suspend fun getTopStackOverflowUsers(): StackExchangeApiResult

    fun getTopStackOverflowUsersFlow(): Flow<StackExchangeApiResult>
}