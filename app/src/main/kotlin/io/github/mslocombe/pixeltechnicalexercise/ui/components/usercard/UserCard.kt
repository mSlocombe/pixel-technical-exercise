package io.github.mslocombe.pixeltechnicalexercise.ui.components.usercard

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import io.github.mslocombe.pixeltechnicalexercise.R
import io.github.mslocombe.pixeltechnicalexercise.ui.components.AsyncImage
import io.github.mslocombe.pixeltechnicalexercise.ui.theme.PixelTechnicalExerciseTheme

@Composable
fun UserCard(
    modifier: Modifier = Modifier, state: UserCardState, onFollow: () -> Unit
) {
    Row(
        modifier
            .testTag("userCard")
            .clip(RoundedCornerShape(25.dp))
            .background(Color.White)
            .padding(16.dp),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.spacedBy(8.dp)
    ) {
        ProfilePicture(state.profilePictureUrl)
        Column(Modifier.weight(1f)) {
            NameLabel(state.name)
            ReputationLabel(state.reputation)
        }
        FollowButton(
            isFollowed = state.isFollowed,
            onClick = { onFollow() })
    }
}

@Composable
private fun ProfilePicture(url: String) {
    AsyncImage(
        modifier = Modifier
            .size(75.dp)
            .clip(CircleShape)
            .border(2.dp, MaterialTheme.colorScheme.background, CircleShape), url = url
    )
}

@Composable
private fun NameLabel(name: String) {
    Text(
        text = name, style = MaterialTheme.typography.labelLarge.copy(
            fontWeight = FontWeight.Bold
        ), maxLines = 1, overflow = TextOverflow.Ellipsis
    )
}

@Composable
private fun ReputationLabel(reputation: Int) {
    Text(
        text = stringResource(R.string.reputation, reputation),
        style = MaterialTheme.typography.labelSmall.copy(
            color = MaterialTheme.colorScheme.secondary
        ),
        maxLines = 1,
        overflow = TextOverflow.Ellipsis
    )
}

@Preview
@Composable
private fun Preview_UserCard() {
    PixelTechnicalExerciseTheme {
        UserCard(
            modifier = Modifier
                .fillMaxWidth()
                .height(160.dp), state = UserCardState(userId = 1,
                profilePictureUrl = "", name = "Jane Doe", reputation = 9000, false
            ), onFollow = {})
    }
}