package io.github.mslocombe.pixeltechnicalexercise.storage

import kotlinx.coroutines.flow.Flow

interface FollowDatastore {

    suspend fun saveFollow(userId: Int)

    fun getFollows(): Flow<Set<String>>
}