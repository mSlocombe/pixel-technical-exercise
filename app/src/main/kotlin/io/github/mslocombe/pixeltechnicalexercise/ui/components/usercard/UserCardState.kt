package io.github.mslocombe.pixeltechnicalexercise.ui.components.usercard


data class UserCardState(
    val userId: Int,
    val profilePictureUrl: String,
    val name: String,
    val reputation: Int,
    val isFollowed: Boolean
)