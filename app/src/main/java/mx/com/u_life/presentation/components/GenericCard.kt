package mx.com.u_life.presentation.components

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.CardColors
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.CardElevation
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp

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
    title: String,
    description: String,
    content: @Composable () -> Unit,
    modifier: Modifier = Modifier
) {
    Card(
        modifier = modifier.padding(8.dp),
        colors = CardDefaults.cardColors(
            containerColor = MaterialTheme.colorScheme.surface,
        ),
        elevation = CardDefaults.cardElevation(4.dp)
    ) {
        Column(
            modifier = Modifier.padding(16.dp)
        ) {
            Text(
                text = title,
                style = MaterialTheme.typography.titleMedium,
                color = MaterialTheme.colorScheme.primary
            )
            Spacer(modifier = Modifier.height(8.dp))
            Text(
                text = description,
                style = MaterialTheme.typography.bodySmall,
                color = Color.Gray
            )
            Spacer(modifier = Modifier.height(16.dp))
            content()
        }
    }
}

@Preview
@Composable
fun PreviewGenericCard() {
    Column {
        GenericCard(
            title = "Título de la Tarjeta",
            description = "Descripción detallada de la tarjeta.",
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