package mx.com.u_life.presentation.components

import androidx.annotation.DrawableRes
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
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
        shape = MaterialTheme.shapes.medium,
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
    onClickMessage: () -> Unit = {},
    onClickImage: () -> Unit = {}
){
    Surface(
        shape = MaterialTheme.shapes.medium,
        color = MaterialTheme.colorScheme.surfaceVariant,
        modifier = modifier.clickable {
            onClickMessage()
        }
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(8.dp)
                .height(50.dp),
            verticalAlignment = Alignment.CenterVertically,
        ) {
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
            Column {
                Text(text = name, style = MaterialTheme.typography.titleMedium)
                Text(text = message, style = MaterialTheme.typography.bodySmall)
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
        message = "Hola, como estas?"
    )
}