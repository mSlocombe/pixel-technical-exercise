package io.github.mslocombe.mocking.storage

import io.github.mslocombe.pixeltechnicalexercise.storage.FollowDatastore
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update

class FollowDatastoreMock : FollowDatastore {

    private val followedUsers = MutableStateFlow<Set<String>>(emptySet())


    override suspend fun saveFollow(userId: Int) {
        followedUsers.update { currentSet ->
            currentSet.toMutableSet().also { it.add(userId.toString()) }
        }
    }

    override fun getFollows(): Flow<Set<String>> = followedUsers.asStateFlow()

    override suspend fun removeFollow(userId: Int) {
        followedUsers.update { currentSet ->
            currentSet.toMutableSet().also { it.remove(userId.toString()) }
        }
    }
}