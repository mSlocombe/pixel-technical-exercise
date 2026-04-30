package io.github.mslocombe.pixeltechnicalexercise.userlist

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider.AndroidViewModelFactory.Companion.APPLICATION_KEY
import androidx.lifecycle.viewModelScope
import androidx.lifecycle.viewmodel.initializer
import androidx.lifecycle.viewmodel.viewModelFactory
import io.github.mslocombe.pixeltechnicalexercise.api.StackExchangeApi
import io.github.mslocombe.pixeltechnicalexercise.api.StackExchangeApiImpl
import io.github.mslocombe.pixeltechnicalexercise.storage.FollowDatastore
import io.github.mslocombe.pixeltechnicalexercise.storage.FollowDatastoreImpl
import io.github.mslocombe.pixeltechnicalexercise.ui.components.usercard.UserCardState
import io.ktor.client.engine.okhttp.OkHttp
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.SupervisorJob
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch

class UserListViewModelImpl(
    stackExchangeApi: StackExchangeApi,
    private val followingDatastore: FollowDatastore,
    coroutineScope: CoroutineScope = CoroutineScope(Dispatchers.Main.immediate + SupervisorJob())
) : UserListViewModel, ViewModel(coroutineScope) {

    override val cards = combine(
        stackExchangeApi.getTopStackOverflowUsersFlow(),
        followingDatastore.getFollows()
    ) { users, followedIds ->

        val followIdInts = followedIds.map { it.toInt() }
        users.map { user ->
            UserCardState(
                user.userId,
                user.profilePicture,
                user.name,
                user.reputation,
                followIdInts.contains(user.userId)
            )
        }
    }.stateIn(
        viewModelScope,
        SharingStarted.WhileSubscribed(5000),
        emptyList()
    )

    override fun followUser(userId: Int) {
        viewModelScope.launch {
            followingDatastore.saveFollow(userId)
        }
    }

    override fun unfollowUser(userId: Int) {
        viewModelScope.launch {
            followingDatastore.removeFollow(userId)
        }
    }

    companion object {
        val Factory = viewModelFactory {
            initializer {
                val applicationContext = this[APPLICATION_KEY]!!.applicationContext

                UserListViewModelImpl(
                    StackExchangeApiImpl(OkHttp.create()),
                    FollowDatastoreImpl(applicationContext)
                )
            }
        }
    }
}