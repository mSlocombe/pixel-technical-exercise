package io.github.mslocombe.pixeltechnicalexercise.userlist

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.lifecycle.viewmodel.initializer
import androidx.lifecycle.viewmodel.viewModelFactory
import io.github.mslocombe.pixeltechnicalexercise.api.StackExchangeApi
import io.github.mslocombe.pixeltechnicalexercise.api.StackExchangeApiImpl
import io.github.mslocombe.pixeltechnicalexercise.ui.components.usercard.UserCardState
import io.ktor.client.engine.okhttp.OkHttp
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class UserListViewModelImpl(
    stackExchangeApi: StackExchangeApi
) : UserListViewModel, ViewModel() {

    private val _cards = MutableStateFlow<List<UserCardState>>(emptyList())
    override val cards = _cards.asStateFlow()

    init {
        viewModelScope.launch {
            _cards.update {
                stackExchangeApi.getTopStackOverflowUsers().map { user ->
                    UserCardState(
                        "", user.name, 0
                    )
                }
            }
        }
    }

    companion object {
        val Factory = viewModelFactory {
            initializer {
                UserListViewModelImpl(
                    StackExchangeApiImpl(OkHttp.create())
                )
            }
        }
    }
}