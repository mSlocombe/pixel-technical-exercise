package io.github.mslocombe.pixeltechnicalexercise.ui.components.usercard

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Devices
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import io.github.mslocombe.pixeltechnicalexercise.R
import io.github.mslocombe.pixeltechnicalexercise.ui.components.AsyncImage
import io.github.mslocombe.pixeltechnicalexercise.ui.theme.PixelTechnicalExerciseTheme

@Composable
fun UserCard(
    modifier: Modifier, state: UserCardState
) {
    Row(
        modifier
            .testTag("userCard")
            .clip(RoundedCornerShape(25.dp))
            .background(Color.White)
            .padding(horizontal = 16.dp),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.spacedBy(8.dp)
    ) {
        AsyncImage(
            modifier = Modifier
                .size(75.dp)
                .clip(CircleShape)
                .border(2.dp, Color(0xFF202020), CircleShape),
            url = state.profilePictureUrl
        )
        Column(Modifier.weight(1f)) {
            Text(
                text = state.name,
                style = MaterialTheme.typography.labelLarge.copy(
                    fontWeight = FontWeight.Bold
                ),
                maxLines = 1,
                overflow = TextOverflow.Ellipsis
            )
            Text(
                text = stringResource(R.string.reputation, state.reputation),
                style = MaterialTheme.typography.labelMedium.copy(
                    color = MaterialTheme.colorScheme.secondary
                ),
                maxLines = 1,
                overflow = TextOverflow.Ellipsis
            )
        }
        Button(
            shape = RoundedCornerShape(15.dp),
            content = {
                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.spacedBy(8.dp)
                ) {
                    Icon(
                        painter = painterResource(R.drawable.ic_follow_add),
                        contentDescription = stringResource(R.string.follow)
                    )
                    Text(
                        text = stringResource(R.string.follow),
                        style = MaterialTheme.typography.labelMedium
                    )
                }
            },
            onClick = { TODO() }
        )
    }
}

@Preview(showBackground = true, backgroundColor = 0xFF202020, device = Devices.TABLET)
@Composable
private fun Preview_UserCard() {
    PixelTechnicalExerciseTheme {
        Box(Modifier.fillMaxSize()) {
            UserCard(
                Modifier
                    .fillMaxWidth()
                    .height(160.dp), state = UserCardState(
                    profilePictureUrl = "", name = "Jane Doe", reputation = 9000
                )
            )
        }
    }
}