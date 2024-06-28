package mx.com.u_life.presentation.components

import androidx.annotation.StringRes
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import mx.com.u_life.R

/**
 * Componente genérico para una tarjeta personalizable en Jetpack Compose.
 *
 * @param title Título de la tarjeta.
 * @param description Descripción de la tarjeta.
 * @param content Composable que se mostrará como contenido adicional en la tarjeta.
 * @param modifier Modificador opcional para personalizar la apariencia de la tarjeta.
 */

@Composable
fun GenericCard(
    @StringRes title: Int,
    @StringRes description: Int,
    content: @Composable () -> Unit,
    modifier: Modifier = Modifier
) {
    Card(
        modifier = modifier
            .padding(8.dp)
            .fillMaxWidth(),
        colors = CardDefaults.cardColors(
            containerColor = MaterialTheme.colorScheme.surface,
        ),
        elevation = CardDefaults.cardElevation(4.dp),
    ) {
        Column(
            modifier = Modifier
                .padding(16.dp)
                .fillMaxWidth(),
            horizontalAlignment = Alignment.CenterHorizontally,
        ) {
            Text(
                text = stringResource(id = title),
                style = MaterialTheme.typography.titleLarge,
                color = MaterialTheme.colorScheme.primary
            )
            Spacer(modifier = Modifier.height(8.dp))
            Text(
                text = stringResource(id = description),
                style = MaterialTheme.typography.bodyMedium,
                color = MaterialTheme.colorScheme.onSurface,
                textAlign = TextAlign.Center
            )
            Spacer(modifier = Modifier.height(16.dp))
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.Center
            ) {
                content()
            }
        }
    }
}

@Preview
@Composable
fun PreviewGenericCard() {
    Column {
        GenericCard(
            title = R.string.chats_card_tittle,
            description = R.string.chats_card_description,
            content = {
                Button(
                    onClick = { /* Acción cuando se hace clic */ },
                    modifier = Modifier.padding(vertical = 8.dp)
                ) {
                    Text(text = "Botón dentro de la tarjeta")
                }
            }
        )
    }
}