package io.github.mslocombe.pixeltechnicalexercise.endpoint

interface StackExchangeApi {

    suspend fun getTopUsers(site: String): List<StackOverflowUser>
}