package io.github.mslocombe.pixeltechnicalexercise.ui.components.usercard

import androidx.compose.animation.AnimatedContent
import androidx.compose.animation.slideInHorizontally
import androidx.compose.animation.slideOutHorizontally
import androidx.compose.animation.togetherWith
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults.buttonColors
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import io.github.mslocombe.pixeltechnicalexercise.R
import io.github.mslocombe.pixeltechnicalexercise.ui.theme.PixelTechnicalExerciseTheme

@Composable
fun FollowButton(
    isFollowed: Boolean, onClick: () -> Unit
) {
    Button(
        shape = RoundedCornerShape(15.dp),
        colors = buttonColors(
            containerColor = if (!isFollowed) MaterialTheme.colorScheme.primaryContainer
            else MaterialTheme.colorScheme.secondaryContainer
        ),
        content = {
            AnimatedContent(
                targetState = isFollowed,
                transitionSpec = {
                    slideInHorizontally() togetherWith slideOutHorizontally()
                }
            ) { followed ->
                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.spacedBy(8.dp)
                ) {
                    if (!followed) {
                        Icon(
                            painter = painterResource(R.drawable.ic_follow_add),
                            contentDescription = null,
                            tint = MaterialTheme.colorScheme.onPrimaryContainer
                        )
                        Text(
                            text = stringResource(R.string.follow),
                            style = MaterialTheme.typography.labelMedium,
                            color = MaterialTheme.colorScheme.onPrimaryContainer
                        )
                    } else {
                        Icon(
                            painter = painterResource(R.drawable.ic_follow_cancel),
                            contentDescription = null,
                            tint = MaterialTheme.colorScheme.onSecondaryContainer
                        )
                        Text(
                            text = stringResource(R.string.unfollow),
                            style = MaterialTheme.typography.labelMedium,
                            color = MaterialTheme.colorScheme.onSecondaryContainer
                        )
                    }
                }
            }
        },
        onClick = { onClick() })
}

@Preview
@Composable
private fun Preview_FollowButton() {
    PixelTechnicalExerciseTheme {
        FollowButton(false) {}
    }
}