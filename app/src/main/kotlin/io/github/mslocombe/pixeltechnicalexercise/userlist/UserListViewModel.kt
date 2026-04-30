package io.github.mslocombe.pixeltechnicalexercise.userlist

import io.github.mslocombe.pixeltechnicalexercise.ui.components.usercard.UserCardState
import kotlinx.coroutines.flow.StateFlow

interface UserListViewModel {

    val cards: StateFlow<List<UserCardState>>

    fun followUser(userId: Int)
    fun unfollowUser(userId: Int)
}