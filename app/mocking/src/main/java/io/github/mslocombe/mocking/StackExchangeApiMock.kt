package io.github.mslocombe.mocking

import io.github.mslocombe.pixeltechnicalexercise.api.StackExchangeApi
import io.github.mslocombe.pixeltechnicalexercise.api.StackExchangeApiResult
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow

class StackExchangeApiMock: StackExchangeApi {

    var getTopStackOverflowUsersReturn: StackExchangeApiResult = StackExchangeApiResult.Success(emptyList())
    override suspend fun getTopStackOverflowUsers(): StackExchangeApiResult {
        return getTopStackOverflowUsersReturn
    }

    override fun getTopStackOverflowUsersFlow(): Flow<StackExchangeApiResult> = flow {
        emit(getTopStackOverflowUsersReturn)
    }
}