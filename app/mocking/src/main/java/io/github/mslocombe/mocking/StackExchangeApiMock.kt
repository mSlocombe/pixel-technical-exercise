package io.github.mslocombe.mocking

import io.github.mslocombe.pixeltechnicalexercise.api.StackExchangeApi
import io.github.mslocombe.pixeltechnicalexercise.api.StackOverflowUser
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow

class StackExchangeApiMock: StackExchangeApi {

    var getTopStackOverflowUsersReturn = listOf<StackOverflowUser>()
    override suspend fun getTopStackOverflowUsers(): List<StackOverflowUser> {
        return getTopStackOverflowUsersReturn
    }

    override fun getTopStackOverflowUsersFlow(): Flow<List<StackOverflowUser>> = flow {
        emit(getTopStackOverflowUsersReturn)
    }
}