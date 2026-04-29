package io.github.mslocombe.pixeltechnicalexercise.api

interface StackExchangeApi {

    suspend fun getTopUsers(site: String): List<StackOverflowUser>
}