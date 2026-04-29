package io.github.mslocombe.pixeltechnicalexercise.api

interface StackExchangeApi {

    suspend fun getTopStackOverflowUsers(): List<StackOverflowUser>
}