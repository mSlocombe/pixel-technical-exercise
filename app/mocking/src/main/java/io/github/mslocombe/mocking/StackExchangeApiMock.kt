package io.github.mslocombe.mocking

import io.github.mslocombe.pixeltechnicalexercise.api.StackExchangeApi
import io.github.mslocombe.pixeltechnicalexercise.api.StackOverflowUser

class StackExchangeApiMock: StackExchangeApi {

    var getTopStackOverflowUsersReturn = listOf<StackOverflowUser>()
    override suspend fun getTopStackOverflowUsers(): List<StackOverflowUser> {
        return getTopStackOverflowUsersReturn
    }
}