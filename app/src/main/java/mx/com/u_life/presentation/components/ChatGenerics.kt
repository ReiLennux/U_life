package mx.com.u_life.presentation.components

import androidx.annotation.DrawableRes
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import mx.com.u_life.R

@Composable
fun ChatName(
    modifier: Modifier = Modifier,
    @DrawableRes image: Int,
    name: String,
    onClickBack: () -> Unit
) {
    Surface(
        shape = RoundedCornerShape(bottomStart = 20.dp, bottomEnd = 20.dp),
        color = MaterialTheme.colorScheme.surfaceVariant,
        modifier = modifier
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(8.dp)
                .height(50.dp),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Row(
                verticalAlignment = Alignment.CenterVertically
            ) {
                IconButton(
                    onClick = { onClickBack() }
                ) {
                    Icon(painter = painterResource(id = R.drawable.ic_back), contentDescription = "Info")
                }
                Image(
                    painter = painterResource(id = image),
                    contentDescription = null,
                    contentScale = ContentScale.Crop,
                    modifier = Modifier
                        .size(45.dp)
                        .clip(CircleShape)
                        .border(BorderStroke(2.dp, MaterialTheme.colorScheme.primary), CircleShape)
                )
                Spacer(modifier = Modifier.width(10.dp))
                Text(text = name, style = MaterialTheme.typography.titleMedium)
            }
            IconButton(
                onClick = { /*TODO*/ }
            ) {
                Icon(painter = painterResource(id = R.drawable.ic_info), contentDescription = "Info")
            }
        }
    }
}

@Preview
@Composable
fun ChatNamePreview() {
    ChatName(
        image = R.drawable.fumo,
        name = "Peluche Chistoso",
        onClickBack = {}
    )
}

@Composable
fun ChatOverview(
    modifier: Modifier = Modifier,
    @DrawableRes image: Int,
    name: String,
    message: String,
    time: String = "12:00 AM",
    unreadCount: Int = 0,
    onClickMessage: () -> Unit = {},
    onClickAvatar: () -> Unit = {}
) {
    Surface(
        modifier = modifier
            .fillMaxWidth()
            .clickable { onClickMessage() },
        color = MaterialTheme.colorScheme.surface
    ) {
        Row(
            modifier = Modifier
                .padding(horizontal = 12.dp, vertical = 10.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            // Avatar
            Image(
                painter = painterResource(id = image),
                contentDescription = "User avatar",
                contentScale = ContentScale.Crop,
                modifier = Modifier
                    .size(48.dp)
                    .clip(CircleShape)
                    .border(2.dp, MaterialTheme.colorScheme.primary, CircleShape)
                    .clickable { onClickAvatar() }
            )

            Spacer(modifier = Modifier.width(12.dp))

            // Name and message
            Column(
                modifier = Modifier.weight(1f)
            ) {
                Text(
                    text = name,
                    style = MaterialTheme.typography.titleMedium,
                    maxLines = 1,
                    overflow = TextOverflow.Ellipsis
                )
                Text(
                    text = message,
                    style = MaterialTheme.typography.bodySmall,
                    color = MaterialTheme.colorScheme.onSurfaceVariant,
                    maxLines = 1,
                    overflow = TextOverflow.Ellipsis
                )
            }

            // Time and unread badge
            Column(
                horizontalAlignment = Alignment.End
            ) {
                Text(
                    text = time,
                    style = MaterialTheme.typography.bodySmall,
                    color = MaterialTheme.colorScheme.onSurfaceVariant
                )
                Spacer(modifier = Modifier.height(6.dp))
                if (unreadCount > 0) {
                    Box(
                        modifier = Modifier
                            .background(MaterialTheme.colorScheme.primary, CircleShape)
                            .padding(horizontal = 8.dp, vertical = 3.dp),
                        contentAlignment = Alignment.Center
                    ) {
                        Text(
                            text = unreadCount.toString(),
                            style = MaterialTheme.typography.labelSmall,
                            color = MaterialTheme.colorScheme.onPrimary
                        )
                    }
                }
            }
        }
    }
}

@Preview
@Composable
fun ChatOverviewPreview() {
    ChatOverview(
        image = R.drawable.fumo,
        name = "Peluche Chistoso",
        message = "Hola, como estas?",
        onClickMessage = {},
        onClickAvatar = {},
        unreadCount = 2
    )
}