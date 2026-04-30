package io.github.mslocombe.mocking

import io.github.mslocombe.pixeltechnicalexercise.ui.components.usercard.UserCardState
import io.github.mslocombe.pixeltechnicalexercise.userlist.UserListViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow

class UserListViewModelMock(
    initialCards: List<UserCardState>
): UserListViewModel {

    private val _cards = MutableStateFlow(initialCards)
    override val cards = _cards.asStateFlow()

    override fun followUser(userId: Int) {

    }
}