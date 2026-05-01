package io.github.mslocombe.pixeltechnicalexercise.ui.userlist

import kotlinx.coroutines.flow.StateFlow

interface UserListViewModel {
    val uiState: StateFlow<UserListState>

    fun followUser(userId: Int)
    fun unfollowUser(userId: Int)
}