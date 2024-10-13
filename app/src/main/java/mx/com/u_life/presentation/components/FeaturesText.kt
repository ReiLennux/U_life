package mx.com.u_life.presentation.components

import androidx.annotation.DrawableRes
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import mx.com.u_life.R

/**
 * Componente genérico para las caracteristicas de las rentas en Jetpack Compose.
 *
 * @param icon Icono de la tarjeta.
 * @param text Texto de la tarjeta.
 */
@Composable
fun GenericFeatureText(
    @DrawableRes icon: Int,
    text : String
){
    Row(
        modifier = Modifier.padding(8.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Icon(
            modifier = Modifier.padding(end = 8.dp),
            painter = painterResource(id = icon),
            contentDescription = "Feature Icon"
        )

        Text(
            text = text,
            style = MaterialTheme.typography.bodySmall,
            color = MaterialTheme.colorScheme.onSurfaceVariant
        )
    }
}

/**
 * Componente genérico para los titulos de las caracteristicas de las rentas en Jetpack Compose.
 * @param style Estilo del texto.
 * @param text Texto de la tarjeta.
 */
@Composable
fun GenericTitleFeatureText(
    text : String,
    style: TextStyle
){
    Row(
        modifier = Modifier.padding(8.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Text(
            text = text,
            style = style,
            color = MaterialTheme.colorScheme.onSurfaceVariant
        )
    }
}

@Preview
@Composable
fun PreviewGenericFeatureText(){
    GenericFeatureText(icon = R.drawable.ic_info, text = "Informacion sobre este lugar de renta")
}