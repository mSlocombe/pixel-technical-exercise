package io.github.mslocombe.mocking

import io.github.mslocombe.pixeltechnicalexercise.ui.userlist.UserListState
import io.github.mslocombe.pixeltechnicalexercise.ui.userlist.UserListViewModel
import kotlinx.coroutines.flow.MutableStateFlow

class UserListViewModelMock(
    initialState: UserListState
): UserListViewModel {

    override val uiState = MutableStateFlow(initialState)

    override fun followUser(userId: Int) {

    }

    override fun unfollowUser(userId: Int) {

    }
}