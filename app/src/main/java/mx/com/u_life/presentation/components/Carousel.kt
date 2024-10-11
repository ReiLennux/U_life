package mx.com.u_life.presentation.components

import androidx.compose.foundation.layout.*
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.carousel.HorizontalUncontainedCarousel
import androidx.compose.material3.carousel.rememberCarouselState
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import coil.request.ImageRequest

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun Carousel(
    items: List<String>,
    width : Dp = 250.dp,
    height : Dp = 200.dp
) {
    Column(
        modifier = Modifier.fillMaxWidth(),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        HorizontalUncontainedCarousel(
            state = rememberCarouselState { items.count() },
            itemWidth = width,
            itemSpacing = 12.dp,
            contentPadding = PaddingValues(horizontal = 12.dp),
            modifier = Modifier
                .fillMaxWidth()
                .wrapContentHeight()
                .padding(top = 12.dp)
                .align(Alignment.CenterHorizontally)
        ) { index ->
            val value = items[index]
            AsyncImage(
                model = ImageRequest.Builder(LocalContext.current)
                    .data(value)
                    .crossfade(true)
                    .build(),
                contentDescription = null,
                contentScale = ContentScale.Crop,
                modifier = Modifier
                    .height(height)
                    .fillMaxWidth()
                    .maskClip(shape = MaterialTheme.shapes.extraLarge)
            )
        }
    }
}

