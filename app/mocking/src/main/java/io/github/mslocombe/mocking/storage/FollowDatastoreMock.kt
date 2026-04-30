package io.github.mslocombe.mocking.storage

import io.github.mslocombe.pixeltechnicalexercise.storage.FollowDatastore
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow

class FollowDatastoreMock: FollowDatastore {

    private val followedUsers = mutableSetOf<String>()

    override suspend fun saveFollow(userId: Int) {
        followedUsers.add(userId.toString())
    }

    override fun getFollows(): Flow<Set<String>> = flow {
        emit(followedUsers)
    }
}