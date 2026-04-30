package io.github.mslocombe.pixeltechnicalexercise.api

import kotlinx.coroutines.flow.Flow

interface StackExchangeApi {

    suspend fun getTopStackOverflowUsers(): List<StackOverflowUser>

    fun getTopStackOverflowUsersFlow(): Flow<List<StackOverflowUser>>
}