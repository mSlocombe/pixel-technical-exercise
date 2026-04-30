package io.github.mslocombe.pixeltechnicalexercise.api

sealed interface StackExchangeApiResult {
    data object Error: StackExchangeApiResult
    data class Success(val users: List<StackOverflowUser>): StackExchangeApiResult
}