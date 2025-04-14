package mx.com.u_life.presentation.components

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.BoxWithConstraints
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import mx.com.u_life.R
import mx.com.u_life.domain.models.rents.RentModel

@Composable
fun RentDetails(
    rent: RentModel?,
    isRentDetail: Boolean
) {
    BoxWithConstraints(
        modifier = Modifier.padding(bottom = 8.dp, start = 8.dp, end = 8.dp)
    ) {
        Column {
            if (!isRentDetail) {
                Box(modifier = Modifier
                    .height(24.dp)
                    .fillMaxWidth(0.7f)
                    .shimmerEffect()
                )
                Spacer(modifier = Modifier.height(8.dp))
                Box(modifier = Modifier
                    .height(20.dp)
                    .fillMaxWidth(0.9f)
                    .shimmerEffect()
                )
                Spacer(modifier = Modifier.height(16.dp))
                Box(modifier = Modifier
                    .height(20.dp)
                    .fillMaxWidth(0.5f)
                    .shimmerEffect()
                )
                Spacer(modifier = Modifier.height(16.dp))
                Row {
                    repeat(3) {
                        Box(modifier = Modifier
                            .height(300.dp)
                            .fillMaxWidth()
                            .shimmerEffect()
                        )
                        Spacer(modifier = Modifier.height(8.dp))
                    }
                }
                Spacer(modifier = Modifier.height(8.dp))
            } else {
                GenericTitleFeatureText(
                    text = rent?.name ?: "",
                    style = MaterialTheme.typography.bodyLarge
                )
                GenericTitleFeatureText(
                    text = rent?.description ?: "",
                    style = MaterialTheme.typography.bodyMedium
                )
                GenericFeatureText(
                    icon = R.drawable.ic_money,
                    text = "${rent?.price ?: 0} MXN"
                )
                GenericTitleFeatureText(
                    text = stringResource(id = R.string.bottom_sheet_restrictions),
                    style = MaterialTheme.typography.bodyLarge
                )
                rent?.restrictions!!.forEach {
                    GenericFeatureText(
                        icon = R.drawable.ic_restriction,
                        text = it
                    )
                }
                GenericTitleFeatureText(
                    text = stringResource(id = R.string.bottom_sheet_services),
                    style = MaterialTheme.typography.bodyLarge
                )
                rent.services.forEach {
                    GenericFeatureText(
                        icon = R.drawable.ic_info,
                        text = it
                    )
                }
                GenericTitleFeatureText(
                    text = stringResource(id = R.string.bottom_sheet_images),
                    style = MaterialTheme.typography.bodyLarge
                )
                Carousel(
                    items = rent.images,
                    width = this@BoxWithConstraints.maxWidth,
                    height = 300.dp
                )
                Spacer(modifier = Modifier.height(8.dp))
            }
        }
    }
}
