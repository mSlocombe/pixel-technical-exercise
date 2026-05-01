package io.github.mslocombe.pixeltechnicalexercise.ui.userlist

import io.github.mslocombe.pixeltechnicalexercise.ui.components.usercard.UserCardState

sealed interface UserListState {
    data object Error: UserListState
    data class Content(val cards: List<UserCardState>): UserListState
}